package org.polyfrost.polyweather.mixin;

import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.polyfrost.polyweather.PolyWeather;
import org.polyfrost.polyweather.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin(World.class)
public class WorldMixin {

    @Unique
    private PolyWeather polyWeather = PolyWeather.INSTANCE;

    @Inject(method = "getRainStrength", at = @At("HEAD"), cancellable = true)
    private void getRainStrength(float delta, CallbackInfoReturnable<Float> cir) {
        if (ModConfig.INSTANCE.enabled)
            cir.setReturnValue(polyWeather.isSnowing() ? polyWeather.getSnowStrength() : polyWeather.isRaining() ? polyWeather.getRainStrength() : 0f);
    }

    @Inject(method = "getThunderStrength", at = @At("HEAD"), cancellable = true)
    private void getThunderStrength(float delta, CallbackInfoReturnable<Float> cir) {
        if (ModConfig.INSTANCE.enabled)
            cir.setReturnValue(polyWeather.isThundering() ? polyWeather.getThunderStrength() : 0f);
    }
}
