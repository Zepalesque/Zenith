package net.zepalesque.zenith.util.function.type;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * <p>Tweaked version of RecipeTypePredicate (??)</p>
 * <p><b>TODO:</b> Figure out what the 'tweaked' part meant</p>
 * @param types The valid {@link RecipeType RecipeTypes} that will satisfy this predicate's requirements.
 */
public record RecipeTypePredicate(HolderSet<RecipeType<?>> types) implements Predicate<Holder<RecipeType<?>>> {
    public static final Codec<RecipeTypePredicate> CODEC = Codec.either(
                    TagKey.hashedCodec(Registries.RECIPE_TYPE), BuiltInRegistries.RECIPE_TYPE.holderByNameCodec()
            )
            .flatComapMap(
                    tagOrHolder -> tagOrHolder.map(
                            tag -> new RecipeTypePredicate(BuiltInRegistries.RECIPE_TYPE.getOrCreateTag(tag)),
                            holder -> new RecipeTypePredicate(HolderSet.direct(holder))
                    ),
                    predicate -> {
                        HolderSet<RecipeType<?>> holderset = predicate.types();
                        Optional<TagKey<RecipeType<?>>> optional = holderset.unwrapKey();
                        if (optional.isPresent()) {
                            return DataResult.success(Either.left(optional.get()));
                        } else {
                            return holderset.size() == 1
                                    ? DataResult.success(Either.right(holderset.get(0)))
                                    : DataResult.error(() -> "Recipe types set must have a single element, but got " + holderset.size());
                        }
                    }
            );

    public static RecipeTypePredicate of(Holder<RecipeType<?>> type) {
        return new RecipeTypePredicate(HolderSet.direct(type));
    }

    public static RecipeTypePredicate of(TagKey<RecipeType<?>> tag) {
        return new RecipeTypePredicate(BuiltInRegistries.RECIPE_TYPE.getOrCreateTag(tag));
    }

    public boolean test(Holder<RecipeType<?>> type) {
        return this.types.contains(type);
    }
}
