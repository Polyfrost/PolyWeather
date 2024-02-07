package org.polyfrost.polyweather.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.data.*
import org.polyfrost.polyweather.PolyWeather
import org.polyfrost.polyweather.util.WeatherType


object ModConfig : Config(Mod(PolyWeather.NAME, ModType.UTIL_QOL), "${PolyWeather.MODID}.json") {

    @Dropdown(
        name = "Weather",
        options = ["Clear", "Rain", "Storm", "Snow"],
        category = "Weather"
    )
    var weather = 0

    @Checkbox(
        name = "Use IRL weather",
        category = "Weather"
    )
    var irlWeather = false

    @Slider(
        name = "Rain intensity",
        min = 0f, max = 1f,
        category = "Weather"
    )
    var rainStrength = 1f

    @Slider(
        name = "Snow intensity",
        min = 0f, max = 1f,
        category = "Weather"
    )
    var snowStrength = 1f

    @Slider(
        name = "Thunder intensity",
        min = 0f, max = 1f,
        category = "Weather"
    )
    var thunderStrength = 1f


    fun getWeatherType(): WeatherType {
        return WeatherType.byId(weather)
    }

    init {
        initialize()

        addDependency("weather", "") { !irlWeather }
        addDependency("rainStrength", "") { PolyWeather.isRaining && !PolyWeather.isSnowing }
        addDependency("snowStrength", "") { PolyWeather.isSnowing }
        addDependency("thunderStrength", "") { PolyWeather.isThundering }
    }
}