package net.zepalesque.zenith.data.generator;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.zepalesque.zenith.api.biometint.BiomeTint;
import net.zepalesque.zenith.api.biometint.BiomeTints;

import java.util.concurrent.CompletableFuture;

/**
 * Example {@link BiomeTint} datagen
 */
public class ZenithDataMapGen extends DataMapProvider {
    public ZenithDataMapGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void gather() {
        Builder<Integer, Biome> tints = this.builder(BiomeTints.EXAMPLE_TINT.get().getDataMap());
        tints.add(Biomes.PLAINS, 0x5BCEFA, false);
        tints.add(Biomes.CHERRY_GROVE, 0xF5A9B8, false);
        tints.add(Biomes.THE_END, 0xFFFFFF, false);
        tints.add(BiomeTags.IS_MOUNTAIN, 0xF5A9B8, false);
        tints.add(BiomeTags.IS_TAIGA, 0x5BCEFA, false);
    }
}