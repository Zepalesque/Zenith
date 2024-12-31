package net.zepalesque.zenith.api.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Helpful methods for creative tab insertion via {@link BuildCreativeModeTabContentsEvent}
 */
public class TabUtil {

    /**
     * Put one or more items after a given base item.
     * @param event The relevant {@link BuildCreativeModeTabContentsEvent}.
     * @param before The item to insert additional items after.
     * @param inserted The first item to insert after the {@code before} parameter.
     * @param others The items to insert after the {@code inserted} parameter, in sequential order.
     */
    @SafeVarargs
    public static void putAfter(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> before, Supplier<? extends ItemLike> inserted, Supplier<? extends ItemLike>... others) {
        event.insertAfter(stack(before), stack(inserted), TabVisibility.PARENT_AND_SEARCH_TABS);
        if (others.length > 0) {
            event.insertAfter(stack(inserted), stack(others[0]), TabVisibility.PARENT_AND_SEARCH_TABS);
            for (int i = 1; i < others.length; i++) {
                event.insertAfter(stack(others[i - 1]), stack(others[i]), TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

    /**
     * Put one or more items before a given base item. Note that these are each inputted one before the next.
     * @param event The relevant {@link BuildCreativeModeTabContentsEvent}.
     * @param after The item to insert additional items before.
     * @param inserted The first item to insert before the {@code after} parameter.
     * @param others The items to insert before the {@code inserted} parameter, in sequential order -- Note that these will appear in the tab itself in reverse order, with the {@code inserted} parameter after all of them.
     */
    @SafeVarargs
    public static void putBefore(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> after, Supplier<? extends ItemLike> inserted, Supplier<? extends ItemLike>... others) {
        event.insertBefore(stack(after), stack(inserted), TabVisibility.PARENT_AND_SEARCH_TABS);
        if (others.length > 0) {
            event.insertBefore(stack(inserted), stack(others[0]), TabVisibility.PARENT_AND_SEARCH_TABS);
            for (int i = 1; i < others.length; i++) {
                event.insertBefore(stack(others[i - 1]), stack(others[i]), TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

    /**
     * Remove one or more items from a creative tab.
     * @param event The relevant {@link BuildCreativeModeTabContentsEvent}.
     * @param removeFromSearch Whether the item(s) should be removed from the search tab as well.
     * @param removed An item to remove from the tab.
     * @param others Any other items to be removed.
     */
    @SafeVarargs
    public static void remove(BuildCreativeModeTabContentsEvent event, boolean removeFromSearch, Supplier<? extends ItemLike> removed, Supplier<? extends ItemLike>... others) {
        TabVisibility visibility = removeFromSearch ? TabVisibility.PARENT_AND_SEARCH_TABS : TabVisibility.PARENT_TAB_ONLY;
        event.remove(stack(removed), visibility);
        for (Supplier<? extends ItemLike> item : others) {
            event.remove(stack(item), visibility);
        }
    }

    /**
     * Add one or more items to a creative tab.
     * @param event The relevant {@link BuildCreativeModeTabContentsEvent}.
     * @param added An item to add to the tab.
     * @param others Any other items to be added.
     */
    @SafeVarargs
    public static void put(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> added, Supplier<? extends ItemLike>... others) {
        event.accept(stack(added), TabVisibility.PARENT_AND_SEARCH_TABS);
        for (Supplier<? extends ItemLike> item : others) {
            event.accept(stack(item), TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    /**
     * Checks if a given {@link BuildCreativeModeTabContentsEvent} is for a given {@link CreativeModeTab}.
     * @param event The relevant {@link BuildCreativeModeTabContentsEvent}.
     * @param tab The relevant {@link CreativeModeTab}.
     * @return Whether the event correlates to the tab.
     */
    public static boolean isForTab(BuildCreativeModeTabContentsEvent event, CreativeModeTab tab) {
        return event.getTab() == tab;
    }

    /**
     * Checks if a given {@link BuildCreativeModeTabContentsEvent} is for a given {@link Supplier}<{@link CreativeModeTab}>.
     * @param event The relevant {@link BuildCreativeModeTabContentsEvent}.
     * @param tab The relevant {@link Supplier}<{@link CreativeModeTab}>.
     * @return Whether the event correlates to the tab.
     */
    public static boolean isForTab(BuildCreativeModeTabContentsEvent event, Supplier<CreativeModeTab> tab) {
        return event.getTab() == tab.get();
    }

    /**
     * Checks if a given {@link BuildCreativeModeTabContentsEvent} is for a given {@link ResourceKey}<{@link CreativeModeTab}>.
     * @param event The relevant {@link BuildCreativeModeTabContentsEvent}.
     * @param tab The relevant {@link ResourceKey}<{@link CreativeModeTab}>.
     * @return Whether the event correlates to the tab.
     */
    public static boolean isForTab(BuildCreativeModeTabContentsEvent event, ResourceKey<CreativeModeTab> tab) {
        return event.getTab() == BuiltInRegistries.CREATIVE_MODE_TAB.get(tab);
    }

    private static <T extends Supplier<? extends ItemLike>> ItemStack stack(T item) {
        return new ItemStack(item.get());
    }
}
