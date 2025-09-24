package org.polyfrost.polyweather.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.WorldProperties;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.Properties.class)
public class Mixin_ForceRainAndThunderChecks {
    @Inject(method = "isRaining", at = @At("HEAD"), cancellable = true)
    private void isRaining(CallbackInfoReturnable<Boolean> cir) {
        if (PolyWeatherConfig.INSTANCE.enabled) {
            cir.setReturnValue(PolyWeatherClient.isRaining());
        }
    }

    @Inject(method = "isThundering", at = @At("HEAD"), cancellable = true)
    private void isThundering(CallbackInfoReturnable<Boolean> cir) {
        if (PolyWeatherConfig.INSTANCE.enabled) {
            cir.setReturnValue(PolyWeatherClient.isThundering());
        }
    }
}
