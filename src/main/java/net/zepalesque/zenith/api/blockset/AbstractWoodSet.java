package net.zepalesque.zenith.api.blockset;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.entity.misc.ZenithBoat;
import net.zepalesque.zenith.entity.misc.ZenithChestBoat;
import net.zepalesque.zenith.tile.ZenithHangingSignBlockEntity;
import net.zepalesque.zenith.tile.ZenithSignBlockEntity;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Should implement methods by having the first of each (with the parameters) used as a construction method and the second (without parameters) as a getter function
 */
public abstract class AbstractWoodSet implements BlockSet {

    // Blocks

    protected abstract DeferredBlock<?> log(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor woodColor, MapColor barkColor, SoundType soundType);
    public abstract DeferredBlock<?> log();

    protected abstract DeferredBlock<?> strippedLog(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> strippedLog();

    protected abstract DeferredBlock<?> wood(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> wood();

    protected abstract DeferredBlock<?> strippedWood(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> strippedWood();

    protected abstract DeferredBlock<?> planks(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> planks();

    protected abstract DeferredBlock<?> stairs(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> stairs();

    protected abstract DeferredBlock<?> slab(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> slab();

    protected abstract DeferredBlock<?> fence(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> fence();

    protected abstract DeferredBlock<?> fenceGate(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color);
    public abstract DeferredBlock<?> fenceGate();

    protected abstract DeferredBlock<?> door(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color);
    public abstract DeferredBlock<?> door();

    protected abstract DeferredBlock<?> trapdoor(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color);
    public abstract DeferredBlock<?> trapdoor();

    protected abstract DeferredBlock<?> pressurePlate(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color);
    public abstract DeferredBlock<?> pressurePlate();

    protected abstract DeferredBlock<?> button(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color);
    public abstract DeferredBlock<?> button();

    protected abstract DeferredBlock<? extends BaseEntityBlock> sign(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<? extends BaseEntityBlock> sign();

    protected abstract DeferredBlock<? extends BaseEntityBlock> wallSign(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<? extends BaseEntityBlock> wallSign();

    protected abstract DeferredBlock<? extends BaseEntityBlock> hangingSign(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<? extends BaseEntityBlock> hangingSign();

    protected abstract DeferredBlock<? extends BaseEntityBlock> wallHangingSign(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<? extends BaseEntityBlock> wallHangingSign();


    // Items

    protected abstract DeferredItem<?> boatItem(DeferredRegister.Items registry, String id);
    public abstract DeferredItem<?> boatItem();

    protected abstract DeferredItem<?> chestBoatItem(DeferredRegister.Items registry, String id);
    public abstract DeferredItem<?> chestBoatItem();


    // Entities

    protected abstract DeferredHolder<EntityType<?>, EntityType<? extends ZenithBoat>> boatEntity(DeferredRegister<EntityType<?>> registry, String id);
    public abstract DeferredHolder<EntityType<?>, EntityType<? extends ZenithBoat>> boatEntity();

    protected abstract DeferredHolder<EntityType<?>, EntityType<? extends ZenithChestBoat>> chestBoatEntity(DeferredRegister<EntityType<?>> registry, String id);
    public abstract DeferredHolder<EntityType<?>, EntityType<? extends ZenithChestBoat>> chestBoatEntity();


    // Block Entities

    protected abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithSignBlockEntity>> signEntity(DeferredRegister<BlockEntityType<?>> registry, String id);
    public abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithSignBlockEntity>> signEntity();

    protected abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithHangingSignBlockEntity>> hangingSignEntity(DeferredRegister<BlockEntityType<?>> registry, String id);
    public abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithHangingSignBlockEntity>> hangingSignEntity();

    // Other

    protected abstract BlockSetType setType(String id, SoundType sound);
    public abstract BlockSetType setType();

    protected abstract WoodType woodType(String id, BlockSetType type, SoundType sound);
    public abstract WoodType woodType();

    protected abstract TagKey<Item> logsTag(String id);
    public abstract TagKey<Item> logsTag();

    protected abstract TagKey<Block> logsBlockTag(String id);
    public abstract TagKey<Block> logsBlockTag();


    public Supplier<Item> getStick() {
        return () -> Items.STICK;
    }


    // Tool conversions

    public abstract void setupStrippables(Map<Block, Block> strippingMap);


    // Language data helpers

    public abstract String logSuffix(boolean isPlural, boolean isLocalized);

    public abstract String woodSuffix(boolean isPlural, boolean isLocalized);

    public abstract String treesName(boolean isPlural);
}

