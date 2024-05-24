package net.zepalesque.zenith.api.blockset;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.entity.misc.ZenithBoat;
import net.zepalesque.zenith.entity.misc.ZenithChestBoat;

import java.util.function.Supplier;

/**
 * Should implement methods by having the first of each (with the parameters) used as a construction method and the second (without parameters) as a getter function
 */
public abstract class BaseWoodSet implements BlockSet {

    // Blocks

    protected abstract DeferredBlock<?> log(DeferredRegister.Blocks registry, String id, MapColor woodColor, MapColor barkColor, SoundType soundType);
    public abstract DeferredBlock<?> log();

    protected abstract DeferredBlock<?> strippedLog(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> strippedLog();

    protected abstract DeferredBlock<?> wood(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> wood();

    protected abstract DeferredBlock<?> strippedWood(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> strippedWood();

    protected abstract DeferredBlock<?> planks(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> planks();

    protected abstract DeferredBlock<?> stairs(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> stairs();

    protected abstract DeferredBlock<?> slab(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> slab();

    protected abstract DeferredBlock<?> fence(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> fence();

    protected abstract DeferredBlock<?> fenceGate(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> fenceGate();

    protected abstract DeferredBlock<?> door(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> door();

    protected abstract DeferredBlock<?> trapDoor(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> trapDoor();

    protected abstract DeferredBlock<?> pressurePlate(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> pressurePlate();

    protected abstract DeferredBlock<?> button(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> button();

    protected abstract DeferredBlock<? extends BaseEntityBlock> signBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<? extends BaseEntityBlock> signBlock();

    protected abstract DeferredBlock<? extends BaseEntityBlock> wallSignBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<? extends BaseEntityBlock> wallSignBlock();

    protected abstract DeferredBlock<? extends BaseEntityBlock> hangingSignBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<? extends BaseEntityBlock> hangingSignBlock();

    protected abstract DeferredBlock<? extends BaseEntityBlock> wallHangingSignBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<? extends BaseEntityBlock> wallHangingSignBlock();


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

    protected abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> sign(DeferredRegister<BlockEntityType<?>> registry, String id);
    public abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> sign();

    protected abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> hangingSign(DeferredRegister<BlockEntityType<?>> registry, String id);
    public abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> hangingSign();

    public Supplier<Item> getStick() {
        return () -> Items.STICK;
    }
}

