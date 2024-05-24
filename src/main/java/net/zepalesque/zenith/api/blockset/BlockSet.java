package net.zepalesque.zenith.api.blockset;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;

/**
 * A set of auto-datagenned blocks. Not to be confused with {@link net.minecraft.world.level.block.state.properties.BlockSetType}!
 */
public interface BlockSet {

    /**
     * Generate blockstate files and block models for this BlockSet
     * @param data the {@link BlockStateProvider} used
     */
    void blockGen(BlockStateProvider data);

    /**
     * Generate item models for this BlockSet
     * @param data the {@link ItemModelProvider} used
     */
    void itemGen(ItemModelProvider data);

    /**
     * Generate language data for this BlockSet
     * @param data the {@link LanguageProvider} used
     */
    void langGen(LanguageProvider data);

    /**
     * Generate recipe data for this BlockSet
     * @param data the {@link RecipeProvider} used
     */
    void recipeGen(RecipeProvider data);

    /**
     * Generate block tag data for this BlockSet
     * @param data the {@link BlockTagsProvider} used
     */
    void blockTagData(BlockTagsProvider data);

    /**
     * Generate item tag data for this BlockSet
     * @param data the {@link ItemTagsProvider} used
     */
    void itemTagData(ItemTagsProvider data);

    /**
     * Generate block loot data for this BlockSet
     * @param data the {@link BlockLootSubProvider} used
     */
    void lootData(BlockLootSubProvider data);

    /**
     * Generate datamap data for this BlockSet
     * @param data the {@link DataMapProvider} used
     */
    void mapData(DataMapProvider data);

}
