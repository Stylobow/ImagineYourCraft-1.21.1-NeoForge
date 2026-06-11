package fr.stylobow.iyc.event;

import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.network.SkinSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class NetworkEvents {

    @SubscribeEvent
    public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof ServerPlayer targetedPlayer) {
            if (event.getEntity() instanceof ServerPlayer regionalPlayer) {

                byte[] skinBytes = targetedPlayer.getData(ModAttachmentTypes.SKIN_DATA.get());
                if (skinBytes != null && skinBytes.length > 0) {
                    String skin = new String(skinBytes, java.nio.charset.StandardCharsets.UTF_8);
                    SkinSyncPayload.sendChunked(targetedPlayer.getUUID(), 0, skin, false, regionalPlayer);
                }

                byte[] capeBytes = targetedPlayer.getData(ModAttachmentTypes.CAPE_DATA.get());
                if (capeBytes != null && capeBytes.length > 0) {
                    String cape = new String(capeBytes, java.nio.charset.StandardCharsets.UTF_8);
                    SkinSyncPayload.sendChunked(targetedPlayer.getUUID(), 0, cape, false, regionalPlayer);
                }

                byte[] hatBytes = targetedPlayer.getData(ModAttachmentTypes.HAT_DATA.get());
                if (hatBytes != null && hatBytes.length > 0) {
                    String hat = new String(hatBytes, java.nio.charset.StandardCharsets.UTF_8);
                    SkinSyncPayload.sendChunked(targetedPlayer.getUUID(), 0, hat, false, regionalPlayer);
                }
            }
        }
    }
}