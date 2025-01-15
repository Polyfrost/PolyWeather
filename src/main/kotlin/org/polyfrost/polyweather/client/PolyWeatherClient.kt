package org.polyfrost.polyweather.client

import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.polyweather.client.realtime.RealWeatherHandler
import org.polyfrost.polyweather.util.WeatherType

object PolyWeatherClient {

    @JvmStatic
    val isRaining: Boolean
        get() = if (PolyWeatherConfig.irlWeather) RealWeatherHandler.isRaining else PolyWeatherConfig.weatherType != WeatherType.CLEAR

    @JvmStatic
    val isThundering: Boolean
        get() = if (PolyWeatherConfig.irlWeather) RealWeatherHandler.isThundering else PolyWeatherConfig.weatherType == WeatherType.STORM

    @JvmStatic
    val isSnowing: Boolean
        get() = if (PolyWeatherConfig.irlWeather) RealWeatherHandler.isSnowing else PolyWeatherConfig.weatherType == WeatherType.SNOW

    @JvmStatic
    val rainStrength: Float
        get() = if (PolyWeatherConfig.irlWeather) RealWeatherHandler.rainStrength else PolyWeatherConfig.rainStrength

    @JvmStatic
    val snowStrength: Float
        get() = if (PolyWeatherConfig.irlWeather) RealWeatherHandler.rainStrength else PolyWeatherConfig.snowStrength

    @JvmStatic
    val thunderStrength: Float
        get() = if (PolyWeatherConfig.irlWeather) RealWeatherHandler.thunderStrength else PolyWeatherConfig.thunderStrength

    fun initialize() {
        PolyWeatherConfig.preload()
        CommandManager.registerCommand(PolyWeatherCommand)
        RealWeatherHandler.initialize()
    }

}
