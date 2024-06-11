package net.zepalesque.zenith.recipe;

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
import net.zepalesque.zenith.advancement.trigger.ZenithAdvancementTriggers;
import net.zepalesque.zenith.recipe.recipes.AbstractStackingRecipe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class StackingRecipeHelper {

    private static final Map<Holder<? extends RecipeType<?>>, Holder<RecipeType<?>>> HOLDER_MAP = new HashMap<>();

    // Additional behavior such as ClickAction types should be done in the event listener/hook
    // The return value of this should be used to cancel the event. False means do not cancel, true means DO cancel
    public static <R extends AbstractStackingRecipe> boolean stack(ItemStackedOnOtherEvent event, Predicate<ItemStack> carriedPredicate, Holder<? extends RecipeType<R>> type) {
        // These seem to be inverted for whatever reason?
        ItemStack carried = event.getStackedOnItem();
        ItemStack stackedOn = event.getCarriedItem();
        Level level = event.getPlayer().level();
        Player player = event.getPlayer();
        Slot slot = event.getSlot();
        if (carriedPredicate.test(carried)) {
            for (RecipeHolder<R> holder : level.getRecipeManager().getAllRecipesFor(type.value())) {
                if (holder != null) {
                    R recipe = holder.value();
                    if (recipe.matches(level, stackedOn)) {
                        ItemStack newStack = recipe.getResultStack(stackedOn);
                        if (newStack != null) {
                            if (!level.isClientSide()) {
                                // Crazy wacko holder magic because java's type generics hate me
                                ZenithAdvancementTriggers.STACKING_RECIPE.get().trigger((ServerPlayer) player, stackedOn, newStack, HOLDER_MAP.computeIfAbsent(type, key -> Holder.direct(key.value())));
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
