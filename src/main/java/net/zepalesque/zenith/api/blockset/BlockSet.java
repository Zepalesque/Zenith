package net.zepalesque.zenith.api.blockset;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;

import java.util.List;
import java.util.function.Supplier;

/**
 * A set of auto-datagenned blocks. Not to be confused with {@link net.minecraft.world.level.block.state.properties.BlockSetType}!
 */
public interface BlockSet {

    /**
     * Generate blockstate files and block models for this BlockSet
     * @param data the {@link BlockStateProvider} used
     */
    <P extends BlockStateProvider> void blockData(P data);

    /**
     * Generate item models for this BlockSet
     * @param data the {@link ItemModelProvider} used
     */
    <P extends ItemModelProvider> void itemData(P data);

    /**
     * Generate language data for this BlockSet
     * @param data the {@link LanguageProvider} used
     */
    <P extends LanguageProvider> void langData(P data);

    /**
     * Generate recipe data for this BlockSet
     * @param data the {@link RecipeProvider} used
     * @param output the {@link RecipeOutput} that the recipe provider uses
     */
    <P extends RecipeProvider> void recipeData(P data, RecipeOutput output);

    /**
     * Generate block tag data for this BlockSet
     * @param data the {@link BlockTagsProvider} used
     */
    <P extends BlockTagsProvider> void blockTagData(P data);

    /**
     * Generate item tag data for this BlockSet
     * @param data the {@link ItemTagsProvider} used
     */
    <P extends ItemTagsProvider> void itemTagData(P data);

    /**
     * Generate block loot data for this BlockSet
     * @param data the {@link BlockLootSubProvider} used
     */
    <P extends BlockLootSubProvider> void lootData(P data);

    /**
     * Generate datamap data for this BlockSet
     * @param data the {@link DataMapProvider} used
     */
    <P extends DataMapProvider> void mapData(P data);

    /**
     * Set the flammability of this BlockSet's blocks
     * @param accessor the {@link FireAccessor} used to set the flammability
     */
    void flammables(FireAccessor accessor);

    /**
     * Register any block entity or just regular old entity renderers for this BlockSet.
     * @param event The event used for registration
     */
    @OnlyIn(Dist.CLIENT)
    void registerRenderers(EntityRenderersEvent.RegisterRenderers event);

    /**
     * Adds the blocks from this set to specific creative tabs.
     * @param event The {@link BuildCreativeModeTabContentsEvent} used.
     * @param prev The previous block added, in case these should be put consecutively.
     * @return The new block to use as the next set's {@code prev} parameter.
     */
    Supplier<? extends ItemLike> addToCreativeTab(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> prev, TabAdditionPhase phase);

    /**
     * Whether the creative tab modifications should be done before or after your mod's other creative tab usages.
     * TODO: Automate this
     */
    enum TabAdditionPhase {
        BEFORE, AFTER
    }
}
