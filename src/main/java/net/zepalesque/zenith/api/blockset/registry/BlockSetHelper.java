package net.zepalesque.zenith.api.blockset.registry;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.zepalesque.zenith.api.blockset.core.BlockSet;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlockSetHelper {

    private static final Map<String, Set<BlockSet>> REGISTRIES = new HashMap<>();

    private static final Object[] NO_EXTRAS = new Object[0];

    /**
     * Given a specific collection of blocksets, does generation for the given instance (with extra inputs applied)
     */
    @SuppressWarnings("unchecked")
    public static <T> void doGen(Collection<BlockSet> sets, T generator, Object[] extras) {
        sets.stream().flatMap(set -> set.handler().entries().stream()).filter(genEntry ->
                genEntry.clazz().isAssignableFrom(generator.getClass())).map(entry -> (BlockSet.GenEntry<? super T>) entry).forEach(e -> e.generate(generator, extras));

    }

    public static <T extends RecipeProvider> void doRecipeGen(Collection<BlockSet> sets, T generator, RecipeOutput output) {
        doGen(sets, generator, new Object[] {output});
    }

    public static <T> void doGen(Collection<BlockSet> sets, T generator) {
        doGen(sets, generator, NO_EXTRAS);
    }


    public Set<BlockSet> createBlockSetRegistry(String modid) {
        return REGISTRIES.computeIfAbsent(modid, s -> new HashSet<>());
    }

    @Nullable
    public Set<BlockSet> get(String modid) {
        return REGISTRIES.get(modid);
    }
}
