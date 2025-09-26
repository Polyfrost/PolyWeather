package org.polyfrost.polyweather.client

import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Dropdown
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.polyweather.PolyWeatherConstants
import org.polyfrost.polyweather.util.WeatherType

object PolyWeatherConfig : Config("${PolyWeatherConstants.ID}.json", "/assets/polyweather/polyweather_dark.svg", PolyWeatherConstants.NAME, Category.QOL) {
    @Switch(
        title = "Enable PolyWeather",
        description = "Master switch to enable/disable the mod",
        category = "Weather"
    ) @JvmStatic var isEnabled = true

    @Dropdown(
        title = "Weather",
        options = ["Clear", "Rain", "Storm", "Snow"],
        category = "Weather"
    ) var weather = 0

    val weatherType: WeatherType
        get() = WeatherType.from(weather)

    @Checkbox(
        title = "Use IRL weather",
        category = "Weather"
    ) var isIrlWeather = false

    @Slider(
        title = "Rain intensity",
        min = 0f, max = 1f,
        category = "Weather"
    ) var rainStrength = 1f

    @Slider(
        title = "Snow intensity",
        min = 0f, max = 1f,
        category = "Weather"
    ) var snowStrength = 1f

    @Slider(
        title = "Thunder intensity",
        min = 0f, max = 1f,
        category = "Weather"
    ) var thunderStrength = 1f

    init {
        addDependency("weather", "IRL Weather") { if (isIrlWeather) Property.Display.DISABLED else Property.Display.SHOWN }
        addDependency("rainStrength", "Not Snowing") { if (ClientWeatherManager.isRainy && !ClientWeatherManager.isSnowy) Property.Display.SHOWN else Property.Display.DISABLED }
        addDependency("snowStrength", "Snowing") { if (ClientWeatherManager.isSnowy) Property.Display.SHOWN else Property.Display.DISABLED }
        addDependency("thunderStrength", "Thundering") { if (ClientWeatherManager.isStormy) Property.Display.SHOWN else Property.Display.DISABLED }
    }
}
