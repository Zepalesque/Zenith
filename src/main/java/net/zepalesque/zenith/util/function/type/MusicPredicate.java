package net.zepalesque.zenith.util.function.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.zepalesque.zenith.util.serialization.codec.MoreCodecs;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public record MusicPredicate(Optional<HolderSet<SoundEvent>> sounds, Optional<List<Integer>> minDelays, Optional<List<Integer>> maxDelays, Optional<Boolean> replaceCurrent) implements Predicate<Music> {

    public static final Codec<MusicPredicate> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            MoreCodecs.SOUND_EVENT_SET.optionalFieldOf("sounds").forGetter(MusicPredicate::sounds),
            Codec.INT.listOf().optionalFieldOf("valid_min_delays").forGetter(MusicPredicate::minDelays),
            Codec.INT.listOf().optionalFieldOf("valid_max_delays").forGetter(MusicPredicate::maxDelays),
            Codec.BOOL.optionalFieldOf("replaces_current").forGetter(MusicPredicate::replaceCurrent)).apply(builder, MusicPredicate::new));

    @Override
    public boolean test(Music music) {
        return (this.sounds.isEmpty()
              || this.sounds.get().contains(music.getEvent()))
            
            && (this.minDelays.isEmpty() || this.minDelays.get().isEmpty()
              || this.minDelays.get().contains(music.getMinDelay()))
            
            && (this.maxDelays.isEmpty()
              || this.maxDelays.get().isEmpty()
              || this.maxDelays.get().contains(music.getMaxDelay()))
            
            && (this.replaceCurrent.isEmpty()
                || music.replaceCurrentMusic() == this.replaceCurrent.get());
    }
}
