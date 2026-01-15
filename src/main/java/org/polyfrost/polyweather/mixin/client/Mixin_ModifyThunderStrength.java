package org.polyfrost.polyweather.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
//? if >=1.21.8 {
import net.minecraft.client.renderer.fog.environment.AirBasedFogEnvironment;
//?} else {
/*import net.minecraft.client.renderer.FogRenderer;
*///?}
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//? if >=1.21.8 {
@Mixin(AirBasedFogEnvironment.class)
//?} else {
/*@Mixin(FogRenderer.class)
*///?}
public class Mixin_ModifyThunderStrength {
    @Redirect(method = /*? if >=1.21.8 {*/ "getBaseColor" /*?} else if >=1.21.4 {*/ /*"computeFogColor" *//*?} else {*/ /*"setupColor" *//*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getThunderLevel(F)F"))
    private static float modifyThunderStrength(ClientLevel instance, float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return instance.getThunderLevel(delta);
    }
}
