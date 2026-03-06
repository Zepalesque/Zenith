package net.zepalesque.zenith.core.registry;

import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.neoforged.neoforge.registries.datamaps.AdvancedDataMapType;
import net.zepalesque.zenith.api.extstate.ExtendableStateList;
import net.zepalesque.zenith.core.Zenith;

import java.util.List;
import java.util.stream.Stream;

public class StateLists {
//    public static final DeferredRegister<ExtendableStateList> STATE_LISTS = DeferredRegister.create(Zenith.Keys.EXTENDABLE_STATE_LIST, Zenith.MODID);
    public static final Registry<ExtendableStateList> STATE_LIST_REGISTRY =
        new RegistryBuilder<>(Zenith.Keys.EXTENDABLE_STATE_LIST)
            .sync(true)
            .create();
   
    public static final AdvancedDataMapType<ExtendableStateList, HolderSet<ExtendableStateList.Entry>, ?> STATE_LIST_MODIFIERS =
        AdvancedDataMapType.builder(Zenith.loc("ext_state_list_modifier"),
                Zenith.Keys.EXTENDABLE_STATE_LIST,
            
                ExtendableStateList.Entry.LIST_CODEC
            ).merger((registry, srcA, a, srcB, b) ->
                HolderSet.direct(Stream.concat(a.stream(), b.stream()).toList())
            ).build();
}
