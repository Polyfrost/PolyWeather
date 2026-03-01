package org.polyfrost.polyweather.mixin.client;

//? if >=1.21.4 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.WeatherEffectRenderer;
//?} else {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
*///?}
import net.minecraft.world.level.biome.Biome;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.4 {
@Mixin(WeatherEffectRenderer.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class Mixin_AllowWeatherEverywhere {
    //? if >=1.21.4 {
    @ModifyReturnValue(method = "getPrecipitationAt", at = @At("RETURN"))
    //?} else {
    /*@WrapOperation(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    *///?}
    private Biome.Precipitation changeWeather(/*? if >=1.21.4 {*/ Biome.Precipitation original /*?} else {*/ /*Biome instance, BlockPos pos, Operation<Biome.Precipitation> original *//*?}*/) {
        if (PolyWeatherConfig.isEnabled()) {
            if (ClientWeatherManager.isSnowy()) {
                return Biome.Precipitation.SNOW;
            } else if (ClientWeatherManager.isRainy()) {
                return Biome.Precipitation.RAIN;
            } else {
                return Biome.Precipitation.NONE;
            }
        }


        return original/*? if <1.21.4 {*//*.call(instance, pos) *//*?}*/;
    }

    //? if <1.21.4 {
    /*@WrapOperation(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;hasPrecipitation()Z"))
    private boolean allowWeatherEverywhere(Biome instance, Operation<Boolean> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return true;
        }

        return original.call(instance);
    }
    *///?}
}
