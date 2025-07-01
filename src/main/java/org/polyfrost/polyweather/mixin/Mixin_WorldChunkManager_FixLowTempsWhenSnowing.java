package org.polyfrost.polyweather.mixin;

//#if MC >= 1.21.4
//$$ import net.minecraft.client.MinecraftClient;
//#endif
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//#if MC >= 1.12
//$$ import net.minecraft.world.biome.BiomeProvider;
//#else
import net.minecraft.world.biome.WorldChunkManager;
//#endif

//#if MC >= 1.21.4
//$$ @Mixin(MinecraftClient.class)
//#elseif MC >= 1.12
//$$ @Mixin(BiomeProvider.class)
//#else
@Mixin(WorldChunkManager.class)
//#endif
public class Mixin_WorldChunkManager_FixLowTempsWhenSnowing {
    //#if MC <= 1.21.2
    @Inject(method = "getTemperatureAtHeight", at = @At("HEAD"), cancellable = true)
    private void getTemperatureAtHeight(float p_76939_1_, int p_76939_2_, CallbackInfoReturnable<Float> cir) {
        if (PolyWeatherConfig.INSTANCE.enabled && PolyWeatherClient.isSnowing()) {
            cir.setReturnValue(0f);
        }
    }
    //#endif
}
