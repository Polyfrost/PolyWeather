package org.polyfrost.polyweather.client

import dev.deftu.omnicore.api.client.world
import dev.deftu.omnicore.api.math.OmniMath
import org.polyfrost.polyweather.client.realtime.RealWeatherHandler
import org.polyfrost.polyweather.util.WeatherType

object ClientWeatherManager {
    private val weatherStorage: WeatherStorage?
        get() {
            val world = world ?: return null
            return world as? WeatherStorage
                ?: throw IllegalStateException("World does not implement WeatherStorage")
        }

    @JvmStatic val isRainy: Boolean get() = if (PolyWeatherConfig.isIrlWeather) RealWeatherHandler.isRainy else PolyWeatherConfig.weatherType != WeatherType.CLEAR
    @JvmStatic val isStormy: Boolean get() = if (PolyWeatherConfig.isIrlWeather) RealWeatherHandler.isStormy else PolyWeatherConfig.weatherType == WeatherType.STORM
    @JvmStatic val isSnowy: Boolean get() = if (PolyWeatherConfig.isIrlWeather) RealWeatherHandler.isSnowy else PolyWeatherConfig.weatherType == WeatherType.SNOW

    @JvmStatic val isActuallyRaining: Boolean get() = weatherStorage?.`polyweather$isRaining`() == true

    private val rainStrength: Float get() = if (PolyWeatherConfig.isIrlWeather) RealWeatherHandler.rainStrength else PolyWeatherConfig.rainStrength
    private val snowStrength: Float get() = if (PolyWeatherConfig.isIrlWeather) RealWeatherHandler.rainStrength else PolyWeatherConfig.snowStrength
    private val stormStrength: Float get() = if (PolyWeatherConfig.isIrlWeather) RealWeatherHandler.thunderStrength else PolyWeatherConfig.thunderStrength

    private var prevRainStrength = 0f
    private var prevSnowStrength = 0f
    private var prevStormStrength = 0f

    @JvmStatic
    fun getRainStrength(delta: Float): Float {
        if (!isRainy) {
            prevRainStrength = 0f
            return 0f
        }

        prevRainStrength = OmniMath.lerp(prevRainStrength, rainStrength, delta)
        return prevRainStrength
    }

    @JvmStatic
    fun getSnowStrength(delta: Float): Float {
        if (!isSnowy) {
            prevSnowStrength = 0f
            return 0f
        }

        prevSnowStrength = OmniMath.lerp(prevSnowStrength, snowStrength, delta)
        return prevSnowStrength
    }

    @JvmStatic
    fun getPrecipitationStrength(delta: Float): Float {
        return when {
            isSnowy -> getSnowStrength(delta)
            isRainy -> getRainStrength(delta)
            else -> 0f
        }
    }

    @JvmStatic
    fun getStormStrength(delta: Float): Float {
        if (!isStormy) {
            prevStormStrength = 0f
            return 0f
        }

        prevStormStrength = OmniMath.lerp(prevStormStrength, stormStrength, delta)
        return prevStormStrength
    }
}
