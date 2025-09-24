package org.polyfrost.polyweather.util

enum class WeatherType {
    CLEAR,
    RAIN,
    STORM,
    SNOW;

    companion object {

        fun from(idx: Int): WeatherType {
            return values()[idx]
        }

    }

}
