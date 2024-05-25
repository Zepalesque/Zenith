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

public record BiomeTintSyncPacket(Map<ResourceLocation, Map<ResourceLocation, Integer>> types) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Zenith.MODID, "sync_biome_tints");

    public void write(FriendlyByteBuf buf) {
        buf.writeMap(types, FriendlyByteBuf::writeResourceLocation, (b1, map) -> b1.writeMap(map, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeInt));
    }

    public static BiomeTintSyncPacket decode(FriendlyByteBuf buf) {
        Map<ResourceLocation, Map<ResourceLocation, Integer>> map = buf.readMap(FriendlyByteBuf::readResourceLocation, b1 -> b1.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readInt));
        return new BiomeTintSyncPacket(map);
    }

    public void execute(Player player) {
        if (Minecraft.getInstance().player != null) {
            Level level = Minecraft.getInstance().player.level();
            this.types.forEach((tintType, map) ->{
                @Nullable BiomeTint tint = BiomeTints.TINT_REGISTRY.get(tintType);
                if (tint != null) {
                    tint.clear();
                    Registry<Biome> registry = level.registryAccess().registryOrThrow(Registries.BIOME);
                    for (Map.Entry<ResourceLocation, Integer> entry : map.entrySet()) {
                        Biome b = registry.get(entry.getKey());
                        tint.addTint(b, entry.getValue());
                    }
                } else {
                    Zenith.LOGGER.warn("Attempted to read non-existent BiomeTint {}!", tintType);
                }
            });
        }
    }

    public void handle(PlayPayloadContext context) {
        context.workHandler().execute(() -> this.execute(context.player().orElse(null)));
    }


    @Override
    public ResourceLocation id() {
        return ID;
    }




}
