package net.zepalesque.zenith.api;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public abstract class BaseWoodSet implements BlockSet {

    // Blocks

    protected abstract DeferredBlock<?> generateLog(DeferredRegister.Blocks registry, String id, MapColor woodColor, MapColor barkColor, SoundType soundType);

    protected abstract DeferredBlock<?> generateStrippedLog(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateWood(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateStrippedWood(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generatePlanks(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateStairs(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateSlab(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateFence(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateFenceGate(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateDoor(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateTrapDoor(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generatePressurePlate(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateButton(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<? extends BaseEntityBlock> generateSignBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<? extends BaseEntityBlock> generateWallSignBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<? extends BaseEntityBlock> generateHangingSignBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<? extends BaseEntityBlock> generateWallHangingSignBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);


    // Items

    protected abstract DeferredItem<?> generateBoatItem(DeferredRegister.Items registry, String id);

    protected abstract DeferredItem<?> generateChestBoatItem(DeferredRegister.Items registry, String id);


    // Entities

    protected abstract DeferredHolder<EntityType<?>, EntityType<?>> generateBoatEntity(DeferredRegister<EntityType<?>> registry, String id);

    protected abstract DeferredHolder<EntityType<?>, EntityType<?>> generateChestBoatEntity(DeferredRegister<EntityType<?>> registry, String id);


    // Block Entities

    protected abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> generateSign(DeferredRegister<BlockEntityType<?>> registry, String id);

    protected abstract DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> generateHangingSign(DeferredRegister<BlockEntityType<?>> registry, String id);



}

