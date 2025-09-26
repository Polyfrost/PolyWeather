package org.polyfrost.polyweather.mixin;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.polyfrost.polyweather.client.ClientWeatherManager;
import org.polyfrost.polyweather.client.PolyWeatherClient;
import org.polyfrost.polyweather.client.PolyWeatherConfig;
import org.polyfrost.polyweather.client.WeatherStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldClient.class)
public abstract class Mixin_FixRainAndThunderStrength extends World implements WeatherStorage {
    protected Mixin_FixRainAndThunderStrength(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }

    public float getRainStrength(float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getPrecipitationStrength(delta);
        }

        return super.getRainStrength(delta);
    }

    public float getThunderStrength(float delta) {
        if (PolyWeatherConfig.isEnabled()) {
            return ClientWeatherManager.getStormStrength(delta);
        }

        return super.getThunderStrength(delta);
    }

    public boolean isRainingAt(BlockPos strikePosition) {
        if (PolyWeatherConfig.isEnabled()) {
            // Stops the player from being able to use the trident when they have rainy weather enabled
            // We do this by just checking if it's actually raining, otherwise just say nope
            if (!ClientWeatherManager.isActuallyRaining()) {
                return false;
            }

            if (!this.canSeeSky(strikePosition)) {
                return false;
            }

            int topY = this.getPrecipitationHeight(strikePosition).getY();
            if (topY > strikePosition.getY()) {
                return false;
            }

            BiomeGenBase biome = this.getBiomeGenForCoords(strikePosition);
            return biome.getEnableSnow() ? false : (this.canSnowAt(strikePosition, false) ? false : biome.canRain());
        }

        return super.isRainingAt(strikePosition);
    }

    @Unique
    @Override
    public boolean polyweather$isRaining() {
        return super.getRainStrength(1f) > 0.2f;
    }
}
