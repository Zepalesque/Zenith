// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.
package net.zepalesque.zenith.api.serialization.codec.type;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;

import java.util.Map;

/** CODE COPY: {@link com.mojang.serialization.codecs.UnboundedMapCodec} -- Implements {@link BaseHashMapCodec} rather than {@link com.mojang.serialization.codecs.BaseMapCodec} */
public record UnboundedHashMapCodec<K, V>(
    Codec<K> keyCodec,
    Codec<V> elementCodec
) implements BaseHashMapCodec<K, V>, Codec<Map<K, V>> {
    @Override
    public <T> DataResult<Pair<Map<K, V>, T>> decode(final DynamicOps<T> ops, final T input) {
        return ops.getMap(input).setLifecycle(Lifecycle.stable()).flatMap(map -> decode(ops, map)).map(r -> Pair.of(r, input));
    }

    @Override
    public <T> DataResult<T> encode(final Map<K, V> input, final DynamicOps<T> ops, final T prefix) {
        return encode(input, ops, ops.mapBuilder()).build(prefix);
    }

    @Override
    public String toString() {
        return "UnboundedHashMapCodec[" + keyCodec + " -> " + elementCodec + ']';
    }

    public static <K, V> UnboundedHashMapCodec<K, V> of(final Codec<K> keyCodec, final Codec<V> elementCodec) {
        return new UnboundedHashMapCodec<>(keyCodec, elementCodec);
    }
}
