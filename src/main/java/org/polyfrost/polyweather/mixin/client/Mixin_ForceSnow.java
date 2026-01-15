package org.polyfrost.polyweather.mixin.client;

//? if >=1.21.4 {
import net.minecraft.client.renderer.WeatherEffectRenderer;
//?} else {
/*import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
*///?}
import net.minecraft.world.level.biome.Biome;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.4 {
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
 //?} else {
/*import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
*///?}

//? if >=1.21.4 {
@Mixin(WeatherEffectRenderer.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class Mixin_ForceSnow {
    //? if >=1.21.4 {
    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true)
    private void forceSnow(CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (PolyWeatherConfig.isEnabled() && ClientWeatherManager.isSnowy()) {
            cir.setReturnValue(Biome.Precipitation.SNOW);
        }
    }
    //?} else {
    /*@Redirect(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    private Biome.Precipitation polyweather$forceSnow(Biome instance, BlockPos pos) {
        if (PolyWeatherConfig.isEnabled() && ClientWeatherManager.isSnowy()) {
            return Biome.Precipitation.SNOW;
        }

        return instance.getPrecipitationAt(pos);
    }
    *///?}
}
