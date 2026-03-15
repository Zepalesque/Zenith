package net.zepalesque.zenith.util.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import javax.annotation.Nullable;

import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

/**
 * A builder for creative tab insertions and modifications via a given {@link BuildCreativeModeTabContentsEvent}
 */
public class TabBuilder {
	protected final @Nullable BuildCreativeModeTabContentsEvent event;
	protected final ResourceKey<CreativeModeTab> tab;

	/**
	 * Create a new {@link TabBuilder} for the given creative tab.
	 * @param event The Event used for tab modification.
	 * @param tab The tab to be modified.
	 */
	public TabBuilder(BuildCreativeModeTabContentsEvent event, ResourceKey<CreativeModeTab> tab) {	
		this.tab = tab;
		this.event = event.getTabKey() == tab
			? event
			: null;
	}

	/**
	 * Put one or more items after a given base item.
	 * @param before The item to insert additional items after.
	 * @param inserted The items to insert after the {@code before} parameter, in sequential order.
	 */
	public TabBuilder putAfter(ItemLike before, ItemLike... inserted) {
		if (this.event == null) return this;

		var prev = before;
		for (var item : inserted) {
			event.insertAfter(stack(prev), stack(item), PARENT_AND_SEARCH_TABS);
			prev = item;
		}

		return this;
	}

	/**
	 * Put one or more items before a given base item.
	 * @param after The item to insert additional items before.
	 * @param inserted The items to insert after the {@code before} parameter, in sequential order -- Note that these will appear in the tab itself in reverse order, with the {@code after} parameter after all of them.
	 */
	public TabBuilder putBefore(ItemLike after, ItemLike... inserted) {
		if (this.event == null) return this;
		
		var prev = after;
		for (var item : inserted) {
			event.insertBefore(stack(prev), stack(item), PARENT_AND_SEARCH_TABS);
			prev = item;
		}

		return this;
	}

	/**
	 * Replace one item in the tab with a new one.
	 * @param toReplace The item to be replaced.
	 * @param replaceWith The item to replace {@code toReplace} with.
	 */
	public TabBuilder replace(ItemLike toReplace, ItemLike replaceWith) {
		if (this.event == null) return this;
		
		event.insertAfter(stack(toReplace), stack(replaceWith), PARENT_AND_SEARCH_TABS);
		event.remove(stack(toReplace), TabVisibility.PARENT_TAB_ONLY);
		
		return this;
	}

	/**
	 * Remove items from a tab.
	 * @param scope The extent of removal -- whether it should be removed from this tab, the search tab, or both.
	 * @param removed The items to be removed.
	 */
	public TabBuilder remove(TabVisibility scope, ItemLike... removed) {
		if (this.event == null) return this;

		for (var item : removed) event.remove(stack(item), scope);
		
		return this;
	}

	/**
	 * Add items to a creative tab.
	 * @param inserted The items to be added.
	 */
	public TabBuilder put(ItemLike... inserted) {	
		if (this.event == null) return this;

		for (var item : inserted) event.accept(stack(item), PARENT_AND_SEARCH_TABS);
		
		return this;
	}

	/**
	 * Get the tab this {@code TabBuilder} is modifying
	 */
	public ResourceKey<CreativeModeTab> getTab() {
		return this.tab;
	}

	// Helper method for creating an ItemStack from an item.
	private static ItemStack stack(ItemLike item) {
		return item.asItem().getDefaultInstance();
	}
}
