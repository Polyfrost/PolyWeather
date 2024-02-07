package org.polyfrost.polyweather.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import org.polyfrost.polyweather.PolyWeather;
import org.polyfrost.polyweather.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "addRainParticles", at = @At("HEAD"), cancellable = true)
    private void addRainParticles(CallbackInfo ci) {
        if (ModConfig.INSTANCE.enabled && (!PolyWeather.INSTANCE.isRaining() || PolyWeather.INSTANCE.isSnowing())) {
            ci.cancel();
        }
    }

}
