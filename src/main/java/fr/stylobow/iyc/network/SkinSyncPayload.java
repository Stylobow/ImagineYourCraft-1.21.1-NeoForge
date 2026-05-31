package fr.stylobow.iyc.network;

import fr.stylobow.iyc.client.SkinRenderManager;
import fr.stylobow.iyc.client.skin.CustomSkinManager;
import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public record SkinSyncPayload(UUID playerId, int cosmeticType, String textureUrl, int chunkIndex, boolean isLast) implements CustomPacketPayload {

    public static final Type<SkinSyncPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("iyc", "skin_sync"));

    private static final Map<UUID, Map<Integer, Map<Integer, String>>> serverAssemblyBuffers = new HashMap<>();
    private static final Map<UUID, Map<Integer, Map<Integer, String>>> clientAssemblyBuffers = new HashMap<>();

    public static final StreamCodec<FriendlyByteBuf, SkinSyncPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, SkinSyncPayload::playerId,
            ByteBufCodecs.INT, SkinSyncPayload::cosmeticType,
            ByteBufCodecs.stringUtf8(65535), SkinSyncPayload::textureUrl,
            ByteBufCodecs.INT, SkinSyncPayload::chunkIndex,
            ByteBufCodecs.BOOL, SkinSyncPayload::isLast,
            SkinSyncPayload::new
    );

    public static void sendChunked(UUID playerId, int cosmeticType, String textureUrl, boolean toServer, ServerPlayer targetPlayer) {
        String compressed = compressStringToBase64(textureUrl);
        int chunkSize = 30000;
        int totalLength = compressed.length();
        int index = 0;

        if (totalLength == 0) {
            SkinSyncPayload payload = new SkinSyncPayload(playerId, cosmeticType, "", 0, true);
            sendPayload(payload, toServer, targetPlayer);
            return;
        }

        for (int i = 0; i < totalLength; i += chunkSize) {
            int end = Math.min(totalLength, i + chunkSize);
            String chunk = compressed.substring(i, end);
            boolean isLast = (end == totalLength);

            SkinSyncPayload payload = new SkinSyncPayload(playerId, cosmeticType, chunk, index++, isLast);
            sendPayload(payload, toServer, targetPlayer);
        }
    }

    private static void sendPayload(SkinSyncPayload payload, boolean toServer, ServerPlayer targetPlayer) {
        if (toServer) {
            PacketDistributor.sendToServer(payload);
        } else {
            if (targetPlayer != null) {
                PacketDistributor.sendToPlayer(targetPlayer, payload);
            } else {
                PacketDistributor.sendToAllPlayers(payload);
            }
        }
    }

    public static void handle(final SkinSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Map<UUID, Map<Integer, Map<Integer, String>>> targetMap = context.flow().isClientbound() ? clientAssemblyBuffers : serverAssemblyBuffers;

            targetMap.putIfAbsent(payload.playerId(), new HashMap<>());
            targetMap.get(payload.playerId()).putIfAbsent(payload.cosmeticType(), new HashMap<>());

            Map<Integer, String> chunks = targetMap.get(payload.playerId()).get(payload.cosmeticType());
            chunks.put(payload.chunkIndex(), payload.textureUrl());

            if (!payload.isLast()) {
                return;
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < chunks.size(); i++) {
                String c = chunks.get(i);
                if (c != null) {
                    builder.append(c);
                }
            }
            chunks.clear();

            String fullUrl = decompressBase64ToString(builder.toString());
            String url = (fullUrl.isEmpty() || "RESET".equals(fullUrl)) ? "RESET" : fullUrl;

            if (context.flow().isClientbound()) {
                if (payload.cosmeticType() == 0) {
                    SkinRenderManager.registerPlayerSkin(payload.playerId(), url);
                    CustomSkinManager.registerRemoteCosmetic(payload.playerId(), 0, url);
                } else if (payload.cosmeticType() == 1) {
                    CustomSkinManager.registerRemoteCosmetic(payload.playerId(), 1, url);
                } else if (payload.cosmeticType() == 2) {
                    CustomSkinManager.registerRemoteCosmetic(payload.playerId(), 2, url);
                }
            } else {
                if (context.player() instanceof ServerPlayer serverPlayer) {
                    byte[] dataBytes = ("RESET".equals(url) || url.isEmpty()) ? new byte[0] : url.getBytes(StandardCharsets.UTF_8);

                    if (payload.cosmeticType() == 0) {
                        serverPlayer.setData(ModAttachmentTypes.SKIN_DATA.get(), dataBytes);
                    } else if (payload.cosmeticType() == 1) {
                        serverPlayer.setData(ModAttachmentTypes.CAPE_DATA.get(), dataBytes);
                    } else if (payload.cosmeticType() == 2) {
                        serverPlayer.setData(ModAttachmentTypes.HAT_DATA.get(), dataBytes);
                    }

                    ServerLevel level = serverPlayer.serverLevel();

                    Packet<?> removePacket = new ClientboundRemoveEntitiesPacket(serverPlayer.getId());
                    Packet<?> addPacket = new ClientboundAddEntityPacket(
                            serverPlayer.getId(),
                            serverPlayer.getUUID(),
                            serverPlayer.getX(),
                            serverPlayer.getY(),
                            serverPlayer.getZ(),
                            serverPlayer.getXRot(),
                            serverPlayer.getYRot(),
                            serverPlayer.getType(),
                            0,
                            serverPlayer.getDeltaMovement(),
                            (double) serverPlayer.getYHeadRot()
                    );

                    Packet<?> headRotPacket = new ClientboundRotateHeadPacket(serverPlayer, (byte) ((int) (serverPlayer.getYHeadRot() * 256.0F / 360.0F)));
                    Packet<?> metadataPacket = new ClientboundSetEntityDataPacket(serverPlayer.getId(), serverPlayer.getEntityData().getNonDefaultValues());

                    for (ServerPlayer otherPlayer : level.players()) {
                        if (otherPlayer != serverPlayer) {
                            otherPlayer.connection.send(removePacket);
                            otherPlayer.connection.send(addPacket);
                            otherPlayer.connection.send(headRotPacket);
                            otherPlayer.connection.send(metadataPacket);
                        }
                    }

                    sendChunked(payload.playerId(), payload.cosmeticType(), url, false, null);
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private static String compressStringToBase64(String str) {
        if (str == null || str.isEmpty()) return "";
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
            gzip.close();
            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (IOException e) {
            return "";
        }
    }

    private static String decompressBase64ToString(String base64Str) {
        if (base64Str == null || base64Str.isEmpty()) return "";
        try {
            byte[] bytes = Base64.getDecoder().decode(base64Str);
            try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                 GZIPInputStream gzip = new GZIPInputStream(in);
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = gzip.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                return out.toString(StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            return "";
        }
    }
}