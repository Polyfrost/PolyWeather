package org.polyfrost.polyweather

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

val currentHour: Instant
    get() = Instant.now().truncatedTo(ChronoUnit.HOURS)

val nextHour: Instant
    get() = currentHour.plus(1, ChronoUnit.HOURS)

val currentTime: Float
    get() {
        val now = Instant.now()
        return Duration.between(now.truncatedTo(ChronoUnit.HOURS), now).toMillis() / 3600000f
    }

fun interpolate(f1: Float, f2: Float, t: Float): Float {
    return f1 * (1 - t) + f2 * t
}
