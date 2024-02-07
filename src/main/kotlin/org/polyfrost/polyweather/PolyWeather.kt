package org.polyfrost.polyweather

import cc.polyfrost.oneconfig.utils.commands.CommandManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.polyfrost.polyweather.command.WeatherCommand
import org.polyfrost.polyweather.config.ModConfig
import org.polyfrost.polyweather.util.*

@Mod(modid = PolyWeather.MODID, name = PolyWeather.NAME, version = PolyWeather.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object PolyWeather {

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent?) {
        CommandManager.INSTANCE.registerCommand(WeatherCommand)
        ModConfig
        IrlWeatherHandler.fetchData()
    }

    const val MODID: String = "@ID@"
    const val NAME: String = "@NAME@"
    const val VERSION: String = "@VER@"

    val isRaining: Boolean
        get() {
            if (ModConfig.irlWeather) return IrlWeatherHandler.isRaining
            return ModConfig.getWeatherType() != WeatherType.CLEAR
        }

    val isThundering: Boolean
        get() {
            if (ModConfig.irlWeather) return IrlWeatherHandler.isThundering
            return ModConfig.getWeatherType() == WeatherType.STORM
        }

    val isSnowing: Boolean
        get() {
            if (ModConfig.irlWeather) return IrlWeatherHandler.isSnowing
            return ModConfig.getWeatherType() == WeatherType.SNOW
        }

    val rainStrength: Float
        get() {
            if (ModConfig.irlWeather) return IrlWeatherHandler.rainStrength
            return ModConfig.rainStrength
        }

    val snowStrength: Float
        get() {
            if (ModConfig.irlWeather) return IrlWeatherHandler.rainStrength
            return ModConfig.snowStrength
        }

    val thunderStrength: Float
        get() {
            if (ModConfig.irlWeather) return IrlWeatherHandler.thunderStrength
            return ModConfig.thunderStrength
        }

}
