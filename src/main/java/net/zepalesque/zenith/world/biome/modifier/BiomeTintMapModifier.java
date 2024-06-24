package net.zepalesque.zenith.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.zenith.api.biometint.BiomeTint;
import net.zepalesque.zenith.api.biometint.BiomeTints;
import net.zepalesque.zenith.util.codec.ZenithCodecs;
import net.zepalesque.zenith.util.predicate.MusicPredicate;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

public record BiomeTintMapModifier(Holder<BiomeTint> tintHolder, Map<Holder<Biome>, Integer> colors, Optional<Integer> defaultOverride) implements BiomeModifier {


    public static final Codec<BiomeTintMapModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            BiomeTints.TINT_REGISTRY.holderByNameCodec().fieldOf("tint_id").forGetter(BiomeTintMapModifier::tintHolder),
            ZenithCodecs.BIOME_COLOR_MAP.fieldOf("colors").forGetter(BiomeTintMapModifier::colors),
            Codec.INT.optionalFieldOf("default_color_override").forGetter(BiomeTintMapModifier::defaultOverride)).apply(builder, BiomeTintMapModifier::new));


    @Override
    @ParametersAreNonnullByDefault
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && this.colors.containsKey(biome) && this.tintHolder.isBound()) {
            BiomeTint tint = this.tintHolder.value();
            tint.
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }

    public record MusicOperator(Optional<Holder<SoundEvent>> sound, Optional<Integer> minDelay, Optional<Integer> maxDelay, Optional<Boolean> replaceCurrent) implements UnaryOperator<Music> {

        public static final Codec<MusicOperator> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                SoundEvent.CODEC.optionalFieldOf("sound").forGetter(MusicOperator::sound),
                Codec.INT.optionalFieldOf("min_delay").forGetter(MusicOperator::minDelay),
                Codec.INT.optionalFieldOf("max_delay").forGetter(MusicOperator::maxDelay),
                Codec.BOOL.optionalFieldOf("replace_current").forGetter(MusicOperator::replaceCurrent)).apply(builder, MusicOperator::new));

        @Override
        public Music apply(Music music) {
            if (sound.isEmpty() && minDelay.isEmpty() && maxDelay.isEmpty() && replaceCurrent.isEmpty()) {
                return music;
            }
            Holder<SoundEvent> soundEvent = music.getEvent();
            int minimum = music.getMinDelay();
            int maximum = music.getMaxDelay();
            boolean replace = music.replaceCurrentMusic();
            if (sound.isPresent()) { soundEvent = sound.get(); }
            if (minDelay.isPresent()) { minimum = minDelay.get(); }
            if (maxDelay.isPresent()) { maximum = maxDelay.get(); }
            if (replaceCurrent.isPresent()) { replace = replaceCurrent.get(); }
            return new Music(soundEvent, minimum, maximum, replace);
        }
    }
}
