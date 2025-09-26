package org.polyfrost.polyweather.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.world.World;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class Mixin_ModifyThunderStrength {
    @Redirect(method = "updateFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getThunderStrength(F)F"))
    private float polyweather$modifyThunderStrength(World instance, float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return instance.getThunderStrength(delta);
    }
}
