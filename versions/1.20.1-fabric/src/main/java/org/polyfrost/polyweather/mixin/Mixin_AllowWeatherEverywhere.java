package org.polyfrost.polyweather.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.world.biome.Biome;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class Mixin_AllowWeatherEverywhere {
    @Redirect(method = "renderWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;hasPrecipitation()Z"))
    private boolean polyweather$allowRainEverywhere(Biome instance) {
        if (PolyWeatherConfig.isEnabled()) {
            return true;
        }

        return instance.hasPrecipitation();
    }
}
