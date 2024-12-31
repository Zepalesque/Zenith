package net.zepalesque.zenith.api.condition.type;

import com.mojang.serialization.MapCodec;
import net.neoforged.fml.loading.FMLLoader;
import net.zepalesque.zenith.api.condition.Condition;

/**
 *
 */
public class DevEnvironmentCondition implements Condition<DevEnvironmentCondition> {

    public static final DevEnvironmentCondition INSTANCE = new DevEnvironmentCondition();

    public static final MapCodec<DevEnvironmentCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    public DevEnvironmentCondition() {
    }

    @Override
    public boolean test() {
        return !FMLLoader.isProduction();
    }

    @Override
    public MapCodec<DevEnvironmentCondition> codec() {
        return CODEC;
    }

}
