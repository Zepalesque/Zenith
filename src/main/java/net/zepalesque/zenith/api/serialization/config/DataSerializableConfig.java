package net.zepalesque.zenith.api.serialization.config;

import com.google.gson.JsonSyntaxException;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.zenith.api.condition.type.ConfigCondition;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * A class for config files that can be used with {@link ConfigCondition ConfigConditions}
 */
public abstract class DataSerializableConfig {

    protected final Supplier<ModConfigSpec> spec;
    protected final String id;

    public DataSerializableConfig(Supplier<ModConfigSpec> spec, String id) {
        this.spec = spec;
        this.id = id;
    }

    /**
     * Registers a {@link ConfigSerializer} for this config.
     */
    public void registerSerializer() {
        ConfigCondition.registerSerializer(this.serializerID(), new ConfigSerializer(this::serialize, this::deserialize));
    }

    public String serialize(ModConfigSpec.ConfigValue<Boolean> config) {
        try {
            return config.getPath().toString();
        } catch (NullPointerException e) {
            throw new JsonSyntaxException("Error loading config entry from JSON! Maybe the config key is incorrect?");
        }
    }

    public ModConfigSpec.ConfigValue<Boolean> deserialize(String string) {
        List<String> path = Arrays.asList(string.replace("[", "").replace("]", "").split(", "));

        return this.spec().getValues().get(path);
    }

    public ModConfigSpec spec() {
        return spec.get();
    }

    public String serializerID() {
        return this.id;
    }
}
