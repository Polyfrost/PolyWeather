package org.polyfrost.polyweather.command;

import org.polyfrost.polyweather.PolyWeather;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.polyfrost.polyweather.config.WeatherConfig;

@Command(value = PolyWeather.MODID, description = "Access the " + PolyWeather.NAME + " GUI.", aliases = "weatherchanger")
public class WeatherCommand {
    @Main
    private void main() {
        WeatherConfig.getInstance().openGui();
    }
}