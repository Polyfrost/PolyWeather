package org.polyfrost.polyweather.mixin.client;

import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.world.level.Level;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WeatherEffectRenderer.class)
public class Mixin_ModifyPrecipitationStrength {
    @Redirect(method = /*? if >=1.21.10 {*/ "extractRenderState" /*?} else {*/ /*"render(Lnet/minecraft/world/level/Level;Lnet/minecraft/client/renderer/MultiBufferSource;IFLnet/minecraft/world/phys/Vec3;)V" *//*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getRainLevel(F)F"))
    private float polyweather$modifyPrecipitationStength(Level instance, float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return instance.getRainLevel(delta);
    }
}
