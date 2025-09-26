@file:Suppress("FunctionName")

package org.polyfrost.polyweather.client

interface ClientWeather {
    fun `polyweather$getTrueRainGradient`(delta: Float): Float
}
