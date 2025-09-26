package org.polyfrost.polyweather.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class Mixin_ForceSnow {
    @Redirect(method = "renderWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;getTemperature(Lnet/minecraft/util/math/BlockPos;)F"))
    private float polyweather$forceSnow(Biome instance, BlockPos pos) {
        if (PolyWeatherConfig.isEnabled() && ClientWeatherManager.isSnowy()) {
            return 0.0f;
        }

        return instance.getTemperature(pos);
    }
}
