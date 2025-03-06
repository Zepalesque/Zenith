package net.zepalesque.zenith.api.condition.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.api.serialization.config.ConfigSerializer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;

public record ConfigCondition(@Nullable String serializerId, @Nullable ConfigValue<Boolean> config) implements Condition<ConfigCondition> {

    private static final HashMap<String, ConfigSerializer> SERIALIZERS = new HashMap<>();

    public static MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                            Codec.STRING.fieldOf("serializer").forGetter(ConfigCondition::serializerId),
                            Codec.STRING.fieldOf("config_path").forGetter(ConfigCondition::serializePath)
                    )
                    .apply(builder, (id, path) -> {
                        @Nullable ConfigSerializer serializer = SERIALIZERS.get(id);
                        return new ConfigCondition(id, serializer == null ? null : serializer.deserialize(path));
                    }));

    // Record constructors are cool :eyes:
    public ConfigCondition {
        if (!SERIALIZERS.containsKey(serializerId))
            throw new UnsupportedOperationException("Attempted to create ConfigCondition with unregistered serializer!");
    }

    @Override
    public boolean test() {
        return this.config() == null || this.config().get();
    }

    @Override
    public MapCodec<ConfigCondition> codec() {
        return CODEC;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean registerSerializer(String id, ConfigSerializer serializer) {
        if (SERIALIZERS.containsKey(id)) {
            Zenith.LOGGER.warn("Attempted to register config serializer when one with the same id, {}, already exists! Skipping...", id);
            return false;
        }
        SERIALIZERS.putIfAbsent(id, serializer);
        return true;
    }

    private String serializePath() {
        Objects.requireNonNull(this.config());
        if (!SERIALIZERS.containsKey(this.serializerId()) || SERIALIZERS.get(this.serializerId()) == null) return "";
        else return SERIALIZERS.get(this.serializerId()).serialize(this.config());
    }
}
