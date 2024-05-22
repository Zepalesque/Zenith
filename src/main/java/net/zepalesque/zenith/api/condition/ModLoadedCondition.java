package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.fml.ModList;

public class ModLoadedCondition implements Condition<ModLoadedCondition> {

    public static final Codec<ModLoadedCondition> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(Codec.STRING.fieldOf("modid").forGetter((config) -> config.modid))
                    .apply(condition, ModLoadedCondition::new));

    protected final String modid;

    public ModLoadedCondition(String modid) {
        this.modid = modid;
    }

    @Override
    public boolean isMet() {
        return ModList.get().isLoaded(this.modid);
    }

    @Override
    public Codec<ModLoadedCondition> codec() {
        return CODEC;
    }

}
