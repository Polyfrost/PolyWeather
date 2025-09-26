package org.polyfrost.polyweather.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.biome.Biome;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class Mixin_AllowWeatherEverywhere {
    @Redirect(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitation()Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    private Biome.Precipitation polyweather$allowRainEverywhere(Biome instance) {
        if (PolyWeatherConfig.isEnabled()) {
            // Any precipitation type will do, as long as it's not NONE
            return Biome.Precipitation.RAIN;
        }

        return instance.getPrecipitation();
    }
}
