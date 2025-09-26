package org.polyfrost.polyweather.mixin;

import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.world.level.biome.Biome;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatherEffectRenderer.class)
public class Mixin_ForceSnow {
    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true)
    private void polyweather$forceSnow(CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (PolyWeatherConfig.isEnabled() && ClientWeatherManager.isSnowy()) {
            cir.setReturnValue(Biome.Precipitation.SNOW);
        }
    }
}
