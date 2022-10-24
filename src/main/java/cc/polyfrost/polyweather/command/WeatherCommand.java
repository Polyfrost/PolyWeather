package cc.polyfrost.polyweather.command;

import cc.polyfrost.polyweather.PolyWeather;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import cc.polyfrost.polyweather.config.WeatherConfig;

@Command(value = PolyWeather.MODID, description = "Access the " + PolyWeather.NAME + " GUI.")
public class WeatherCommand {
    @Main
    private void main() {
        WeatherConfig.getInstance().openGui();
    }
}