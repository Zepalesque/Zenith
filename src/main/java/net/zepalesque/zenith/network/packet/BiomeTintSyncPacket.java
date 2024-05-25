package net.zepalesque.zenith.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.biometint.BiomeTint;
import net.zepalesque.zenith.api.biometint.BiomeTints;

import javax.annotation.Nullable;
import java.util.Map;

public record BiomeTintSyncPacket(ResourceLocation id, Map<ResourceLocation, Integer> tintMap) implements BasePacket {

    public static final ResourceLocation ID = new ResourceLocation(Zenith.MODID, "sync_biome_tints");

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.id);
        buf.writeMap(this.tintMap, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeInt);
    }

    public static BiomeTintSyncPacket decode(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation();
        Map<ResourceLocation, Integer> map = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readInt);
        return new BiomeTintSyncPacket(id, map);
    }

    public void execute(Player player) {
        if (Minecraft.getInstance().player != null) {
            Level level = Minecraft.getInstance().player.level();
            Registry<Biome> registry = level.registryAccess().registryOrThrow(Registries.BIOME);
            @Nullable BiomeTint tint = BiomeTints.TINT_REGISTRY.get(this.id);
            if (tint != null) {
                tint.clear();
                this.tintMap.forEach((biomeLoc, color) ->{
                    Biome b = registry.get(biomeLoc);
                    tint.addTint(b, color);
                });
            } else {
                Zenith.LOGGER.warn("Attempted to read non-existent BiomeTint {}!", this.id);
            }
        }
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }




}
