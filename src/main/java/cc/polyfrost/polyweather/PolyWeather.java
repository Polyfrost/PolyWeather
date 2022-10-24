package cc.polyfrost.polyweather;

import cc.polyfrost.polyweather.command.WeatherCommand;
import cc.polyfrost.polyweather.config.WeatherConfig;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import cc.polyfrost.polyweather.util.IrlWeatherHandler;
import cc.polyfrost.polyweather.util.WeatherType;

@net.minecraftforge.fml.common.Mod(modid = PolyWeather.MODID, name = PolyWeather.NAME, version = PolyWeather.VERSION)
public class PolyWeather {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    @net.minecraftforge.fml.common.Mod.EventHandler
    public void onFMLInitialization(net.minecraftforge.fml.common.event.FMLInitializationEvent event) {
        CommandManager.INSTANCE.registerCommand(new WeatherCommand());
        WeatherConfig.getInstance();
        IrlWeatherHandler.fetchData();
    }

    public static boolean isRaining() {
        if (WeatherConfig.irlWeather) return IrlWeatherHandler.isRaining();
        return WeatherConfig.getWeatherType() != WeatherType.CLEAR;
    }

    public static boolean isThundering() {
        if (WeatherConfig.irlWeather) return IrlWeatherHandler.isThundering();
        return WeatherConfig.getWeatherType() == WeatherType.STORM;
    }

    public static boolean isSnowing() {
        if (WeatherConfig.irlWeather) return IrlWeatherHandler.isSnowing();
        return WeatherConfig.getWeatherType() == WeatherType.SNOW;
    }

    public static float getRainStrength() {
        if (WeatherConfig.irlWeather) return IrlWeatherHandler.getRainStrength();
        return WeatherConfig.rainStrength;
    }

    public static float getThunderStrength() {
        if (WeatherConfig.irlWeather) return IrlWeatherHandler.getThunderStrength();
        return WeatherConfig.thunderStrength;
    }
}
