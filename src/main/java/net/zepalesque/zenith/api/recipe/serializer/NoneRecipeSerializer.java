package net.zepalesque.zenith.api.recipe.serializer;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zepalesque.zenith.api.recipe.recipes.NoneRecipe;

public record NoneRecipeSerializer() implements RecipeSerializer<NoneRecipe>  {


    public static final MapCodec<NoneRecipe> CODEC = MapCodec.unit(NoneRecipe.INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, NoneRecipe> STREAM_CODEC = StreamCodec.unit(NoneRecipe.INSTANCE);

    @Override
    public MapCodec<NoneRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, NoneRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
