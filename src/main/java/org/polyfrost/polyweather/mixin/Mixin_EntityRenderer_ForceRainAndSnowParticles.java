package org.polyfrost.polyweather.mixin;

//#if MC >= 1.21.4
//$$ import net.minecraft.client.MinecraftClient;
//#endif
import net.minecraft.client.renderer.EntityRenderer;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 1.21.4
//$$ @Mixin(MinecraftClient.class)
//#else
@Mixin(EntityRenderer.class)
//#endif
public class Mixin_EntityRenderer_ForceRainAndSnowParticles {
    //#if MC <= 1.21.2
    @Inject(method = "addRainParticles", at = @At("HEAD"), cancellable = true)
    private void addRainParticles(CallbackInfo ci) {
        if (PolyWeatherConfig.INSTANCE.enabled && (!PolyWeatherClient.isRaining() || PolyWeatherClient.isSnowing())) {
            ci.cancel();
        }
    }
    //#endif
}
