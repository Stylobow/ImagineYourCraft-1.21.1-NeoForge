package fr.stylobow.iyc.event;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.network.SkinSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ImagineYourCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerSyncEvents {

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player trackedPlayer) {

            if (event.getEntity() instanceof ServerPlayer trackingPlayer) {

                if (trackedPlayer instanceof ServerPlayer targetServerPlayer) {

                    byte[] skinBytes = targetServerPlayer.getData(ModAttachmentTypes.SKIN_DATA);
                    if (skinBytes != null && skinBytes.length > 0) {
                        String skin = new String(skinBytes, java.nio.charset.StandardCharsets.UTF_8);
                        SkinSyncPayload.sendChunked(targetServerPlayer.getUUID(), 0, skin, false, trackingPlayer);
                    }

                    byte[] capeBytes = targetServerPlayer.getData(ModAttachmentTypes.CAPE_DATA);
                    if (capeBytes != null && capeBytes.length > 0) {
                        String cape = new String(capeBytes, java.nio.charset.StandardCharsets.UTF_8);
                        SkinSyncPayload.sendChunked(targetServerPlayer.getUUID(), 1, cape, false, trackingPlayer);
                    }

                    byte[] hatBytes = targetServerPlayer.getData(ModAttachmentTypes.HAT_DATA);
                    if (hatBytes != null && hatBytes.length > 0) {
                        String hat = new String(hatBytes, java.nio.charset.StandardCharsets.UTF_8);
                        SkinSyncPayload.sendChunked(targetServerPlayer.getUUID(), 2, hat, false, trackingPlayer);
                    }
                }
            }
        }
    }
}