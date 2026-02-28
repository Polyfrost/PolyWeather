package org.polyfrost.polyweather.mixin.client;

//? if >=1.21.11 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.attribute.WeatherAttributes;
//?} else {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
*///?}
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.11 {
@Mixin(WeatherAttributes.WeatherAccess.class)
//?} else {
/*@Mixin(ClientLevel.class)
*///?}
public /*? if >=1.21.11 {*/ interface /*?} else {*//* class *//*?}*/ Mixin_FixWorldColors {
    //? if >=1.21.11 {
    @ModifyReturnValue(method = "from", at = @At("RETURN"))
    private static WeatherAttributes.WeatherAccess fixWeatherAccess(WeatherAttributes.WeatherAccess original) {
        return new WeatherAttributes.WeatherAccess() {
            @Override
            public float rainLevel() {
                if (!PolyWeatherConfig.isEnabled()) return original.rainLevel();
                return ClientWeatherManager.getPrecipitationStrength(1.0f);
            }

            @Override
            public float thunderLevel() {
                if (!PolyWeatherConfig.isEnabled()) return original.thunderLevel();
                return ClientWeatherManager.getStormStrength(1.0f);
            }
        };
    }
    //?} else {
    /*@WrapOperation(method = "getSkyColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
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
    *///?}
}
