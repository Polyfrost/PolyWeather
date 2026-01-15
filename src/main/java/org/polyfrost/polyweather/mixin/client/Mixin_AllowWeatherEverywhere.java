package org.polyfrost.polyweather.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WeatherEffectRenderer.class)
public class Mixin_AllowWeatherEverywhere {
    @Redirect(method = "tickRainParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float allowRainEverywhere(ClientLevel instance, float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return instance.getRainLevel(delta);
    }
}
