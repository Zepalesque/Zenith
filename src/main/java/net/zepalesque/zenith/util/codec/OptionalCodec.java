package net.zepalesque.zenith.util.codec;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.EitherCodec;

import java.util.Optional;

public class OptionalCodec<A> implements Codec<Optional<A>> {

    private final Codec<A> elementCodec;
    public OptionalCodec(final Codec<A> elementCodec) {
        this.elementCodec = elementCodec;
    }

    @Override
    public <T> DataResult<Pair<Optional<A>, T>> decode(DynamicOps<T> ops, T input) {
        final DataResult<Pair<Optional<A>, T>> decoded = elementCodec.decode(ops, input).map(pair -> pair.mapFirst(Optional::of));
        if (decoded.result().isPresent()) {
            return decoded;
        }
        return DataResult.success(Pair.of(Optional.empty(), input));
    }

    @Override
    public <T> DataResult<T> encode(Optional<A> input, DynamicOps<T> ops, T prefix) {
        return input.isPresent() ? elementCodec.encode(input.get(), ops, prefix) : DataResult.success(ops.createString("empty"));
    }
}
