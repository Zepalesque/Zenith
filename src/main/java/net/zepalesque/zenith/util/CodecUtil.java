package net.zepalesque.zenith.util;


import com.mojang.serialization.Codec;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class CodecUtil {
    public static final Codec<HolderSet<SoundEvent>> SOUND_EVENT_SET = RegistryCodecs.homogeneousList(Registries.SOUND_EVENT, SoundEvent.DIRECT_CODEC);
}