package org.polyfrost.polyweather.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.LevelRenderer;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class Mixin_FixLowTempsWhenSnowing {
    @ModifyExpressionValue(
            method = "renderSnowAndRain",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;getTemperature(Lnet/minecraft/core/BlockPos;)F"
            )
    )
    private float polyweather$updateTemperatures(float original) {
        if (PolyWeatherConfig.isEnabled() && ClientWeatherManager.isSnowy()) {
            return 0f;
        }

        return original;
    }
}
