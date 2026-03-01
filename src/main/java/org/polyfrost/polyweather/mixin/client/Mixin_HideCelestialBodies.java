package org.polyfrost.polyweather.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
//? if >=1.21.10 {
import net.minecraft.client.renderer.SkyRenderer;
//?} else
/*import net.minecraft.client.renderer.LevelRenderer;*/
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.10 {
@Mixin(SkyRenderer.class)
//?} else
/*@Mixin(LevelRenderer.class)*/
public class Mixin_HideCelestialBodies {
    @WrapOperation(method = /*? if >=1.21.10 {*/ "extractRenderState" /*?} else if >=1.21.4 {*/ /*"method_62215" *//*?} else {*//* "renderSky" *//*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float modifyRainStrength(ClientLevel instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return original.call(instance, delta);
    }
}
