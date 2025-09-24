package org.polyfrost.polyweather.mixin;

import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatherEffectRenderer.class)
public class Mixin_FixLowTempsWhenSnowing {
    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true)
    private void polyweather$updateTemperatures(Level world, BlockPos pos, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (!PolyWeatherConfig.INSTANCE.enabled || !PolyWeatherClient.isSnowing()) {
            return;
        }

        cir.setReturnValue(Biome.Precipitation.SNOW);
    }
}
