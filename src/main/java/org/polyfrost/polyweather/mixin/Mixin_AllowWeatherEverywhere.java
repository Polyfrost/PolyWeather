package org.polyfrost.polyweather.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.world.biome.BiomeGenBase;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class Mixin_AllowWeatherEverywhere {
    @Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeGenBase;canRain()Z"))
    private boolean polyweather$allowRainEverywhere(BiomeGenBase instance) {
        if (PolyWeatherConfig.isEnabled()) {
            return true;
        }

        return instance.canRain();
    }

    @Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeGenBase;getEnableSnow()Z"))
    private boolean polyweather$allowSnowEverywhere(BiomeGenBase instance) {
        if (PolyWeatherConfig.isEnabled()) {
            return true;
        }

        return instance.getEnableSnow();
    }
}
