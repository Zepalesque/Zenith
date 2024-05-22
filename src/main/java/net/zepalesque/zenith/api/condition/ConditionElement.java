package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;

public interface ConditionElement<T extends ConditionElement<?>> {

    boolean isMet();

    Codec<T> codec();

}
