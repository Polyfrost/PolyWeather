package org.polyfrost.polyweather.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
//? if >=1.21.11 {
import net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment;
//?} else if >=1.21.8 {
/*import net.minecraft.client.renderer.fog.environment.AirBasedFogEnvironment;
*///?} else {
/*import net.minecraft.client.renderer.FogRenderer;
*///?}
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.11 {
@Mixin(AtmosphericFogEnvironment.class)
//?} else if >=1.21.8 {
/*@Mixin(AirBasedFogEnvironment.class)
*///?} else {
/*@Mixin(FogRenderer.class)
*///?}
public class Mixin_ModifyThunderStrength {
    @WrapOperation(method = /*? if >=1.21.8 {*/ "getBaseColor" /*?} else if >=1.21.4 {*/ /*"computeFogColor" *//*?} else {*/ /*"setupColor" *//*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getThunderLevel(F)F"))
    private static float modifyThunderStrength(ClientLevel instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return original.call(instance, delta);
    }
}
