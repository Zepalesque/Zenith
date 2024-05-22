package net.zepalesque.zenith.api.condition.config;

import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

import java.util.function.Function;

public class ConfigSerializer {

    private final Function<ConfigValue<Boolean>, String> serialize;
    private final Function<String, ConfigValue<Boolean>> deserialize;

    public ConfigSerializer(Function<ConfigValue<Boolean>, String> serialize, Function<String, ConfigValue<Boolean>> deserialize) {
        this.serialize = serialize;
        this.deserialize = deserialize;
    }

    public ConfigValue<Boolean> deserialize(String string) {
        return this.deserialize.apply(string);
    }

    public String serialize(ConfigValue<Boolean> config) {
        return this.serialize.apply(config);
    }
}
