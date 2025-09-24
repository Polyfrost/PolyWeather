package org.polyfrost.polyweather.mixin;

import net.minecraft.world.biome.WorldChunkManager;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldChunkManager.class)
public class Mixin_FixLowTempsWhenSnowing {
    @Inject(method = "getTemperatureAtHeight", at = @At("HEAD"), cancellable = true)
    private void polyweather$updateTemperatures(float p_76939_1_, int p_76939_2_, CallbackInfoReturnable<Float> cir) {
        if (PolyWeatherConfig.INSTANCE.enabled && PolyWeatherClient.isSnowing()) {
            cir.setReturnValue(0f);
        }
    }
}
