package org.polyfrost.polyweather.mixin;

import net.minecraft.client.render.WorldRenderer;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class Mixin_ForceRainAndSnowParticles {
    @Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
    private void addRainParticles(CallbackInfo ci) {
        if (PolyWeatherConfig.INSTANCE.enabled && (!ClientWeatherManager.isRainy() || ClientWeatherManager.isSnowy())) {
            ci.cancel();
        }
    }
}
