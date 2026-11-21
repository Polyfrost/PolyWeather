package org.polyfrost.polyweather.mixin.client;

//? if =1.8.9 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.Level;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.injection.At;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "dev.rdh.cera.modules.colors.LightMaps")
public class Mixin_CeraLightMaps {
    //? if =1.8.9 {
    /*@WrapOperation(method = "compute", remap = false, require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getRainLevel(F)F", remap = true))
    private float modifyRainStrength(Level instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return original.call(instance, delta);
    }

    @WrapOperation(method = "compute", remap = false, require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getThunderLevel(F)F", remap = true))
    private float modifyThunderStrength(Level instance, float delta, Operation<Float> original) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return original.call(instance, delta);
    }
    *///?}
}
