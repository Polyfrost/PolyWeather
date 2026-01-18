package org.polyfrost.polyweather.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if >=1.21.4 {
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.world.level.Level;
//?} else {
/*import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
*///?}
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.4 {
@Mixin(WeatherEffectRenderer.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class Mixin_ModifyPrecipitationStrength {
    @WrapOperation(method = /*? if >=1.21.10 {*/ "extractRenderState" /*?} else if >=1.21.4 {*/ /*"render(Lnet/minecraft/world/level/Level;Lnet/minecraft/client/renderer/MultiBufferSource;IFLnet/minecraft/world/phys/Vec3;)V" *//*?} else {*/ /*"renderSnowAndRain" *//*?}*/, at = @At(value = "INVOKE", target = /*? if >=1.21.4 {*/ "Lnet/minecraft/world/level/Level;getRainLevel(F)F" /*?} else {*/ /*"Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F" *//*?}*/))
    private float modifyPrecipitationStrength(/*? if >=1.21.4 {*/ Level /*?} else {*/ /*ClientLevel *//*?}*/ instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return original.call(instance, delta);
    }
}
