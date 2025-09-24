package org.polyfrost.polyweather.client.realtime

import org.apache.logging.log4j.LogManager
import org.polyfrost.oneconfig.api.event.v1.eventHandler
import org.polyfrost.oneconfig.api.event.v1.events.TickEvent
import org.polyfrost.oneconfig.utils.v1.JsonUtils
import org.polyfrost.oneconfig.utils.v1.dsl.runAsync
import org.polyfrost.polyweather.client.PolyWeatherConfig
import org.polyfrost.polyweather.currentHour
import org.polyfrost.polyweather.currentTime
import org.polyfrost.polyweather.interpolate
import org.polyfrost.polyweather.nextHour
import org.polyfrost.polyweather.util.WMOCode
import java.time.Instant
import java.util.Calendar

object RealWeatherHandler {
    private val logger = LogManager.getLogger(RealWeatherHandler::class.java)

    private val data = mutableMapOf<Instant, WMOCode>()

    val currentCode: WMOCode?
        get() {
            val currentHour = currentHour // Caches the value
            if (!data.containsKey(currentHour)) {
                populate() // Populates synchronously so that we wait until the data is populated

                // If the data is still not present, return null
                if (!data.containsKey(currentHour)) {
                    return null
                }
            }

            return data[currentHour]
        }

    val nextCode: WMOCode?
        get() {
            val nextHour = nextHour // Caches the value
            if (!data.containsKey(nextHour)) {
                populate() // Populates synchronously so that we wait until the data is populated

                // If the data is still not present, return null
                if (!data.containsKey(nextHour)) {
                    return null
                }
            }

            return data[nextHour]
        }

    val rainStrength: Float
        get() {
            val currentCode = currentCode ?: return 0f // Caches the value
            val nextCode = nextCode ?: return 0f // Caches the value
            return interpolate(currentCode.rainStrength, nextCode.rainStrength, currentTime)
        }

    val thunderStrength: Float
        get() {
            val currentCode = currentCode ?: return 0f // Caches the value
            val nextCode = nextCode ?: return 0f // Caches the value
            return interpolate(currentCode.thunderStrength, nextCode.thunderStrength, currentTime)
        }

    val isRaining: Boolean
        get() = rainStrength > 0.05

    val isThundering: Boolean
        get() = thunderStrength > 0.05

    val isSnowing: Boolean
        get() {
            val currentCode = currentCode ?: return false // Caches the value
            return currentCode.snow
        }

    fun initialize() {
        if (!PolyWeatherConfig.irlWeather) {
            return
        }

        var tickCount = 0
        eventHandler<TickEvent.Start> {
            // Only re-run the following code every 1200 ticks (1 minute)
            // We don't need this check to be done as often as the tick event is called
            // Technically it's not that computationally expensive, but it's free performance that we can save for our end user.
            if (tickCount++ % 1200 != 0) {
                return@eventHandler
            }

            // Checks if the next hour is within the forecast data map,
            // and re-populates the data if it is not.
            if (!data.containsKey(Instant.now().plusSeconds(3600))) {
                runAsync {
                    populate()
                }
            }
        }.register()

        // Asynchronously populate the forecast map at startup.
        runAsync {
            populate()
        }
    }

    private fun populate() {
        val longitudeLatitude = obtainLongitudeLatitude() ?: return
        val (longitude, latitude) = longitudeLatitude

        val weatherCodes = obtainHourlyWeatherCodes(longitude, latitude)
        data.putAll(weatherCodes)
    }

    private fun obtainLongitudeLatitude(): Pair<Double, Double>? {
        val json = JsonUtils.parseFromUrl("http://ip-api.com/json/")?.asJsonObject
        if (json == null) {
            logger.error("Failed to obtain JSON from ip-api.com")
            return null
        }

        if (!json.has("lat") || !json.has("lon")) {
            logger.error("ip-api.com did not return longitude and latitude values")
            return null
        }

        try {
            val longitude = json.get("lon").asDouble
            val latitude = json.get("lat").asDouble
            return Pair(longitude, latitude)
        } catch (e: Exception) {
            logger.error("Failed to obtain latitude and longitude from ip-api.com JSON", e)
            return null
        }
    }

    private fun obtainHourlyWeatherCodes(longitude: Double, latitude: Double): Map<Instant, WMOCode> {
        val json = JsonUtils.parseFromUrl("https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&hourly=weathercode&timezone=${Calendar.getInstance().timeZone.id}")?.asJsonObject
        if (json == null) {
            logger.error("Failed to obtain JSON from open-meteo.com")
            return emptyMap()
        }

        val hourly = json.getAsJsonObject("hourly")
        val time = hourly.getAsJsonArray("time")
        val weatherCode = hourly.getAsJsonArray("weathercode")

        val weatherCodes = mutableMapOf<Instant, WMOCode>()
        for (i in 0 until time.size()) {
            val value = time.get(i).asString
            val code = WMOCode.fromCode(weatherCode.get(i).asInt)
            weatherCodes[Instant.parse("$value:00Z")] = code
        }

        return weatherCodes
    }
}
