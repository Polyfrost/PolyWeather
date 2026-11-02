package org.polyfrost.polyweather.mixin.client;

//? if =1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.level.biome.Biome;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.injection.At;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "dev.rdh.argentum.impl.render.environment.WeatherRenderer")
public class Mixin_ArgentumWeather {
    //? if =1.8.9 {
    /*@WrapOperation(method = "rebuild", remap = false, require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;hasPrecipitation()Z", remap = true))
    private boolean allowWeatherEverywhere(Biome instance, Operation<Boolean> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return true;
        }

        return original.call(instance);
    }

    @WrapOperation(method = "rebuild", remap = false, require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/source/BiomeSource;adjustTemperatureForHeight(FI)F", remap = true))
    private float changeWeather(BiomeSource instance, float temperature, int height, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.isSnowy() ? 0.0F : 0.15F;
        }

        return original.call(instance, temperature, height);
    }
    *///?}
}
