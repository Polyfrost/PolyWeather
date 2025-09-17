package org.polyfrost.polyweather.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.MutableWorldProperties;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class Mixin_FixRainAndThunderStrength extends World {
    protected Mixin_FixRainAndThunderStrength(MutableWorldProperties mutableWorldProperties, RegistryKey<World> registryKey, RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> supplier, boolean bl, boolean bl2, long l, int i) {
        super(mutableWorldProperties, registryKey, registryEntry, supplier, bl, bl2, l, i);
    }

    @Override
    public float getRainGradient(float f) {
        if (PolyWeatherConfig.INSTANCE.enabled) {
            return PolyWeatherClient.isSnowing() ? PolyWeatherClient.getSnowStrength() : PolyWeatherClient.isRaining() ? PolyWeatherClient.getRainStrength() : 0f;
        }

        return super.getRainGradient(f);
    }

    @Override
    public float getThunderGradient(float f) {
        if (PolyWeatherConfig.INSTANCE.enabled) {
            return PolyWeatherClient.isThundering() ? PolyWeatherClient.getThunderStrength() : 0f;
        }

        return super.getThunderGradient(f);
    }
}
