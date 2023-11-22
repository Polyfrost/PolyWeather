package org.polyfrost.polyweather.util;

/**
 * <a href="https://open-meteo.com/en/docs">code docs</a>
 * Light/Slight = 0.25
 * Moderate = 0.6
 * Dense = 0.8
 * Heavy = 1
 */
public enum WMOCode {
    CLEAR(0),
    MAINLY_CLEAR(1),
    PARTLY_CLOUDY(2),
    OVERCAST(3),
    FOG(45),
    DEPOSITING_RIME_FOG(48),
    LIGHT_DRIZZLE(51, 0.25f),
    MODERATE_DRIZZLE(53, 0.6f),
    DENSE_DRIZZLE(55, 0.8f),
    LIGHT_FREEZING_DRIZZLE(56, 0.25f, true),
    DENSE_FREEZING_DRIZZLE(57, 0.8f, true),
    SLIGHT_RAIN(61, 0.25f),
    MODERATE_RAIN(63, 0.6f),
    HEAVY_RAIN(65, 1f),
    LIGHT_FREEZING_RAIN(66, 0.25f, true),
    HEAVY_FREEZING_RAIN(67, 1f, true),
    SLIGHT_SNOW(71, 0.25f, true),
    MODERATE_SNOW(73, 0.6f, true),
    HEAVY_SNOW(75, 1f, true),
    SNOW_GRAINS(77, 0.25f, true),
    RAIN_SHOWER_SLIGHT(80, 0.25f),
    RAIN_SHOWER_MODERATE(81, 0.6f),
    RAIN_SHOWER_VIOLENT(82, 1f),
    SNOW_SHOWER_SLIGHT(85, 0.25f),
    SNOW_SHOWER_HEAVY(86, 1f),
    THUNDERSTORM(95, 0.6f, 0.6f),
    SLIGHT_HAIL_THUNDERSTORM(96, 0.6f, 0.25f),
    HEAVY_HAIL_THUNDERSTORM(99, 1f, 1f);

    public final int code;
    public final float rainStrength;
    public final boolean snow;
    public final float thunderStrength;

    WMOCode(int code, float rainStrength, boolean snow, float thunderStrength) {
        this.code = code;
        this.rainStrength = rainStrength;
        this.snow = snow;
        this.thunderStrength = thunderStrength;
    }

    WMOCode(int code, float rainStrength, float thunderStrength) {
        this(code, rainStrength, false, thunderStrength);
    }

    WMOCode(int code, float rainStrength, boolean snow) {
        this(code, rainStrength, snow, 0f);
    }

    WMOCode(int code, float rainStrength) {
        this(code, rainStrength, false);
    }

    WMOCode(int code) {
        this(code, 0f);
    }

    public static WMOCode fromCode(int code) {
        for (WMOCode WMOCode : values()) {
            if (code == WMOCode.code) return WMOCode;
        }
        return CLEAR;
    }
}
