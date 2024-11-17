package net.zepalesque.zenith.util;

import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;

public class TabUtil {

    @SuppressWarnings("unchecked")
    public static void putAfter(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> before, Supplier<? extends ItemLike> inserted, Supplier<? extends ItemLike>... others) {
        event.insertAfter(stack(before), stack(inserted), TabVisibility.PARENT_AND_SEARCH_TABS);
        if (others.length > 0) {
            event.insertAfter(stack(inserted), stack(others[0]), TabVisibility.PARENT_AND_SEARCH_TABS);
            for (int i = 1; i < others.length - 1; i++) {
                event.insertAfter(stack(others[i]), stack(others[i + 1]), TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void putBefore(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> after, Supplier<? extends ItemLike> inserted, Supplier<? extends ItemLike>... others) {
        event.insertBefore(stack(after), stack(inserted), TabVisibility.PARENT_AND_SEARCH_TABS);
        if (others.length > 0) {
            event.insertBefore(stack(inserted), stack(others[0]), TabVisibility.PARENT_AND_SEARCH_TABS);
            for (int i = 1; i < others.length - 1; i++) {
                event.insertBefore(stack(others[i]), stack(others[i + 1]), TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void remove(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> removed, Supplier<? extends ItemLike>... others) {
        event.remove(stack(removed), TabVisibility.PARENT_AND_SEARCH_TABS);
        for (Supplier<? extends ItemLike> item : others) {
            event.remove(stack(item), TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @SuppressWarnings("unchecked")
    public static void put(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> added, Supplier<? extends ItemLike>... others) {
        event.accept(stack(added), TabVisibility.PARENT_AND_SEARCH_TABS);
        for (Supplier<? extends ItemLike> item : others) {
            event.accept(stack(item), TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private static ItemStack stack(Supplier<? extends ItemLike> item) {
        return new ItemStack(item.get());
    }
}
