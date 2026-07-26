package org.polyfrost.polyweather.client.realtime

import com.google.gson.JsonElement
import com.google.gson.JsonObject
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
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

object RealWeatherHandler {
    private val logger = LogManager.getLogger(RealWeatherHandler::class.java)

    private const val MIN_RETRY_SECONDS = 60L
    private const val MAX_RETRY_SECONDS = 900L

    private val data = ConcurrentHashMap<Instant, WMOCode>()

    // The forecast is only ever fetched off-thread, so guard against two populates overlapping.
    private val populating = AtomicBoolean()

    @Volatile
    private var retryAfter = Instant.MIN

    @Volatile
    private var retryDelaySeconds = MIN_RETRY_SECONDS

    // These are read from the render thread, so they only ever look at the already-fetched forecast.
    val currentCode: WMOCode?
        get() = data[currentHour]

    val nextCode: WMOCode?
        get() = data[nextHour]

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

    val isRainy: Boolean
        get() = rainStrength > 0.05

    val isStormy: Boolean
        get() = thunderStrength > 0.05

    val isSnowy: Boolean
        get() {
            val currentCode = currentCode ?: return false // Caches the value
            return currentCode.snow
        }

    fun initialize() {
        if (!PolyWeatherConfig.isIrlWeather) {
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
            if (!data.containsKey(nextHour)) {
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
        if (Instant.now().isBefore(retryAfter)) {
            return
        }

        if (!populating.compareAndSet(false, true)) {
            return
        }

        try {
            val longitudeLatitude = obtainLongitudeLatitude()
            if (longitudeLatitude == null) {
                backOff()
                return
            }

            val (longitude, latitude) = longitudeLatitude

            val weatherCodes = obtainHourlyWeatherCodes(longitude, latitude)
            if (weatherCodes.isEmpty()) {
                backOff()
                return
            }

            data.putAll(weatherCodes)
            retryDelaySeconds = MIN_RETRY_SECONDS
        } finally {
            populating.set(false)
        }
    }

    /**
     * Delays the next attempt so that a failing or rate-limiting endpoint isn't retried every minute.
     */
    private fun backOff() {
        retryAfter = Instant.now().plusSeconds(retryDelaySeconds)
        logger.warn("Failed to populate the weather forecast, retrying in {} seconds", retryDelaySeconds)
        retryDelaySeconds = (retryDelaySeconds * 2).coerceAtMost(MAX_RETRY_SECONDS)
    }

    /**
     * Fetches and parses JSON, returning `null` for anything that isn't a usable response.
     */
    private fun fetchJson(url: String): JsonElement? {
        return try {
            JsonUtils.parseFromUrl(url)
        } catch (e: Exception) {
            logger.error("Failed to fetch JSON from {}", url, e)
            null
        }
    }

    private fun obtainLongitudeLatitude(): Pair<Double, Double>? {
        val json = fetchJson("http://ip-api.com/json") as? JsonObject
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
        // The forecast is requested in UTC so that its timestamps line up with the instants used to look them up.
        val json = fetchJson("https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&hourly=weathercode&timezone=UTC") as? JsonObject
        if (json == null) {
            logger.error("Failed to obtain JSON from open-meteo.com")
            return emptyMap()
        }

        val hourly = json.getAsJsonObject("hourly")
        val time = hourly?.getAsJsonArray("time")
        val weatherCode = hourly?.getAsJsonArray("weathercode")
        if (time == null || weatherCode == null || time.size() != weatherCode.size()) {
            logger.error("open-meteo.com did not return an hourly forecast")
            return emptyMap()
        }

        val weatherCodes = mutableMapOf<Instant, WMOCode>()
        try {
            for (i in 0 until time.size()) {
                val value = time.get(i).asString
                val code = WMOCode.fromCode(weatherCode.get(i).asInt)
                weatherCodes[LocalDateTime.parse(value).toInstant(ZoneOffset.UTC)] = code
            }
        } catch (e: Exception) {
            logger.error("Failed to parse the hourly forecast from open-meteo.com", e)
            return emptyMap()
        }

        return weatherCodes
    }
}
