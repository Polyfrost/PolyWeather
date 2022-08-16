package cc.polyfrost.polyweather.mixin;

import cc.polyfrost.polyweather.PolyWeather;
import cc.polyfrost.polyweather.config.WeatherConfig;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin(WorldChunkManager.class)
public class WorldChunkManagerMixin {
    @Inject(method = "getTemperatureAtHeight", at = @At("HEAD"), cancellable = true)
    private void getTemperatureAtHeight(float p_76939_1_, int p_76939_2_, CallbackInfoReturnable<Float> cir) {
        if (WeatherConfig.getInstance().enabled && PolyWeather.isSnowing()) {
            cir.setReturnValue(0f);
        }
    }
}
