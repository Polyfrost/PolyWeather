package cc.polyfrost.polyweather.util;

public enum WeatherType {
    CLEAR(0),
    RAIN(1),
    STORM(2),
    SNOW(3);

    private final int id;

    WeatherType(int id) {
        this.id = id;
    }

    public static WeatherType byId(int id) {
        for (WeatherType type : values()) {
            if (id == type.id) return type;
        }
        return CLEAR;
    }
}
