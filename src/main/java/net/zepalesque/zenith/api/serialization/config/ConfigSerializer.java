package net.zepalesque.zenith.api.serialization.config;

import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

import java.util.function.Function;

/**
 * A serializer for {@link ConfigValue ConfigValues}.
 */
public record ConfigSerializer(Function<ConfigValue<Boolean>, String> serialize, Function<String, ConfigValue<Boolean>> deserialize) {

    public ConfigValue<Boolean> deserialize(String string) {
        return this.deserialize.apply(string);
    }

    public String serialize(ConfigValue<Boolean> config) {
        return this.serialize.apply(config);
    }
}
