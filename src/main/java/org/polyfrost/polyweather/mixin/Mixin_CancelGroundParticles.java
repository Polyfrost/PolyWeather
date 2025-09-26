package org.polyfrost.polyweather.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class Mixin_CancelGroundParticles {
    @Inject(method = "addRainParticles", at = @At("HEAD"), cancellable = true)
    private void polyweather$cancelGroundParticles(CallbackInfo ci) {
        if (PolyWeatherConfig.isEnabled() && (!ClientWeatherManager.isRainy() || ClientWeatherManager.isSnowy())) {
            ci.cancel();
        }
    }
}
