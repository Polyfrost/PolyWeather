package org.polyfrost.polyweather.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientLevel.class)
public class Mixin_FixWorldColors {
    @WrapOperation(method = "getSkyColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float fixSkyColors(ClientLevel instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return original.call(instance, delta);
    }

    @WrapOperation(method = "getSkyColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getThunderLevel(F)F"))
    private float fixSkyColorsThunder(ClientLevel instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return original.call(instance, delta);
    }

    @WrapOperation(method = "getCloudColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float fixCloudColors(ClientLevel instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return original.call(instance, delta);
    }

    @WrapOperation(method = "getCloudColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getThunderLevel(F)F"))
    private float fixCloudColorsThunder(ClientLevel instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return original.call(instance, delta);
    }
}
