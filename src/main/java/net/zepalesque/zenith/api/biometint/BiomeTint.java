package net.zepalesque.zenith.api.biometint;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BiomeTint {

    private final DataMapType<Biome, Integer> dataMap;
    private Map<Biome, Integer> tints = null;
    private final int defaultColor;
    
    public BiomeTint(ResourceLocation loc, int defaultColor) {
        DataMapType<Biome, Integer> dataMap = createTintMap(loc);
        this.dataMap = dataMap;
        this.defaultColor = defaultColor;
    }

    private static DataMapType<Biome, Integer> createTintMap(ResourceLocation location) {
        return DataMapType.builder(location.withPrefix("tint/"), Registries.BIOME, Codec.INT).synced(Codec.INT, true).build();
    }

    public int getColor(Biome biome) {
        if (this.tints == null) {
            throw new UnsupportedOperationException("Biome tints have not been initialized yet!");
        } else {
            return this.tints.getOrDefault(biome, defaultColor);
        }
    }

    public void clearTints() {
        if (this.tints == null) {
            this.tints = new HashMap<>();
        } else {
            this.tints.clear();
        }
    }
    public void addTint(Biome biome, int color) {
        if (this.tints == null) {
            throw new UnsupportedOperationException("Biome tints have not been initialized yet!");
        } else {
            this.tints.put(biome, color);
        }
    }

    public DataMapType<Biome, Integer> getDataMap() {
        return this.dataMap;
    }

    public void register(RegisterDataMapTypesEvent event) {
        event.register(this.dataMap);
    }
}
