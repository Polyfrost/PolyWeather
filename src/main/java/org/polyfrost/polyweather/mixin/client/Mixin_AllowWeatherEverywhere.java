package org.polyfrost.polyweather.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if >=1.21.4 {
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import org.polyfrost.polyweather.client.ClientWeatherManager;
//?} else {
/*import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.biome.Biome;
*///?}
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=1.21.4 {
@Mixin(WeatherEffectRenderer.class)
//?} else {
/*@Mixin(LevelRenderer.class)
*///?}
public class Mixin_AllowWeatherEverywhere {
    @WrapOperation(method = /*? if >=1.21.4 {*/ "tickRainParticles" /*?} else {*/ /*"renderSnowAndRain" *//*?}*/, at = @At(value = "INVOKE", target = /*? if >=1.21.4 {*/ "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F" /*?} else {*/ /*"Lnet/minecraft/world/level/biome/Biome;hasPrecipitation()Z" *//*?}*/))
    private /*? if >=1.21.4 {*/ float /*?} else {*/ /*boolean *//*?}*/ allowRainEverywhere(/*? if >=1.21.4 {*/ ClientLevel instance, float delta /*?} else {*/ /*Biome instance *//*?}*/, Operation</*? if >=1.21.4 {*/ Float /*?} else {*/ /*Boolean *//*?}*/> original) {
        if (PolyWeatherConfig.isEnabled()) {
            //? if >=1.21.4 {
            return ClientWeatherManager.getPrecipitationStrength(delta);
            //?} else {
            /*return true;
            *///?}
        }

        return original.call(instance /*? if >=1.21.4 {*/, delta /*?}*/);
    }
}
