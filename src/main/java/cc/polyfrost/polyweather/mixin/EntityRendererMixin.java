package cc.polyfrost.polyweather.mixin;

import cc.polyfrost.polyweather.PolyWeather;
import cc.polyfrost.polyweather.config.WeatherConfig;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "addRainParticles", at = @At("HEAD"), cancellable = true)
    private void addRainParticles(CallbackInfo ci) {
        if (WeatherConfig.getInstance().enabled && (!PolyWeather.isRaining() || PolyWeather.isSnowing())) {
            ci.cancel();
        }
    }
}
