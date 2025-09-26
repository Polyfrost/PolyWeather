package org.polyfrost.polyweather.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.MutableWorldProperties;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.polyfrost.polyweather.client.WeatherStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class Mixin_FixRainAndThunderStrength extends World implements WeatherStorage {
    protected Mixin_FixRainAndThunderStrength(MutableWorldProperties arg, RegistryKey<World> arg2, DynamicRegistryManager arg3, RegistryEntry<DimensionType> arg4, Supplier<Profiler> supplier, boolean bl, boolean bl2, long l, int i) {
        super(arg, arg2, arg3, arg4, supplier, bl, bl2, l, i);
    }

    @Override
    public float getRainGradient(float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return super.getRainGradient(delta);
    }

    @Override
    public float getThunderGradient(float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return super.getThunderGradient(delta);
    }

    @Override
    public boolean hasRain(BlockPos pos) {
        if (PolyWeatherConfig.isEnabled()) {
            // Stops the player from being able to use the trident when they have rainy weather enabled
            // We do this by just checking if it's actually raining, otherwise just say nope
            if (!ClientWeatherManager.isActuallyRaining()) {
                return false;
            }

            if (!this.isSkyVisible(pos)) {
                return false;
            }

            int topY = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY();
            if (topY > pos.getY()) {
                return false;
            }

            return this.getBiome(pos)
                    .comp_349()
                    .getPrecipitation(pos) == Biome.Precipitation.RAIN;
        }

        return super.hasRain(pos);
    }

    @Unique
    @Override
    public boolean polyweather$isRaining() {
        return super.getRainGradient(1f) > 0.2f;
    }
}
