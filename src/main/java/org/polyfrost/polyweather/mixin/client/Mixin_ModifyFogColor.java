package org.polyfrost.polyweather.mixin.client;

//? if >=1.21.8 <=1.21.10 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.fog.environment.AirBasedFogEnvironment;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.injection.At;
*///?}
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;

//? if >=1.21.8 <=1.21.10 {
/*@Mixin(AirBasedFogEnvironment.class)
*///?} else {
@Mixin(ClientLevel.class)
//?}
public class Mixin_ModifyFogColor {
    //? if >=1.21.8 <=1.21.10 {
    /*@WrapOperation(method = "getBaseColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private static float modifyPrecipitationStrength(ClientLevel instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return original.call(instance, delta);
    }
    *///?}
}
