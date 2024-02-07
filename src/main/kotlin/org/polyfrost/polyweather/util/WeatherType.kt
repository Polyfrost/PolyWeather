package org.polyfrost.polyweather.util

enum class WeatherType(private val id: Int) {
    CLEAR(0),
    RAIN(1),
    STORM(2),
    SNOW(3);

    companion object {
        fun byId(id: Int): WeatherType {
            for (type in values()) {
                if (id == type.id) return type
            }
            return CLEAR
        }
    }
}
