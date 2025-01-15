package org.polyfrost.polyweather.mixin;

import net.minecraft.world.World;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class Mixin_World_FixRainAndThunderStrength {

    @Inject(method = "getRainStrength", at = @At("HEAD"), cancellable = true)
    private void getRainStrength(float delta, CallbackInfoReturnable<Float> cir) {
        if (PolyWeatherConfig.INSTANCE.enabled)
            cir.setReturnValue(PolyWeatherClient.isSnowing() ? PolyWeatherClient.getSnowStrength() : PolyWeatherClient.isRaining() ? PolyWeatherClient.getRainStrength() : 0f);
    }

    @Inject(method = "getThunderStrength", at = @At("HEAD"), cancellable = true)
    private void getThunderStrength(float delta, CallbackInfoReturnable<Float> cir) {
        if (PolyWeatherConfig.INSTANCE.enabled)
            cir.setReturnValue(PolyWeatherClient.isThundering() ? PolyWeatherClient.getThunderStrength() : 0f);
    }
}
