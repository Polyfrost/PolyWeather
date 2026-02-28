package org.polyfrost.polyweather.mixin.client;

//? if >=1.21.4 {
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.server.level.ParticleStatus;
//?} else {
/*import net.minecraft.client.renderer.LevelRenderer;
*///?}
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.4 {
@Mixin(WeatherEffectRenderer.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class Mixin_CancelGroundParticles {
    @Inject(method = /*? if >=1.21.4 {*/ "tickRainParticles" /*?} else {*/ /*"tickRain" *//*?}*/, at = @At("HEAD"), cancellable = true)
    private void cancelGroundParticles(/*? if >=1.21.4 {*/ ClientLevel clientLevel, Camera camera, int m, ParticleStatus particleStatus, /*?}*/ /*? if >=1.21.11 {*/ int j, /*?}*/ CallbackInfo ci) {
        if (PolyWeatherConfig.isEnabled() && (!ClientWeatherManager.isRainy() || ClientWeatherManager.isSnowy())) {
            ci.cancel();
        }
    }
}
