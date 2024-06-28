package net.zepalesque.zenith.util.codec;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;

public class ZenithCodecs {

    public static final Codec<Map<Holder<Biome>, Integer>> MAP_CODEC = ExtraCodecs.strictUnboundedMap(Biome.CODEC, Codec.INT);

}
