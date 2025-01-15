package org.polyfrost.polyweather.client

import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Dropdown
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.polyweather.PolyWeatherConstants
import org.polyfrost.polyweather.util.WeatherType

object PolyWeatherConfig : Config("${PolyWeatherConstants.ID}.json", "/polyweather_dark.svg", PolyWeatherConstants.NAME, Category.QOL) {

    @Dropdown(
        title = "Weather",
        options = ["Clear", "Rain", "Storm", "Snow"],
        category = "Weather"
    )
    var weather = 0

    val weatherType: WeatherType
        get() = WeatherType.from(weather)

    @Checkbox(
        title = "Use IRL weather",
        category = "Weather"
    )
    var irlWeather = false

    @Slider(
        title = "Rain intensity",
        min = 0f, max = 1f,
        category = "Weather"
    )
    var rainStrength = 1f

    @Slider(
        title = "Snow intensity",
        min = 0f, max = 1f,
        category = "Weather"
    )
    var snowStrength = 1f

    @Slider(
        title = "Thunder intensity",
        min = 0f, max = 1f,
        category = "Weather"
    )
    var thunderStrength = 1f

    init {
        addDependency("weather", "IRL Weather") { if (irlWeather) Property.Display.DISABLED else Property.Display.SHOWN }
        addDependency("rainStrength", "Not Snowing") { if (PolyWeatherClient.isRaining && !PolyWeatherClient.isSnowing) Property.Display.SHOWN else Property.Display.DISABLED }
        addDependency("snowStrength", "Snowing") { if (PolyWeatherClient.isSnowing) Property.Display.SHOWN else Property.Display.DISABLED }
        addDependency("thunderStrength", "Thundering") { if (PolyWeatherClient.isThundering) Property.Display.SHOWN else Property.Display.DISABLED }
    }
}