package net.zepalesque.zenith.util.codec;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;

public class ZenithCodecs {

    public static final Codec<Map<Holder<Biome>, Integer>> BIOME_COLOR_MAP = ExtraCodecs.strictUnboundedMap(Biome.CODEC, Codec.INT);


    public static final Codec<HolderSet<SoundEvent>> SOUND_EVENT_SET = RegistryCodecs.homogeneousList(Registries.SOUND_EVENT, SoundEvent.DIRECT_CODEC);
}
