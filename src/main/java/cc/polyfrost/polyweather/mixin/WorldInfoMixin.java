package cc.polyfrost.polyweather.mixin;

import cc.polyfrost.polyweather.PolyWeather;
import cc.polyfrost.polyweather.config.WeatherConfig;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin(WorldInfo.class)
public class WorldInfoMixin {

    @Inject(method = "isRaining", at = @At("HEAD"), cancellable = true)
    private void isRaining(CallbackInfoReturnable<Boolean> cir) {
        if (WeatherConfig.getInstance().enabled) {
            cir.setReturnValue(PolyWeather.isRaining());
        }
    }

    @Inject(method = "isThundering", at = @At("HEAD"), cancellable = true)
    private void isThundering(CallbackInfoReturnable<Boolean> cir) {
        if (WeatherConfig.getInstance().enabled) {
            cir.setReturnValue(PolyWeather.isThundering());
        }
    }
}
