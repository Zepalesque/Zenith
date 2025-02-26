package net.zepalesque.zenith.api.blockset.type;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.blockset.core.CraftingMatrix;
import net.zepalesque.zenith.api.blockset.type.base.AbstractStoneSet;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;
import net.zepalesque.zenith.util.data.DatagenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BaseStoneSet<S extends BaseStoneSet<S>> extends AbstractStoneSet<S> {

    public final String id, textureFolder;


    protected final DeferredBlock<? extends Block> base;
    protected final DeferredBlock<? extends StairBlock> stairs;
    protected final DeferredBlock<? extends SlabBlock> slab;
    protected final DeferredBlock<? extends WallBlock> wall;
    protected NoteBlockInstrument instrument = NoteBlockInstrument.BASEDRUM;
    protected final Map<CraftingMatrix, Supplier<? extends ItemLike>> crafted_blocks = new HashMap<>();
    protected final Map<CraftingMatrix, Supplier<AbstractStoneSet<?>>> crafted_sets = new HashMap<>();
    protected final Map<Supplier<? extends ItemLike>, Integer> stonecut_blocks = new HashMap<>();
    protected final List<Supplier<AbstractStoneSet<?>>> stonecut_sets = new ArrayList<>();
    protected final Map<Supplier<? extends ItemLike>, Float> smelted_blocks = new HashMap<>();
    protected final Map<Supplier<AbstractStoneSet<?>>, Float> smelted_sets = new HashMap<>();
    protected final Map<Supplier<? extends ItemLike>, Float> blasted_blocks = new HashMap<>();
    protected final Map<Supplier<AbstractStoneSet<?>>, Float> blasted_sets = new HashMap<>();

    protected final Supplier<Block[]> blocks;

    public BaseStoneSet(String id, MapColor color, SoundType sound, float breakTime, float blastResistance, String textureFolder, DeferredRegister.Blocks blocks, DeferredRegister.Items items) {
        this.id = id;
        this.textureFolder = textureFolder;
        this.base = this.block(blocks, items, id, color, sound, breakTime, blastResistance);
        this.stairs = this.stairs(blocks, items, id, color, sound, breakTime, blastResistance);
        this.slab = this.slab(blocks, items, id, color, sound, breakTime, blastResistance);
        this.wall = this.wall(blocks, items, id, color, sound, breakTime, blastResistance);
        this.blocks = Lazy.of(() -> new Block[]{ base.get(), stairs.get(), slab.get(), wall.get() });
    }

    public S instrument(NoteBlockInstrument instrument) {
        this.instrument = instrument;
        return self();
    }

    @Override
    protected DeferredBlock<? extends Block> block(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(true), () -> new Block(
                BlockBehaviour.Properties.of()
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<? extends Block> block() {
        return this.base;
    }

    @Override
    protected DeferredBlock<? extends StairBlock> stairs(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(false) + "_stairs", () -> new StairBlock(this.block().get().defaultBlockState(),
                BlockBehaviour.Properties.of()
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<? extends StairBlock> stairs() {
        return this.stairs;
    }

    @Override
    protected DeferredBlock<? extends SlabBlock> slab(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(false) + "_slab", () -> new SlabBlock(
                BlockBehaviour.Properties.of()
                        // Unchanged as vanilla appears to no longer have higher break times for slabs
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }


    @Override
    public DeferredBlock<? extends SlabBlock> slab() {
        return this.slab;
    }

    @Override
    protected DeferredBlock<? extends WallBlock> wall(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(false) + "_wall", () -> new WallBlock(
                BlockBehaviour.Properties.of()
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<? extends WallBlock> wall() {
        return this.wall;
    }

    @Override
    public S craftsIntoSet(Supplier<AbstractStoneSet<?>> set, CraftingMatrix shape) {
        this.crafted_sets.put(shape, set);
        return self();
    }

    @Override
    public S craftsInto(Supplier<? extends ItemLike> block, CraftingMatrix shape) {
        this.crafted_blocks.put(shape, block);
        return self();
    }

    @Override
    public S stonecutIntoSet(Supplier<AbstractStoneSet<?>> set) {
        this.stonecut_sets.add(set);
        return self();
    }
    @Override
    public S stonecutInto(Supplier<? extends ItemLike> block, int count) {
        this.stonecut_blocks.put(block, count);
        return self();
    }

    @Override
    public S smeltsIntoSet(Supplier<AbstractStoneSet<?>> set, float experience) {
        this.smelted_sets.put(set, experience);
        return self();
    }

    @Override
    public S smeltsInto(Supplier<? extends ItemLike> block, float experience) {
        this.smelted_blocks.put(block, experience);
        return self();
    }

    public S blastsIntoSet(Supplier<AbstractStoneSet<?>> set, float experience) {
        this.blasted_sets.put(set, experience);
        return self();
    }

    public S blastsInto(Supplier<? extends ItemLike> block, float experience) {
        this.blasted_blocks.put(block, experience);
        return self();
    }

    @Override
    public String baseName(boolean isBaseBlock) {
        return this.id;
    }

    @Override
    public void blockData(BlockStateProvider data) {
        ResourceLocation baseName = BuiltInRegistries.BLOCK.getKey(this.block().get());
        ResourceLocation loc = baseName.withPrefix("block/");
        String locPath = loc.getPath();
        ResourceLocation texture = baseName.withPrefix("block/" + this.textureFolder);

        data.models().cubeAll(locPath, texture);
        data.stairsBlock(this.stairs().get(), locPath, texture);
        data.slabBlock(this.slab().get(), loc, texture);
        data.wallBlock(this.wall().get(), locPath, texture);
    }

    @Override
    public void itemData(ItemModelProvider data) {

        ResourceLocation baseName = BuiltInRegistries.BLOCK.getKey(this.block().get());
        ResourceLocation loc = baseName.withPrefix("block/");
        String locPath = loc.getPath();
        ResourceLocation texture = baseName.withPrefix("block/" + this.textureFolder);

        data.simpleBlockItem(this.block().get());
        data.simpleBlockItem(this.stairs().get());
        data.simpleBlockItem(this.slab().get());
        data.wallInventory(locPath, texture);
    }

    @Override
    public void langData(LanguageProvider data) {

        data.addBlock(this.block(), DatagenUtil.localize(this.block()));
        data.addBlock(this.stairs(), DatagenUtil.localize(this.stairs()));
        data.addBlock(this.slab(), DatagenUtil.localize(this.slab()));
        data.addBlock(this.wall(), DatagenUtil.localize(this.wall()));
    }

    @Override
    public S flammable(int encouragement, int flammability) {
        return self();
    }

    @Override
    public void recipeData(RecipeProvider data, RecipeOutput consumer) {
//        data.stairs(this.stairs(), this.block()).save(consumer);
//        ReduxRecipeProvider.slab(consumer, RecipeCategory.BUILDING_BLOCKS, this.slab().get(), this.block().get());
//        ReduxRecipeProvider.wall(consumer, RecipeCategory.BUILDING_BLOCKS, this.wall().get(), this.block().get());
//
//        this.crafted_sets.forEach((matrix, set) ->
//            matrix.apply(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, set.get().block().get(), matrix.count()), this.block().get())
//                    .unlockedBy(ReduxRecipeProvider.getHasName(set.get().block().get()), ReduxRecipeProvider.has(set.get().block().get())).save(consumer,
//                            data.name(ReduxRecipeProvider.getConversionRecipeName(set.get().block().get(), this.block().get()))
//                    )
//        );
//
//        this.crafted_blocks.forEach((matrix, block) ->
//            matrix.apply(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block.get(), matrix.count()), this.block().get())
//                    .unlockedBy(ReduxRecipeProvider.getHasName(this.block().get()), ReduxRecipeProvider.has(this.block().get())).save(consumer,
//                            data.name(ReduxRecipeProvider.getConversionRecipeName(block.get(), this.block().get()))
//                    )
//        );
//
//        this.stonecut_blocks.forEach((block, count) ->
//                data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, block.get(), this.block().get(), count)
//        );
//
//        this.stonecut_sets.forEach(set -> {
//                    data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, set.get().block().get(), this.block().get());
//                    data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, set.get().stairs().get(), this.block().get());
//                    data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, set.get().slab().get(), this.block().get(), 2);
//                    data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, set.get().wall().get(), this.block().get());
//                }
//        );
//
//        this.smelted_blocks.forEach((block, xp) ->
//            data.smeltingOreRecipe(block.get(), this.block().get(), xp).save(consumer, data.name(ReduxRecipeProvider.getConversionRecipeName(block.get(), this.block().get()) + "_smelting"))
//        );
//
//        this.smelted_sets.forEach((set, xp) ->
//                data.smeltingOreRecipe(set.get().block().get(), this.block().get(), xp).save(consumer, data.name(ReduxRecipeProvider.getConversionRecipeName(set.get().block().get(), this.block().get()) + "_smelting"))
//        );
//
//        this.blasted_blocks.forEach((block, xp) ->
//            data.blastingOreRecipe(block.get(), this.block().get(), xp).save(consumer, data.name(ReduxRecipeProvider.getConversionRecipeName(block.get(), this.block().get()) + "_blasting"))
//        );
//
//        this.blasted_sets.forEach((set, xp) ->
//                data.blastingOreRecipe(set.get().block().get(), this.block().get(), xp).save(consumer, data.name(ReduxRecipeProvider.getConversionRecipeName(set.get().block().get(), this.block().get()) + "_blasting"))
//        );
    }

    @Override
    public void lootData(BlockLootSubProvider data) {
        data.dropSelf(this.block().get());
        data.dropSelf(this.stairs().get());
        data.add(this.slab().get(), data::createSlabItemTable);
        data.dropSelf(this.wall().get());
    }

    @Override
    public void flammables(FireAccessor accessor) { }

    @Override
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) { }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public ItemLike[] items() {
        return this.blocks.get();
    }

    @Override
    public Supplier<Block[]> blocks() {
        return this.blocks;
    }
}
