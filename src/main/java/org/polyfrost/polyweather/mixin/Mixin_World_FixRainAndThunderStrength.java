package org.polyfrost.polyweather.mixin;

//#if MC > 1.12.2 && MC < 1.21.4
//$$ import net.minecraft.client.MinecraftClient;
//#endif
//#if MC <= 1.12.2
import net.minecraft.world.World;
//#else
//$$ import net.minecraft.world.World;
//#endif
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//#if MC <= 1.12.2 || MC >= 1.21.4
@Mixin(World.class)
//#else
//$$ @Mixin(MinecraftClient.class)
//#endif
public class Mixin_World_FixRainAndThunderStrength {
    //#if MC <= 1.12.2 || MC >= 1.21.4
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
    //#endif
}
