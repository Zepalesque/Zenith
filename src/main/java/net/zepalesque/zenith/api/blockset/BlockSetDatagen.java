package net.zepalesque.zenith.api.blockset;

import net.minecraft.data.DataProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.zepalesque.zenith.api.blockset.BlockSet;

/**
 * Allows semi-automated generation
 * @param <T> The data generator's class. For instance, an ExampleBlockstateGenerator should extend {@code BlockSetDatagen<ExampleBlockstateGenerator>}, and so on for other data generator types
 *            Note that it is still necessary to call something around the lines of this.doBlockSetGeneration() in the data generator's respective generation method.
 */
public interface BlockSetDatagen<T extends BlockSetDatagen<T>> {

    /**
     * The respective method for generating a given BlockSet's data.<br>
     * For example, a {@link BlockStateProvider} might implement this as follows:<br><br>
     * <pre><code>
     * <literal>@Override</literal>
     * public void generateDataForBlockSet(BlockSet set) {
     *     set.blockData(this);
     * }
     * </code></pre>
     * @param set The
     */
    void generateDataForBlockSet(BlockSet set);

    /**
     * Generate the data for this generator's set BlockSets. Should be called in the generator's respective method of generation.
     * For example, a {@link BlockStateProvider} would do this in its {@link BlockStateProvider#registerStatesAndModels() registerStatesAndModels()} method.
     */
    default void doBlockSetGeneration() {
        this.getSets().forEach(registry -> registry.forEach(this::generateDataForBlockSet));
    }

    Map<BlockSetDatagen<?>, Collection<Collection<BlockSet>>> SETS_FOR_GENERATORS = new HashMap<>();

    @SuppressWarnings("unchecked")
    default T withBlockSets(Collection<BlockSet> registry, Collection<BlockSet>... others) {
        this.getSets().add(registry);
        this.getSets().addAll(List.of(others));
        return this.self();
    }


    default Collection<Collection<BlockSet>> getSets() {
        return SETS_FOR_GENERATORS.computeIfAbsent(this, gen -> new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    default T self() {
        return (T) this;
    }
}
