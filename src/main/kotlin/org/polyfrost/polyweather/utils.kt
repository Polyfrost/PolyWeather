package org.polyfrost.polyweather

import java.time.Instant
import java.util.Calendar

val currentHour: Instant
    get() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.toInstant()
    }

val nextHour: Instant
    get() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, 1)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.toInstant()
    }

val currentTime: Float
    get() = Calendar.getInstance().get(Calendar.MINUTE) / 60f + Calendar.getInstance().get(Calendar.SECOND) / 3600f + Calendar.getInstance().get(Calendar.MILLISECOND) / 3600000f

fun interpolate(f1: Float, f2: Float, t: Float): Float {
    return f1 * (1 - t) + f2 * t
}
