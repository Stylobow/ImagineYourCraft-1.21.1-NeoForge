package fr.stylobow.iyc.event;

import fr.stylobow.iyc.client.skin.CustomSkinManager;
import fr.stylobow.iyc.network.SkinSyncPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientHandler {
    public static void handleSkinPacket(final SkinSyncPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            CustomSkinManager.receiveSkinPacket(payload.playerId(), payload.cosmeticType(), payload.textureUrl());
        });
    }
}