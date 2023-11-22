package org.polyfrost.polyweather.mixin;

import org.polyfrost.polyweather.PolyWeather;
import org.polyfrost.polyweather.config.WeatherConfig;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin(World.class)
public class WorldMixin {
    @Inject(method = "getRainStrength", at = @At("HEAD"), cancellable = true)
    private void getRainStrength(float delta, CallbackInfoReturnable<Float> cir) {
        if (WeatherConfig.getInstance().enabled && !PolyWeather.isRaining()) {
            cir.setReturnValue(0f);
        } else if (WeatherConfig.getInstance().enabled) {
            cir.setReturnValue(PolyWeather.getRainStrength());
        }
    }

    @Inject(method = "getThunderStrength", at = @At("HEAD"), cancellable = true)
    private void getThunderStrength(float delta, CallbackInfoReturnable<Float> cir) {
        if (WeatherConfig.getInstance().enabled && !PolyWeather.isThundering()) {
            cir.setReturnValue(0f);
        } else if (WeatherConfig.getInstance().enabled) {
            cir.setReturnValue(PolyWeather.getThunderStrength());
        }
    }
}
