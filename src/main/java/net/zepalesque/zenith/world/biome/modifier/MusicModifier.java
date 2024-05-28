package net.zepalesque.zenith.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.zenith.util.codec.CodecPredicates;

import java.util.Optional;

public record MusicModifier(
        HolderSet<Biome> biomes,
        Optional<Holder<SoundEvent>> event,
        Optional<CodecPredicates.DualInt> delay,
        Optional<Boolean> replaceCurrentMusic,
        Optional<CodecPredicates.Sound> soundPredicate,
        Optional<CodecPredicates.DualInt> delayPredicate,
        Optional<CodecPredicates.Bool> replacePredicate
) implements BiomeModifier {

    public static Codec<CodecPredicates.DualInt> DELAY = CodecPredicates.DualInt.createCodec("min", "max");

    public static final Codec<MusicModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(MusicModifier::biomes),
            SoundEvent.CODEC.optionalFieldOf("sound").forGetter(MusicModifier::event),
            MusicModifier.DELAY.optionalFieldOf("delay").forGetter(MusicModifier::delay),
            Codec.BOOL.optionalFieldOf("replace").forGetter(MusicModifier::replaceCurrentMusic),
            CodecPredicates.Sound.CODEC.optionalFieldOf("sound_predicate").forGetter(MusicModifier::soundPredicate),
            MusicModifier.DELAY.optionalFieldOf("delay_predicate").forGetter(MusicModifier::delayPredicate),
            CodecPredicates.Bool.CODEC.optionalFieldOf("replace_predicate").forGetter(MusicModifier::replacePredicate))
            .apply(builder, MusicModifier::new));


    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (this.biomes.contains(biome) && biome.value().getBackgroundMusic().isPresent()) {
            builder.getSpecialEffects().backgroundMusic(this.processMusic(biome.value().getBackgroundMusic().get()));
        }
    }


    private Music processMusic(Music music) {
        if (this.soundPredicate.isEmpty() && this.delay.isEmpty() && this.replaceCurrentMusic.isEmpty()) {
            return music;
        }
        Holder<SoundEvent> event = music.getEvent();
        if (this.event.isPresent() && (this.soundPredicate.isEmpty() || this.soundPredicate.get().test(music.getEvent()))) {
            event = this.event.get();
        }
        int minDelay = music.getMinDelay();
        int maxDelay = music.getMaxDelay();
        if ((this.delayPredicate.isEmpty() || this.delayPredicate.get().test(music.getMinDelay(), music.getMaxDelay()))) {
            if (this.delay.isPresent()) {
                minDelay = this.delay.get().arg1;
                maxDelay = this.delay.get().arg2;
            }
        }
        boolean replace = music.replaceCurrentMusic();
        if (this.replaceCurrentMusic.isPresent() && (this.replacePredicate.isEmpty() || this.replacePredicate.get().test(music.replaceCurrentMusic()))) {
            replace = this.replaceCurrentMusic.get();
        }
        return new Music(event, minDelay, maxDelay, replace);
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
