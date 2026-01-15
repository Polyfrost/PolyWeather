package org.polyfrost.polyweather.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.server.level.ParticleStatus;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatherEffectRenderer.class)
public class Mixin_CancelGroundParticles {
    @Inject(method = "tickRainParticles", at = @At("HEAD"), cancellable = true)
    private void cancelGroundParticles(ClientLevel arg, Camera arg2, int m, ParticleStatus arg3, CallbackInfo ci) {
        if (PolyWeatherConfig.isEnabled() && (!ClientWeatherManager.isRainy() || ClientWeatherManager.isSnowy())) {
            ci.cancel();
        }
    }
}
