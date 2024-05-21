package net.zepalesque.zenith.config;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.zenith.Zenith;
import org.apache.commons.lang3.tuple.Pair;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

@Mod.EventBusSubscriber(modid = Zenith.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ZConfig {

    public static class Common {

        public final ConfigValue<Boolean> placeholder;

        public Common(ModConfigSpec.Builder builder) {
            builder.push("TODO");
            placeholder = builder
                    .comment("Temporary placeholder config")
                    .define("placeholder", true);
            builder.pop();
        }
    }


    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

}
