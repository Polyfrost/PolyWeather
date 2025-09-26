package org.polyfrost.polyweather.mixin;

import net.minecraft.world.World;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public class Mixin_FixWorldColors {
    @Redirect(
            //#if FORGE
            method = "getSkyColorBody",
            //#else
            //$$ method = "method_3631",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getRainStrength(F)F"
            )
    )
    private float polyweather$fixSkyColors(World instance, float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return instance.getRainStrength(delta);
    }

    @Redirect(
            //#if FORGE
            method = "getSkyColorBody",
            //#else
            //$$ method = "method_3631",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getThunderStrength(F)F"
            )
    )
    private float polyweather$fixSkyColorsThunder(World instance, float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return instance.getThunderStrength(delta);
    }

    @Redirect(
            //#if FORGE
            method = "drawCloudsBody",
            //#else
            //$$ method = "getCloudColor",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getRainStrength(F)F"
            )
    )
    private float polyweather$fixCloudColors(World instance, float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return instance.getRainStrength(delta);
    }

    @Redirect(
            //#if FORGE
            method = "drawCloudsBody",
            //#else
            //$$ method = "getCloudColor",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getThunderStrength(F)F"
            )
    )
    private float polyweather$fixCloudColorsThunder(World instance, float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return instance.getThunderStrength(delta);
    }
}
