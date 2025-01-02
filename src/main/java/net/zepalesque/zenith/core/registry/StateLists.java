package net.zepalesque.zenith.core.registry;

import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.zepalesque.zenith.api.extendablestate.ExtendableStateList;
import net.zepalesque.zenith.core.Zenith;

public class StateLists {
//    public static final DeferredRegister<ExtendableStateList> STATE_LISTS = DeferredRegister.create(Zenith.Keys.EXTENDABLE_STATE_LIST, Zenith.MODID);
    public static final Registry<ExtendableStateList> STATE_LIST_REGISTRY = new RegistryBuilder<>(Zenith.Keys.EXTENDABLE_STATE_LIST).sync(true).create();
}
