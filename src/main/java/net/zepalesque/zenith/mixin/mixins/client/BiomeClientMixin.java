package net.zepalesque.zenith.mixin.mixins.client;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.zepalesque.zenith.config.ZConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BiomeClientMixin {

    @Shadow
    public abstract BiomeSpecialEffects getModifiedSpecialEffects();

    @Shadow
    protected abstract int getGrassColorFromTexture();

    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    public void grass(double posX, double posZ, CallbackInfoReturnable<Integer> cir) {
        if (ZConfig.CLIENT.fix_biome_modifier_bug.get()) {
            int i = this.getModifiedSpecialEffects().getGrassColorOverride().orElseGet(this::getGrassColorFromTexture);
            cir.setReturnValue(this.getModifiedSpecialEffects().getGrassColorModifier().modifyColor(posX, posZ, i));
        }
    }
}
