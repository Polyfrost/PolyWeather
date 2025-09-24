package org.polyfrost.polyweather.mixin;

import net.minecraft.world.biome.SingletonBiomeSource;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SingletonBiomeSource.class)
public class Mixin_FixLowTempsWhenSnowing {
    @Inject(method = "method_11533", at = @At("HEAD"), cancellable = true)
    private void polyweather$updateTemperatures(float p_76939_1_, int p_76939_2_, CallbackInfoReturnable<Float> cir) {
        if (PolyWeatherConfig.INSTANCE.enabled && PolyWeatherClient.isSnowing()) {
            cir.setReturnValue(0f);
        }
    }
}
