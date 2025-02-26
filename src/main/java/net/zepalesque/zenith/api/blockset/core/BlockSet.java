package net.zepalesque.zenith.api.blockset.core;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.ItemLike;
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;


// TODO: Full rewrite, unify creative tab gen and stuff

/**
 * A set of auto-datagenned blocks. Not to be confused with {@link net.minecraft.world.level.block.state.properties.BlockSetType}!
 */
public interface BlockSet {

    DatagenHandler handler();

    /**
     * Generate blockstate files and block models for this BlockSet
     * @param data the {@link BlockStateProvider} used
     */
    void blockData(BlockStateProvider data);

    /**
     * Generate item models for this BlockSet
     * @param data the {@link ItemModelProvider} used
     */
    void itemData(ItemModelProvider data);

    /**
     * Generate language data for this BlockSet
     * @param data the {@link LanguageProvider} used
     */
    void langData(LanguageProvider data);

    /**
     * Generate recipe data for this BlockSet
     * @param data the {@link RecipeProvider} used
     * @param output the {@link RecipeOutput} that the recipe provider uses
     */
    void recipeData(RecipeProvider data, RecipeOutput output);

    /**
     * Generate block components data for this BlockSet
     * @param data the {@link BlockTagsProvider} used
     */
    void blockTagData(BlockTagsProvider data);

    /**
     * Generate item components data for this BlockSet
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

    default void registerDefaultDatagenMethods() {
        this.handler().add(BlockStateProvider.class, this::blockData);
        this.handler().add(ItemModelProvider.class, this::itemData);
        this.handler().add(LanguageProvider.class, this::langData);
        this.handler().add(RecipeProvider.class, (recipes, extra) -> this.recipeData(recipes, (RecipeOutput) extra[0]));
        this.handler().add(BlockTagsProvider.class, this::blockTagData);
        this.handler().add(ItemTagsProvider.class, this::itemTagData);
        this.handler().add(BlockLootSubProvider.class, this::lootData);
        this.handler().add(DataMapProvider.class, this::mapData);
    }

    enum TabAdditionPhase {
        BEFORE, AFTER
    }

    String getID();

    final class DatagenHandler {

        private final Set<GenEntry<?>> entries = new HashSet<>();

        public DatagenHandler() {}

        public <T> boolean replace(Class<T> clazz, Consumer<? super T> generator) {
            boolean exists = this.entries.removeIf(e -> e.clazz().isAssignableFrom(clazz));
            this.entries.add(GenEntry.of(clazz, generator));
            return exists;
        }

        public <T> boolean replace(Class<T> clazz, BiConsumer<? super T, Object[]> generator) {
            boolean exists = this.entries.removeIf(e -> e.clazz().isAssignableFrom(clazz));
            this.entries.add(GenEntry.of(clazz, generator));
            return exists;
        }

        public <T> boolean replace(GenEntry<T> entry) {
            boolean exists = this.entries.removeIf(e -> e.clazz().isAssignableFrom(entry.clazz()));
            this.entries.add(entry);
            return exists;
        }

        public Set<GenEntry<?>> entries() {
            return entries;
        }

        @SuppressWarnings("unchecked")
        public <T, A extends T> boolean trySwap(Class<T> original, Class<A> replacement) {
            Optional<GenEntry<?>> optional = this.entries.stream().filter(e -> e.clazz() == original).findFirst();
            if (optional.isPresent()) {
                GenEntry<T> existing = (GenEntry<T>) optional.get();
                this.entries.remove(existing);
                this.entries.add(GenEntry.of(replacement, existing.generator));
                return true;
            }
            return false;
        }

        public <T> DatagenHandler add(Class<T> clazz, Consumer<? super T> generator) {
            this.replace(clazz, generator);
            return this;
        }

        public <T> DatagenHandler add(Class<T> clazz, BiConsumer<? super T, Object[]> generator) {
            this.replace(clazz, generator);
            return this;
        }

        public <T> DatagenHandler add(GenEntry<T> entry) {
            this.replace(entry);
            return this;
        }

        public <T, A extends T> DatagenHandler swap(Class<T> original, Class<A> replacement) {
            this.trySwap(original, replacement);
            return this;
        }
    }

    final class GenEntry<T> {
        private final Class<T> clazz;
        private final BiConsumer<? super T, Object[]> generator;

        private GenEntry(Class<T> clazz, BiConsumer<? super T, Object[]> generator) {
            this.clazz = clazz;
            this.generator = generator;
        }

        static <T> GenEntry<T> of(Class<T> clazz, Consumer<? super T> generator) {
            return of(clazz, (gen, extra) -> generator.accept(gen));
        }

        static <T> GenEntry<T> of(Class<T> clazz, BiConsumer<? super T, Object[]> generator) {
            return new GenEntry<>(clazz, generator);
        }

        static <T extends RecipeProvider> GenEntry<T> recipe(Class<T> clazz, BiConsumer<? super T, RecipeOutput> generator) {
            return new GenEntry<>(clazz, (t, objects) -> generator.accept(t, (RecipeOutput) objects[0]));
        }

        public void generate(T instance, Object... extra) {
            this.generator.accept(instance, extra);
        }

        public Class<T> clazz() {
            return clazz;
        }

        @Override
        public int hashCode() {
            return Objects.hash(clazz, generator);
        }
    }
}
