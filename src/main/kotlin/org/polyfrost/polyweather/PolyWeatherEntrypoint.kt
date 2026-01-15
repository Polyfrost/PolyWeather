package org.polyfrost.polyweather

import net.fabricmc.api.ClientModInitializer
import org.polyfrost.polyweather.client.PolyWeatherClient

class PolyWeatherEntrypoint : ClientModInitializer {
    override
    fun onInitializeClient() {
        PolyWeatherClient.initialize()
    }
}
