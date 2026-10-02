package org.polyfrost.polyweather.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
//? if >=1.21.8 {
import net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment;
//?} elif >1.8.9 {
/*import net.minecraft.client.renderer.FogRenderer;
*///?} else {
/*import net.minecraft.client.render.GameRenderer;
import net.minecraft.world.level.Level;
*///?}
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.8 {
@Mixin(AtmosphericFogEnvironment.class)
//?} elif >1.8.9 {
/*@Mixin(FogRenderer.class)
*///?} else
//@Mixin(GameRenderer.class)
public class Mixin_ModifyWeatherFog {
    //? if >1.8.9 {
    @WrapOperation(method = /*? if >=1.21.11 {*/ {"getBaseColor", "updateRainFogState"} /*?} else if >=1.21.8 {*/ /*"setupFog" *//*?} else >=1.21.4 {*/ /*"computeFogColor" *//*?} else {*//* "setupColor" *//*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private static float modifyPrecipitationStrength(ClientLevel instance, float delta, Operation<Float> original) {
    //?} else {
    /*@WrapOperation(method = "setupClearColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getRainLevel(F)F"))
    private static float modifyPrecipitationStrength(Level instance, float delta, Operation<Float> original) {
    *///?}
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return original.call(instance, delta);
    }
}
