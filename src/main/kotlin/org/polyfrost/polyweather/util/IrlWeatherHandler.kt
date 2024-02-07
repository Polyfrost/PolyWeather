package org.polyfrost.polyweather.util

import cc.polyfrost.oneconfig.utils.NetworkUtils
import cc.polyfrost.oneconfig.utils.dsl.runAsync
import com.google.gson.JsonObject
import org.polyfrost.polyweather.config.ModConfig
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Calendar


object IrlWeatherHandler {
    private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    private val data: HashMap<Instant, WMOCode> = HashMap()
    private var fetching = false

    fun fetchData() {
        if (!ModConfig.irlWeather || fetching) return
        fetching = true
        runAsync {
            val location: JsonObject = NetworkUtils.getJsonElement("http://ip-api.com/json/").asJsonObject
            if (!location.has("lat") || !location.has("lon")) return@runAsync
            val latitude: Double = location.get("lat").asDouble
            val longitude: Double = location.get("lon").asDouble
            try {
                val json: JsonObject = NetworkUtils.getJsonElement(
                    "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&hourly=weathercode&timezone=" + URLEncoder.encode(Calendar.getInstance().getTimeZone().id, StandardCharsets.UTF_8.toString())
                ).asJsonObject
                for (i in 0 until json.get("hourly").getAsJsonObject().get("time").getAsJsonArray().size()) {
                    val value: String = json.get("hourly").getAsJsonObject().get("time").getAsJsonArray().get(i).asString
                    val code = WMOCode.fromCode(json.get("hourly").getAsJsonObject().get("weathercode").getAsJsonArray().get(i).asFloat as Int)
                    data[Instant.parse("$value:00Z")] = code
                }
                // Check if we have the data, so we don't re-fetch later
                if (data.containsKey(thisHour) && data.containsKey(nextHour)) fetching = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val isRaining: Boolean
        get() = rainStrength > 0.05

    val isThundering: Boolean
        get() = thunderStrength > 0.05

    val isSnowing: Boolean
        get() = get(thisHour)!!.snow

    val rainStrength: Float
        get() = interpolate(get(thisHour)!!.rainStrength, get(nextHour)!!.rainStrength, time)

    val thunderStrength: Float
        get() = interpolate(get(thisHour)!!.thunderStrength, get(nextHour)!!.thunderStrength, time)

    private fun get(instant: Instant): WMOCode? {
        if (!data.containsKey(instant)) {
            fetchData()
            return WMOCode.CLEAR
        }
        return data[instant]
    }

    private val thisHour: Instant
        get() {
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.toInstant()
        }

    private val nextHour: Instant
        get() {
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.add(Calendar.HOUR, 1)
            return calendar.toInstant()
        }

    private val time: Float
        get() = Calendar.getInstance().get(Calendar.MINUTE) / 60f + Calendar.getInstance().get(Calendar.SECOND) / 3600f + Calendar.getInstance().get(Calendar.MILLISECOND) / 3600000f

    private fun interpolate(f1: Float, f2: Float, t: Float): Float {
        return f1 * (1 - t) + f2 * t
    }
}