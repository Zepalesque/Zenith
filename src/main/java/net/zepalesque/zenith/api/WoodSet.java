package net.zepalesque.zenith.api;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WoodSet implements BlockSet {

    public final DeferredBlock<Block> log;

    public WoodSet(DeferredRegister.Blocks registry, String id, MapColor barkColor, MapColor woodColor, SoundType soundType) {

        this.log = generateLog(registry, id, barkColor, woodColor, soundType);

    }

    protected DeferredBlock<Block> generateLog(DeferredRegister.Blocks registry, String id, MapColor barkColor, MapColor woodColor, SoundType soundType) {
        return registry.register(id, () -> new RotatedPillarBlock(Properties.of()
                .mapColor(p_152624_ -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? woodColor : barkColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
    }





    @Override
    public void blockGen(BlockStateProvider data) {

    }

    @Override
    public void itemGen(ItemModelProvider data) {

    }

    @Override
    public void langGen(LanguageProvider data) {

    }
}
