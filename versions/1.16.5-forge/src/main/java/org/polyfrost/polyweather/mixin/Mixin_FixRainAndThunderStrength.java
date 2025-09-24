package org.polyfrost.polyweather.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class Mixin_FixRainAndThunderStrength extends Level {
    protected Mixin_FixRainAndThunderStrength(WritableLevelData arg, ResourceKey<Level> arg2, DimensionType arg3, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l) {
        super(arg, arg2, arg3, supplier, bl, bl2, l);
    }

    @Override
    public float getRainLevel(float f) {
        if (PolyWeatherConfig.INSTANCE.enabled) {
            return PolyWeatherClient.isSnowing() ? PolyWeatherClient.getSnowStrength() : PolyWeatherClient.isRaining() ? PolyWeatherClient.getRainStrength() : 0f;
        }

        return super.getRainLevel(f);
    }

    @Override
    public float getThunderLevel(float f) {
        if (PolyWeatherConfig.INSTANCE.enabled) {
            return PolyWeatherClient.isThundering() ? PolyWeatherClient.getThunderStrength() : 0f;
        }

        return super.getThunderLevel(f);
    }
}
