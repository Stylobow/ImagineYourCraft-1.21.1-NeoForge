package fr.stylobow.iyc.network;

import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public record SyncTardisStatusPacket(UUID playerUuid, boolean visible) implements CustomPacketPayload {
    public static final Type<SyncTardisStatusPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("iyc", "sync_tardis_status"));

    public static final StreamCodec<FriendlyByteBuf, SyncTardisStatusPacket> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, SyncTardisStatusPacket::playerUuid,
            ByteBufCodecs.BOOL, SyncTardisStatusPacket::visible,
            SyncTardisStatusPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SyncTardisStatusPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                Player targetPlayer = level.getPlayerByUUID(payload.playerUuid());
                if (targetPlayer != null) {
                    targetPlayer.setData(ModAttachmentTypes.TARDIS_VISIBLE, payload.visible());
                }
            }
        });
    }
}