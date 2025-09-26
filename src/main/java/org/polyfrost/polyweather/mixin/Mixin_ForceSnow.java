package org.polyfrost.polyweather.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.world.biome.WorldChunkManager;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class Mixin_ForceSnow {
    @Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/WorldChunkManager;getTemperatureAtHeight(FI)F"))
    private float polyweather$forceSnow(WorldChunkManager instance, float temp, int y) {
        if (PolyWeatherConfig.isEnabled() && ClientWeatherManager.isSnowy()) {
            return 0.0f;
        }

        return instance.getTemperatureAtHeight(temp, y);
    }
}
