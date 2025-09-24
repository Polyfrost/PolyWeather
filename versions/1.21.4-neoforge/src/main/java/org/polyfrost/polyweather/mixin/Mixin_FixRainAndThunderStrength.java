package org.polyfrost.polyweather.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientLevel.class)
public abstract class Mixin_FixRainAndThunderStrength extends Level {
    protected Mixin_FixRainAndThunderStrength(WritableLevelData mutableWorldProperties, ResourceKey<Level> registryKey, RegistryAccess dynamicRegistryManager, Holder<DimensionType> registryEntry, boolean bl, boolean bl2, long l, int i) {
        super(mutableWorldProperties, registryKey, dynamicRegistryManager, registryEntry, bl, bl2, l, i);
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
