package org.polyfrost.polyweather.client

import org.polyfrost.polyweather.client.realtime.RealWeatherHandler

object PolyWeatherClient {
    fun initialize() {
        PolyWeatherConfig.preload()
        RealWeatherHandler.initialize()
    }
}
