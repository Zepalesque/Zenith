package net.zepalesque.zenith.api.condition.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.util.mod.CompatHelper;

public record ModLoadedCondition(String modid) implements Condition<ModLoadedCondition> {

    public static final MapCodec<ModLoadedCondition> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(Codec.STRING.fieldOf("modid").forGetter(ModLoadedCondition::modid))
                    .apply(builder, ModLoadedCondition::new));

    @Override
    public boolean test() {
        return CompatHelper.loaded(this.modid());
    }

    @Override
    public MapCodec<ModLoadedCondition> codec() {
        return CODEC;
    }

}
