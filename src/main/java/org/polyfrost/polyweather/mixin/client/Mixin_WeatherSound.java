package org.polyfrost.polyweather.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
//? if >=1.21.4 <26.2 {
/*import net.minecraft.client.renderer.WeatherEffectRenderer;
*///?} else if <1.21.4 {
/*import net.minecraft.client.renderer.LevelRenderer;
*///?}
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.2 {
@Mixin(ClientLevel.class)
//?} else if >=1.21.4 {
/*@Mixin(WeatherEffectRenderer.class)
*///?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class Mixin_WeatherSound {
    @WrapOperation(method = /*? if >=26.2 {*/ "tickWeatherEffects" /*?} else if >=1.21.4 {*/ /*"tickRainParticles" *//*?} else {*/ /*"tickRain" *//*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float modifyPrecipitationSoundStrength(ClientLevel instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled() && PolyWeatherConfig.getEnableSounds()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return original.call(instance, delta);
    }
}
