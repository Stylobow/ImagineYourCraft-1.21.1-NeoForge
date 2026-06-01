package fr.stylobow.iyc.network;

import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public record ToggleTardisPacket() implements CustomPacketPayload {
    public static final Type<ToggleTardisPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("iyc", "toggle_tardis"));
    private static final UUID DEV_UUID = UUID.fromString("ef3a9cb5-3544-4ba3-b0f5-2fe19d2363be");

    public static final StreamCodec<FriendlyByteBuf, ToggleTardisPacket> STREAM_CODEC =
            StreamCodec.unit(new ToggleTardisPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final ToggleTardisPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.getUUID().equals(DEV_UUID)) {
                    boolean nextState = !serverPlayer.getData(ModAttachmentTypes.TARDIS_VISIBLE);
                    serverPlayer.setData(ModAttachmentTypes.TARDIS_VISIBLE, nextState);

                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(serverPlayer, new SyncTardisStatusPacket(DEV_UUID, nextState));
                }
            }
        });
    }
}