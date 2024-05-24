package net.zepalesque.zenith.api.biometint;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * A class used to easily create biome-dependent tint systems.
 */
public class BiomeTint {

    private final DataMapType<Biome, Integer> dataMap;
    private Map<Biome, Integer> tints = null;
    private final int defaultColor;

    /**
     * @param loc The location that will be used for the {@link DataMapType}. Preferably should be the same as this tint's registered ID.
     * @param defaultColor The fallback color that should be used if a biome does not exist in the tint map.
     */
    public BiomeTint(ResourceLocation loc, int defaultColor) {
        DataMapType<Biome, Integer> dataMap = createTintMap(loc);
        this.dataMap = dataMap;
        this.defaultColor = defaultColor;
    }

    /**
     * Creates a {@link DataMapType} that should be used for this object. Whenever the player joins a world, this will be used to update the tint map.
     * @param location The {@link ResourceLocation} that should be used for this data map. This will automatically be prefixed with "tint/".
     * @return a new {@link DataMapType}
     */
    private static DataMapType<Biome, Integer> createTintMap(ResourceLocation location) {
        return DataMapType.builder(location.withPrefix("tint/"), Registries.BIOME, Codec.INT).synced(Codec.INT, true).build();
    }

    /**
     * Returns the color for the given biome. Note that this will throw an exception if the tint map has not been initialized yet.
     * @param biome The biome to check for.
     * @return The color that should be used, as an integer.
     */
    public int getColor(Biome biome) {
        if (this.tints == null) {
            throw new UnsupportedOperationException("Biome tints have not been initialized yet!");
        } else {
            return this.tints.getOrDefault(biome, defaultColor);
        }
    }

    /**
     *  Clears or initializes the tint map. Used before adding new tints whenever the player joins a new world. Should not be called, as this is handled by Zenith.
     */
    public void tintInit() {
        if (this.tints == null) {
            this.tints = new HashMap<>();
        } else {
            this.tints.clear();
        }
    }

    /**
     *  Adds a tint color to the map. Used to add tints from the data map whenever the player joins a new world. Should not be called, as this is handled by Zenith.
     */
    public void addTint(Biome biome, int color) {
        if (this.tints == null) {
            throw new UnsupportedOperationException("Biome tints have not been initialized yet!");
        } else {
            this.tints.put(biome, color);
        }
    }

    /**
     * Access for the {@link DataMapType} this object uses.
     * @return The data map type. Should mainly be used for datagen purposes
     */
    public DataMapType<Biome, Integer> getDataMap() {
        return this.dataMap;
    }

    /**
     * Registers this object's {@link DataMapType}. Should not be called, as this is handled by Zenith.
     */
    public void register(RegisterDataMapTypesEvent event) {
        event.register(this.dataMap);
    }
}
