package org.polyfrost.polyweather.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.polyfrost.polyweather.client.WeatherStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class Mixin_FixRainAndThunderStrength extends Level implements WeatherStorage {
    protected Mixin_FixRainAndThunderStrength(WritableLevelData arg, ResourceKey<Level> arg2, RegistryAccess arg3, Holder<DimensionType> arg4, boolean bl, boolean bl2, long l, int i) {
        super(arg, arg2, arg3, arg4, bl, bl2, l, i);
    }

    @Override
    public float getRainLevel(float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return super.getRainLevel(delta);
    }

    @Override
    public float getThunderLevel(float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return super.getThunderLevel(delta);
    }

    @Override
    public boolean isRainingAt(BlockPos pos) {
        if (PolyWeatherConfig.isEnabled()) {
            // Stops the player from being able to use the trident when they have rainy weather enabled
            // We do this by just checking if it's actually raining, otherwise just say nope
            if (!ClientWeatherManager.isActuallyRaining()) {
                return false;
            }

            if (!this.canSeeSky(pos)) {
                return false;
            }

            int topY = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY();
            if (topY > pos.getY()) {
                return false;
            }

            return this.getBiome(pos)
                    .value()
                    .getPrecipitationAt(pos, this.getSeaLevel()) == Biome.Precipitation.RAIN;
        }

        return super.isRainingAt(pos);
    }

    @Unique
    @Override
    public boolean polyweather$isRaining() {
        return super.getRainLevel(1f) > 0.2f;
    }
}
