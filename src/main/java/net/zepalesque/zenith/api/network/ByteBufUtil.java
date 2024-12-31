package net.zepalesque.zenith.api.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamDecoder;

import java.util.Optional;

// TODO: Is this actually used anywhere, or is it a leftover from 1.20.4?
public class ByteBufUtil {

    public static <T> Optional<T> readOptionalNullable(FriendlyByteBuf buf, StreamDecoder<? super FriendlyByteBuf, T> p_320700_) {
        return buf.readBoolean() ? Optional.of(p_320700_.decode(buf)) : Optional.empty();
    }
}
