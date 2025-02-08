package net.zepalesque.zenith.api.recipe.serializer;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zepalesque.zenith.api.recipe.recipes.NoneRecipe;

public class NoneRecipeSerializer implements RecipeSerializer<NoneRecipe>  {

    private static final NoneRecipe INSTANCE = new NoneRecipe();

    public static final MapCodec<NoneRecipe> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, NoneRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);



    @Override
    public MapCodec<NoneRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, NoneRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
