package net.zepalesque.zenith.api.recipe;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.zepalesque.zenith.core.recipe.recipes.StackingRecipe;
import net.zepalesque.zenith.core.registry.ZenithAdvancementTriggers;
import net.zepalesque.zenith.api.recipe.recipes.AbstractStackingRecipe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Helps with implementing {@link StackingRecipe StackingRecipe} mechanics.
 *
 * <p>Example implementation from <a href="https://github.com/Zepalesque/The-Aether-Redux">The Aether: Redux</a>:</p>
 *
 * <pre><code>
 * <literal>@SubscribeEvent</literal>
 * public static void onStackItem(ItemStackedOnOtherEvent event) {
 *     if (event.getClickAction() == ClickAction.SECONDARY &&
 *             StackingRecipeHelper.stack(event, stack -> stack.is(AetherItems.AMBROSIUM_SHARD), ReduxRecipes.INFUSION.get())) {
 *         event.setCanceled(true);
 *     }
 * }
 * </code></pre>
 * NOTE: Future versions of Zenith may automate this process for you.
 */
public class StackingRecipeHelper {
    private static final Map<RecipeType<?>, Holder<RecipeType<?>>> DIRECT_HOLDERS = new HashMap<>();

    /**
     * <p>Replaces an item via a stacking recipe, if applicable.</p>
     * <p>Additional behavior such as ClickAction types should be done in the event listener/hook</p>
     *
     * @param event            The relevant {@link ItemStackedOnOtherEvent}
     * @param carriedPredicate A predicate determining whether the held item is valid for this recipe type.
     * @param type             The relevant {@link RecipeType}.
     * @return Whether the associated {@link ItemStackedOnOtherEvent} should be canceled.
     * <p>Do NOT use {@link net.neoforged.bus.api.ICancellableEvent#setCanceled(boolean) ICancellableEvent#setCanceled(boolean)}. Use an {@code if} check, and cancel the event if it passes.</p>
     */
    public static <R extends AbstractStackingRecipe> boolean stack(ItemStackedOnOtherEvent event, Predicate<ItemStack> carriedPredicate, RecipeType<R> type) {
        // These seem to be inverted for whatever reason?
        ItemStack carried = event.getStackedOnItem();
        ItemStack stackedOn = event.getCarriedItem();
        Level level = event.getPlayer().level();
        Player player = event.getPlayer();
        Slot slot = event.getSlot();
        if (carriedPredicate.test(carried)) {
            for (RecipeHolder<R> holder : level.getRecipeManager().getAllRecipesFor(type)) {
                if (holder != null) {
                    R recipe = holder.value();
                    if (recipe.matches(level, stackedOn)) {
                        ItemStack newStack = recipe.getResultStack(stackedOn);
                        if (newStack != null) {
                            if (!level.isClientSide()) {
                                // Crazy wacko holder magic because java's type generics hate me
                                ZenithAdvancementTriggers.STACKING_RECIPE.get().trigger((ServerPlayer) player, stackedOn, newStack, DIRECT_HOLDERS.computeIfAbsent(type, Holder::direct));
                            }
                            if (stackedOn.getCount() <= 1) {
                                slot.set(newStack);
                            } else {
                                stackedOn.shrink(1);
                                newStack.setCount(1);
                                boolean flag = player.getInventory().add(newStack);
                                if (!flag) {
                                    double d0 = player.getEyeY() - (double) 0.3F;
                                    ItemEntity itementity = new ItemEntity(level, player.getX(), d0, player.getZ(), newStack);
                                    itementity.setPickUpDelay(40);
                                    level.addFreshEntity(itementity);
                                } else {
                                    player.containerMenu.broadcastChanges();
                                }
                            }
                            carried.shrink(1);
                            slot.setChanged();
                            if (recipe.getSound().isPresent() && recipe.getSound().get().isBound()) {
                                level.playSound(player, player.getX(), player.getY(), player.getZ(), recipe.getSound().get().value(), SoundSource.PLAYERS, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}