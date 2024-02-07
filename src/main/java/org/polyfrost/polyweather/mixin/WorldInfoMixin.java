package org.polyfrost.polyweather.mixin;

import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.polyfrost.polyweather.PolyWeather;
import org.polyfrost.polyweather.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin(WorldInfo.class)
public class WorldInfoMixin {

    @Inject(method = "isRaining", at = @At("HEAD"), cancellable = true)
    private void isRaining(CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.INSTANCE.enabled) {
            cir.setReturnValue(PolyWeather.INSTANCE.isRaining());
        }
    }

    @Inject(method = "isThundering", at = @At("HEAD"), cancellable = true)
    private void isThundering(CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.INSTANCE.enabled) {
            cir.setReturnValue(PolyWeather.INSTANCE.isThundering());
        }
    }
}
