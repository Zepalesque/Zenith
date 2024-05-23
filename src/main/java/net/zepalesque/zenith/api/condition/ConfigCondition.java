package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.condition.config.ConfigSerializer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigCondition implements Condition<ConfigCondition> {

    private static final HashMap<String, ConfigSerializer> SERIALIZERS = new HashMap<>();

    public static Codec<ConfigCondition> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(
                            Codec.STRING.fieldOf("serializer").forGetter((config) -> config.serializerId),
                            Codec.STRING.fieldOf("config_path").forGetter((config) -> config.serializer == null || config.config == null ? "" : config.serializer.serialize(config.config))
                    )
                    .apply(condition, (serializerId, configPath) -> {
                        @Nullable ConfigSerializer serializer = SERIALIZERS.get(serializerId);
                        return new ConfigCondition(serializerId, serializer == null ? null : serializer.deserialize(configPath));
                    }));

    @Nullable
    protected final ConfigSerializer serializer;
    @Nullable
    protected final ConfigValue<Boolean> config;
    @Nullable
    protected final String serializerId;

    public ConfigCondition(@Nullable String serializerId, @Nullable ConfigValue<Boolean> config) {
        if (!SERIALIZERS.containsKey(serializerId)) {
            throw new UnsupportedOperationException("Attempted to create ConfigCondition with unregistered serializer!");
        }
        this.serializerId = serializerId;
        this.serializer = SERIALIZERS.get(serializerId);
        this.config = config;
    }

    @Override
    public boolean test() {
        return this.config == null || this.config.get();
    }

    @Override
    public Codec<ConfigCondition> codec() {
        return CODEC;
    }

    public static boolean registerSerializer(String id, ConfigSerializer serializer) {
        if (SERIALIZERS.containsKey(id)) {
            Zenith.LOGGER.warn("Attempted to register config serializer when one with the same id, {}, already exists! Skipping...", id);
            return false;
        }
        SERIALIZERS.putIfAbsent(id, serializer);
        return true;
    }
}
