package org.polyfrost.polyweather.client

import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command
import org.polyfrost.oneconfig.utils.v1.dsl.openUI
import org.polyfrost.polyweather.PolyWeatherConstants

@Command(value = [PolyWeatherConstants.ID, "weatherchanger"], description = "Access the " + PolyWeatherConstants.NAME + " GUI.")
object PolyWeatherCommand {

    @Command
    private fun main() {
        PolyWeatherConfig.openUI()
    }

}