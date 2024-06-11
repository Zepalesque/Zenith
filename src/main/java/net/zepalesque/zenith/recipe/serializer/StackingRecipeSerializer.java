package net.zepalesque.zenith.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zepalesque.zenith.recipe.recipes.AbstractStackingRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class StackingRecipeSerializer<T extends AbstractStackingRecipe> implements RecipeSerializer<T> {
    private final AbstractStackingRecipe.Factory<T> factory;

    private final Codec<T> codec;

    public StackingRecipeSerializer(AbstractStackingRecipe.Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.create(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(AbstractStackingRecipe::getIngredient),
            ItemStack.CODEC.fieldOf("result").forGetter(AbstractStackingRecipe::getResult),
            CompoundTag.CODEC.optionalFieldOf("additional_data").forGetter(AbstractStackingRecipe::getAdditionalData),
            SoundEvent.CODEC.optionalFieldOf("additional_data").forGetter(AbstractStackingRecipe::getSound)
        ).apply(inst, this.factory::create));
    }

    @Override
    @NotNull
    public Codec<T> codec() {
        return this.codec;
    }

    @Override
    public T fromNetwork(FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();
        Optional<CompoundTag> function = buffer.readOptional(FriendlyByteBuf::readNbt);
        Optional<Holder<SoundEvent>> sound = buffer.readOptional(buf -> buf.readById(BuiltInRegistries.SOUND_EVENT.asHolderIdMap(), SoundEvent::readFromNetwork));
        return this.factory.create(ingredient, result, function, sound);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        recipe.getIngredient().toNetwork(buffer);
        buffer.writeItem(recipe.getResult());
        buffer.writeOptional(recipe.getAdditionalData(), FriendlyByteBuf::writeNbt);
        buffer.writeOptional(recipe.getSound(), (buf, holder) -> buf.writeId(BuiltInRegistries.SOUND_EVENT.asHolderIdMap(), holder, (buf1, sound) -> sound.writeToNetwork(buf1)));
    }
}

