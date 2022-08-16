package cc.polyfrost.polyweather.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.polyweather.PolyWeather;
import cc.polyfrost.polyweather.util.IrlWeatherHandler;
import cc.polyfrost.polyweather.util.WeatherType;

public class WeatherConfig extends Config {
    @Exclude
    private static WeatherConfig INSTANCE = null;

    public static WeatherConfig getInstance() {
        return INSTANCE == null ? (INSTANCE = new WeatherConfig()) : INSTANCE;
    }

    @Dropdown(
            name = "Weather",
            options = {"Clear", "Rain", "Storm", "Snow"}
    )
    public static int weather = 0;

    @Checkbox(name = "Use IRL weather")
    public static boolean irlWeather = false;

    @Slider(
            name = "Rain/Snow intensity",
            min = 0f, max = 1f
    )
    public static float rainStrength = 1f;

    @Slider(
            name = "Thunder intensity",
            min = 0f, max = 1f
    )
    public static float thunderStrength = 1f;


    public static WeatherType getWeatherType() {
        return WeatherType.byId(weather);
    }

    public WeatherConfig() {
        super(new Mod("Weather Changer", ModType.UTIL_QOL), PolyWeather.MODID + ".json", false);
        initialize();
        addDependency("rainStrength", () -> getWeatherType() != WeatherType.CLEAR);
        addDependency("thunderStrength", () -> getWeatherType() == WeatherType.STORM);
        addDependency("rainStrength", () -> !irlWeather);
        addDependency("thunderStrength", () -> !irlWeather);
        addDependency("weather", () -> !irlWeather);
        addListener("irlWeather", IrlWeatherHandler::fetchData);
    }
}

