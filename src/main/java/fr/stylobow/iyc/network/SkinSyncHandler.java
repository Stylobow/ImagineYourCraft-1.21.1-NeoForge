package fr.stylobow.iyc.network;

import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.client.skin.CustomSkinManager;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SkinSyncHandler {

    public static void handle(final SkinSyncPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.flow().isServerbound()) {
                if (context.player() instanceof ServerPlayer serverPlayer) {
                    String url = payload.textureUrl();
                    byte[] dataBytes = ("RESET".equals(url) || url.isEmpty()) ? new byte[0] : url.getBytes(java.nio.charset.StandardCharsets.UTF_8);

                    if (payload.cosmeticType() == 0) {
                        serverPlayer.setData(ModAttachmentTypes.SKIN_DATA.get(), dataBytes);
                    } else if (payload.cosmeticType() == 1) {
                        serverPlayer.setData(ModAttachmentTypes.CAPE_DATA.get(), dataBytes);
                    } else if (payload.cosmeticType() == 2) {
                        serverPlayer.setData(ModAttachmentTypes.HAT_DATA.get(), dataBytes);
                    }
                    SkinSyncPayload.sendChunked(payload.playerId(), payload.cosmeticType(), payload.textureUrl(), false, null);
                }
            } else {
                if (FMLEnvironment.dist.isClient()) {
                    CustomSkinManager.registerRemoteCosmetic(payload.playerId(), payload.cosmeticType(), payload.textureUrl());
                }
            }
        });
    }
}