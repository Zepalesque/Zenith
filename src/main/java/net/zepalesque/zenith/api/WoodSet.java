package net.zepalesque.zenith.api;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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
import net.zepalesque.zenith.util.DataGenUtil;
import org.codehaus.plexus.util.StringUtils;

public class WoodSet extends BaseWoodSet {

    public final DeferredBlock<RotatedPillarBlock> log;

    protected final MapColor colorPrimary;
    protected final MapColor colorSecondary;

    protected final String id;

    public WoodSet(DeferredRegister.Blocks registry, String id, MapColor woodColor, MapColor barkColor, SoundType soundType) {
        this.id = id;
        this.colorPrimary = woodColor;
        this.colorSecondary = barkColor;

        this.log = generateLog(registry, id, getPrimaryColor(), getSecondaryColor(), soundType);

    }

    protected DeferredBlock<RotatedPillarBlock> generateLog(DeferredRegister.Blocks registry, String id, MapColor woodColor, MapColor barkColor, SoundType soundType) {
        return registry.register(id + "_log", () -> new RotatedPillarBlock(Properties.of()
                .mapColor(p_152624_ -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? woodColor : barkColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
    }

    @Override
    public void blockGen(BlockStateProvider data) {
        data.logBlock(this.log.get());
    }

    @Override
    public void itemGen(ItemModelProvider data) {
        ResourceLocation logID = DataGenUtil.getId(log, BuiltInRegistries.BLOCK);
        data.withExistingParent(logID.getPath(), logID.withPrefix("block/"));
    }

    @Override
    public void langGen(LanguageProvider data) {
        data.addBlock(log, DataGenUtil.getNameLocalized(log, BuiltInRegistries.BLOCK));
    }

    protected MapColor getPrimaryColor() {
        return this.colorPrimary;
    }

    protected MapColor getSecondaryColor() {
        return this.colorSecondary;
    }
}
