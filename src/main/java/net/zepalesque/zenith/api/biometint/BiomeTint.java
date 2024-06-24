package net.zepalesque.zenith.api.biometint;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.zepalesque.zenith.Zenith;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A class used to easily create biome-dependent tint systems.
 */
public class BiomeTint {

    private final Map<Biome, Integer> tints = new HashMap<>();
    private final Map<ResourceKey<Biome>, Integer> serverTints = new HashMap<>();
    private final int defaultColor;
    @Nonnull
    private Optional<Integer> defaultOverride = Optional.empty();
    private boolean initialized = false;

    /**
     * @param loc The location that will be used for the {@link DataMapType}. Preferably should be the same as this tint's registered ID.
     * @param defaultColor The fallback color that should be used if a biome does not exist in the tint map.
     */
    public BiomeTint(ResourceLocation loc, int defaultColor) {
        this.defaultColor = defaultColor;
    }

    /**
     * Returns the color for the given biome.
     * @param biome The biome to check for.
     * @return The color that should be used, as an integer.
     */
    public int getColor(Biome biome) {
        if (!this.initialized) {
            Zenith.LOGGER.warn("Attempted to get uninitialized BiomeTint: {}!", BiomeTints.TINT_REGISTRY.getKey(this));
        }
        return this.tints.getOrDefault(biome, getDefaultColor());
    }

    /**
     *  Clears or initializes the tint map. Used before adding new tints whenever the player joins a new world. Should not be called, as this is handled by Zenith.
     */
    public void clear() {
        this.tints.clear();
        this.initialized = false;
        this.defaultOverride = Optional.empty();
    }

    /**
     *  Adds a tint color to the map. Used to add tints from the data map whenever the player joins a new world. Should not be called, as this is handled by Zenith.
     */
    public void addTint(Biome biome, int color, ResourceLocation biomeID) {
        if (biome == null) {
            Zenith.LOGGER.warn("Attempted to put nonexistent biome {} in tint map!", biomeID.toString());
            return;
        }
        this.tints.put(biome, color);
    }

    /**
     * Sets the override color for the map. Should not be called, as this is handled by Zenith.
     * @param color the color for the override
     */
    public void setOverride(int color) {
        this.defaultOverride = Optional.of(color);
    }

    public int getDefaultColor() {
        return this.defaultOverride.orElse(this.defaultColor);
    }

    public Map<ResourceKey<Biome>, Integer> toSync() {
        return this.serverTints;
    }

    public Optional<Integer> getOverride() {
        return this.defaultOverride;
    }

    public void markInitialized() {
        this.initialized = true;
    }
}
