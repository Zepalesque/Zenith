package net.zepalesque.zenith.api;

import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
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

}
