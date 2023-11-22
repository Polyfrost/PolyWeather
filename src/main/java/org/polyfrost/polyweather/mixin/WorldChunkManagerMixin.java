package org.polyfrost.polyweather.mixin;

import org.polyfrost.polyweather.PolyWeather;
import org.polyfrost.polyweather.config.WeatherConfig;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//#if MC>=11200
//$$ import net.minecraft.world.biome.BiomeProvider;
//#else
import net.minecraft.world.biome.WorldChunkManager;
//#endif

@SideOnly(Side.CLIENT)
//#if MC>=11200
//$$ @Mixin(BiomeProvider.class)
//#else
@Mixin(WorldChunkManager.class)
//#endif
public class WorldChunkManagerMixin {
    @Inject(method = "getTemperatureAtHeight", at = @At("HEAD"), cancellable = true)
    private void getTemperatureAtHeight(float p_76939_1_, int p_76939_2_, CallbackInfoReturnable<Float> cir) {
        if (WeatherConfig.getInstance().enabled && PolyWeather.isSnowing()) {
            cir.setReturnValue(0f);
        }
    }
}
