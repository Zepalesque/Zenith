package net.zepalesque.zenith.config;

import com.google.gson.JsonSyntaxException;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class ZConfig {

    public static class Common {

        public final ConfigValue<Boolean> allow_nonminecraft_autocomplete;
        public final ConfigValue<Boolean> search_as_containing;

        public Common(ModConfigSpec.Builder builder) {
            builder.push("Tweaks");
            builder.push("Suggestion Provider");
            allow_nonminecraft_autocomplete = builder
                    .comment("Allows non-minecraft namespaced results to come up when using commands to autocomplete an ID.")
                    .define("Allow Non-Minecraft Autocompletion", true);
            search_as_containing = builder
                    .comment("Searches for matches that CONTAIN the input rather than just ones that equal it. For instance, typing 'axe' will still return pickaxes with this enabled.")
                    .define("Search for IDs containing input", false);
            builder.pop(2);
        }
    }

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

    public static class Serializer {
        public static String serialize(ConfigValue<Boolean> config) {
            try {
                return config.getPath().toString();
            } catch (NullPointerException e) {
                throw new JsonSyntaxException("Error loading config entry from JSON! Maybe the config key is incorrect?");
            }
        }

        public static ConfigValue<Boolean> deserialize(String string) {
            List<String> path = Arrays.asList(string.replace("[", "").replace("]", "").split(", "));
            ConfigValue<Boolean> config =   COMMON_SPEC.getValues().get(path);

            return config;
        }
    }
}
