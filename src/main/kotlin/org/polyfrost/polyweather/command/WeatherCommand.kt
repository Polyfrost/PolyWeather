package org.polyfrost.polyweather.command

import cc.polyfrost.oneconfig.utils.commands.annotations.*
import org.polyfrost.polyweather.PolyWeather
import org.polyfrost.polyweather.config.ModConfig

@Command(value = PolyWeather.MODID, description = "Access the " + PolyWeather.NAME + " GUI.", aliases = ["weatherchanger"])
object WeatherCommand {
    @Main
    private fun main() {
        ModConfig.openGui()
    }
}