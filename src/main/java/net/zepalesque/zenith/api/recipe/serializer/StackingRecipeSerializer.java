package net.zepalesque.zenith.api.recipe.serializer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zepalesque.zenith.api.itemstack.ItemStackConstructor;
import net.zepalesque.zenith.api.recipe.recipes.AbstractStackingRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class StackingRecipeSerializer<T extends AbstractStackingRecipe> implements RecipeSerializer<T> {
    private final AbstractStackingRecipe.Factory<T> factory;

    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public StackingRecipeSerializer(AbstractStackingRecipe.Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(AbstractStackingRecipe::getIngredient),
                ItemStackConstructor.CODEC.fieldOf("result").forGetter(AbstractStackingRecipe::getResult),
                CompoundTag.CODEC.optionalFieldOf("additional_data").forGetter(AbstractStackingRecipe::getAdditionalData),
                SoundEvent.CODEC.optionalFieldOf("sound").forGetter(AbstractStackingRecipe::getSound)
        ).apply(inst, this.factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    @Override
    @NotNull
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    private static final CompoundTag EMPTY = new CompoundTag();

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Holder<Item> result = buffer.readById(BuiltInRegistries.ITEM.asHolderIdMap()::byId);
        Optional<DataComponentPatch> resultTag = buffer.readOptional(buf -> DataComponentPatch.STREAM_CODEC.decode((RegistryFriendlyByteBuf) buf));
        ItemStackConstructor stack = new ItemStackConstructor(result, resultTag);
        Optional<CompoundTag> additional = buffer.readOptional(buf -> Objects.requireNonNullElse(buf.readNbt(), EMPTY)).flatMap(tag -> tag.isEmpty() ? Optional.empty() : Optional.of(tag));
        Optional<Holder<SoundEvent>> sound = buffer.readOptional(buf -> SoundEvent.STREAM_CODEC.decode(buffer));
        return this.factory.create(ingredient, stack, additional, sound);
    }


    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());
        buffer.writeById(BuiltInRegistries.ITEM.asHolderIdMap()::getId, recipe.getResult().item());
        buffer.writeOptional(recipe.getResult().tag(), (buf, tag) -> DataComponentPatch.STREAM_CODEC.encode((RegistryFriendlyByteBuf) buf, tag));
        buffer.writeOptional(recipe.getAdditionalData(), (buf, tag) -> buf.writeNbt(tag));
        buffer.writeOptional(recipe.getSound(), (buf, holder) -> SoundEvent.STREAM_CODEC.encode((RegistryFriendlyByteBuf)buf, holder));
    }
}

