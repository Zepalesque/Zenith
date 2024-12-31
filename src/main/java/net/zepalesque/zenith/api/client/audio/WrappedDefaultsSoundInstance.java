package net.zepalesque.zenith.api.client.audio;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import org.jetbrains.annotations.Nullable;

/**
 * Wrapper class for {@link SoundInstance}, with certain parameters replaced with their defaults.<br>
 * Intended to be used with {@link PlaySoundEvent} -- you should set the new sound instance via {@link PlaySoundEvent#setSound(SoundInstance)}, wrapping the original instance.
 * <br>
 * @param inner The sound instance you wish to modify
 * @param flags The flags for this instance, represented as an integer.
 * <br><br>
 * FLAGS (Add each number for the flags you want to override with the default value of)<br>
 * 1: getSource - default: {@link SoundSource#MASTER}<br>
 * 2: isLooping - default: false<br>
 * 4: isRelative - default: false<br>
 * 8: getDelay - default: 0<br>
 * 16: getVolume - default: 10.0F<br>
 * 32: getPitch - default: 1.0F<br>
 * 64: getAttenuation - default: {@link Attenuation#LINEAR}
 */
public record WrappedDefaultsSoundInstance<S extends SoundInstance>(S inner, byte flags) implements SoundInstance {

    private static final byte[] powers = { 0b1, 0b10, 0b100, 0b1000, 0b10000, 0b100000, 0b1000000 };

    public static <I extends SoundInstance> WrappedDefaultsSoundInstance<I> create(I inner, int flags) {
        return new WrappedDefaultsSoundInstance<>(inner, (byte) flags);
    }

    protected boolean getFlag(int index) {
        return (flags & powers[index]) != 0;
    }

    @Override
    public ResourceLocation getLocation() {
        return this.inner().getLocation();
    }

    @Override
    public Sound getSound() {
        return this.inner().getSound();
    }

    @Nullable
    @Override
    public WeighedSoundEvents resolve(SoundManager manager) {
        return this.inner().resolve(manager);
    }

    @Override
    public SoundSource getSource() {
        return getFlag(0) ? SoundSource.MASTER : this.inner().getSource();
    }

    @Override
    public boolean isLooping() {
        return !getFlag(1) && this.inner().isLooping();
    }

    @Override
    public boolean isRelative() {
        return !getFlag(2) && this.inner().isRelative();
    }

    @Override
    public int getDelay() {
        return getFlag(3) ? 0 : this.inner().getDelay();
    }

    @Override
    public float getVolume() {
        return getFlag(4) ? 10F : this.inner().getVolume();
    }

    @Override
    public float getPitch() {
        return getFlag(5) ? 1F : this.inner().getPitch();
    }

    @Override
    public double getX() {
        return this.inner().getX();
    }

    @Override
    public double getY() {
        return this.inner().getY();
    }

    @Override
    public double getZ() {
        return this.inner().getZ();
    }

    @Override
    public Attenuation getAttenuation() {
        return getFlag(6) ? Attenuation.LINEAR : this.inner().getAttenuation();
    }

    public S inner() {
        return this.inner;
    }
}
