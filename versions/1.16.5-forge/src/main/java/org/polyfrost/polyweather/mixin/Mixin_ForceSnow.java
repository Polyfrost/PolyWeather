package org.polyfrost.polyweather.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class Mixin_ForceSnow {
    @Redirect(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getTemperature(Lnet/minecraft/core/BlockPos;)F"))
    private float polyweather$forceSnow(Biome instance, BlockPos pos) {
        if (PolyWeatherConfig.isEnabled() && ClientWeatherManager.isSnowy()) {
            return 0.0f;
        }

        return instance.getTemperature(pos);
    }
}
