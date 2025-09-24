package org.polyfrost.polyweather.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.WorldRenderer;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldRenderer.class)
public class Mixin_FixLowTempsWhenSnowing {
    @ModifyExpressionValue(
            method = "renderWeather",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/biome/Biome;getTemperature(Lnet/minecraft/util/math/BlockPos;)F"
            )
    )
    private float polyweather$updateTemperatures(float original) {
        if (PolyWeatherConfig.INSTANCE.enabled && PolyWeatherClient.isSnowing()) {
            return 0f;
        }

        return original;
    }
}
