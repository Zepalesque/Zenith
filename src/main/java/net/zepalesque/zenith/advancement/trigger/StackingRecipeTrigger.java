package net.zepalesque.zenith.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;

import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.zepalesque.zenith.advancement.predicate.RecipeTypePredicate;

import java.util.Optional;

/**
 * Criterion trigger used for checking an item infused with an Ambrosium Shard.
 */
public class StackingRecipeTrigger extends SimpleCriterionTrigger<StackingRecipeTrigger.Instance> {

    public <R extends Recipe<?>> void trigger(ServerPlayer player, ItemStack ingredient, ItemStack result, Holder<RecipeType<?>> recipeType) {
        this.trigger(player, (instance) -> instance.test(ingredient, result, recipeType));
    }

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public record Instance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> ingredient, Optional<ItemPredicate> result, Optional<RecipeTypePredicate> types) implements SimpleInstance {

        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(Instance::player),
                        ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "ingredient").forGetter(Instance::ingredient),
                        ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "result").forGetter(Instance::result),
                        ExtraCodecs.strictOptionalField(RecipeTypePredicate.CODEC, "recipe_types").forGetter(Instance::types))
                .apply(instance, Instance::new));

        public static Instance forIngredient(Optional<ItemPredicate> item, Optional<RecipeTypePredicate> recipeType) {
            return new Instance(Optional.empty(), item, Optional.empty(), recipeType);
        }

        public static Instance forIngredient(ItemLike item, Optional<RecipeTypePredicate> recipeType) {
            return forIngredient(Optional.of(Builder.item().of(item).build()), recipeType);
        }

        public static Instance forResult(Optional<ItemPredicate> item, Optional<RecipeTypePredicate> recipeType) {
            return new Instance(Optional.empty(), Optional.empty(), item, recipeType);
        }

        public static Instance forResult(ItemLike item, Optional<RecipeTypePredicate> recipeType) {
            return forResult(Optional.of(Builder.item().of(item).build()), recipeType);
        }
        public static Instance forConversion(Optional<ItemPredicate> ingredient, Optional<ItemPredicate> result, Optional<RecipeTypePredicate> recipeType) {
            return new Instance(Optional.empty(), ingredient, result, recipeType);
        }

        public static Instance forConversion(ItemLike ingredient, ItemLike result, Optional<RecipeTypePredicate> recipeType) {
            return forConversion(Optional.of(Builder.item().of(ingredient).build()), Optional.of(Builder.item().of(result).build()), recipeType);
        }

        public static Instance forAny() {
            return forIngredient(Optional.empty(), Optional.empty());
        }

        public boolean test(ItemStack ingredient, ItemStack result, Holder<RecipeType<?>> type) {
            return (this.ingredient.isEmpty() || this.ingredient.get().matches(ingredient)) &&
                    (this.result.isEmpty() || this.result.get().matches(result)) &&
                    (this.types.isEmpty() || this.types.get().matches(type));
        }

    }
}
