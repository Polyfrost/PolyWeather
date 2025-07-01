package org.polyfrost.polyweather.mixin;

//#if MC >= 1.21.4
//$$ import net.minecraft.client.render.WeatherRendering;
//#else
import net.minecraft.client.Minecraft;
//#endif
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

//#if MC >= 1.21.4
//$$ @Mixin(WeatherRendering.class)
//#else
@Mixin(Minecraft.class)
//#endif
public class WeatherRenderingMixin {
    //#if MC >= 1.21.4
    //$$ @ModifyArg(method = "renderPrecipitation(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/util/math/Vec3d;IFLjava/util/List;Ljava/util/List;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WeatherRendering;renderPieces(Lnet/minecraft/client/render/VertexConsumer;Ljava/util/List;Lnet/minecraft/util/math/Vec3d;FIF)V", ordinal = 0), index = 5)
    //$$ private float maybeRainStrength(float f) {
    //$$     return PolyWeatherClient.isRaining() ? PolyWeatherClient.getRainStrength() : 0f;
    //$$ }
    //$$
    //$$ @ModifyArg(method = "renderPrecipitation(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/util/math/Vec3d;IFLjava/util/List;Ljava/util/List;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WeatherRendering;renderPieces(Lnet/minecraft/client/render/VertexConsumer;Ljava/util/List;Lnet/minecraft/util/math/Vec3d;FIF)V", ordinal = 1), index = 5)
    //$$ private float maybeSnowStrength(float f) {
    //$$     return PolyWeatherClient.isSnowing() ? PolyWeatherClient.getSnowStrength() : 0f;
    //$$ }
    //#endif
}
