package org.polyfrost.polyweather.mixin;

import net.minecraft.client.render.WeatherRendering;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatherRendering.class)
public class Mixin_FixLowTempsWhenSnowing {
    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true)
    private void polyweather$updateTemperatures(World world, BlockPos pos, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (!PolyWeatherConfig.INSTANCE.enabled || !PolyWeatherClient.isSnowing()) {
            return;
        }

        cir.setReturnValue(Biome.Precipitation.SNOW);
    }
}
