package org.polyfrost.polyweather.client

import com.mojang.brigadier.Command
import dev.deftu.omnicore.api.client.commands.OmniClientCommands
import dev.deftu.omnicore.api.client.commands.command
import org.polyfrost.oneconfig.utils.v1.dsl.openUI
import org.polyfrost.polyweather.PolyWeatherConstants
import org.polyfrost.polyweather.client.realtime.RealWeatherHandler

object PolyWeatherClient {
    fun initialize() {
        PolyWeatherConfig.preload()
        RealWeatherHandler.initialize()

        OmniClientCommands.command(PolyWeatherConstants.ID) {
            runs { _ ->
                PolyWeatherConfig.openUI()
                Command.SINGLE_SUCCESS
            }
        }.register()
    }
}
