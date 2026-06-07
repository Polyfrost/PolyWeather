package org.polyfrost.polyweather.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if >=1.21.4 {
import net.minecraft.client.renderer.WeatherEffectRenderer;
//?} else {
/*import net.minecraft.client.renderer.LevelRenderer;
*///?}
import net.minecraft.client.multiplayer.ClientLevel;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.4 {
@Mixin(WeatherEffectRenderer.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class Mixin_WeatherSound {
    @WrapOperation(method = /*? if >=1.21.4 {*/ "tickRainParticles" /*?} else {*/ /*"tickRain" *//*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float modifyPrecipitationSoundStrength(ClientLevel instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled() && PolyWeatherConfig.getEnableSounds()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return original.call(instance, delta);
    }
}
