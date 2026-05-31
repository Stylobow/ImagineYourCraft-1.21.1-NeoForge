package fr.stylobow.iyc.event;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.network.SkinSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ImagineYourCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerTrackingEventHandler {

    @SubscribeEvent
    public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof ServerPlayer trackedPlayer && event.getEntity() instanceof ServerPlayer processingPlayer) {
            byte[] skinBytes = trackedPlayer.getData(ModAttachmentTypes.SKIN_DATA.get());
            byte[] capeBytes = trackedPlayer.getData(ModAttachmentTypes.CAPE_DATA.get());
            byte[] hatBytes = trackedPlayer.getData(ModAttachmentTypes.HAT_DATA.get());

            if (skinBytes != null && skinBytes.length > 0) {
                String skinUrl = new String(skinBytes, java.nio.charset.StandardCharsets.UTF_8);
                SkinSyncPayload.sendChunked(trackedPlayer.getUUID(), 0, skinUrl, false, processingPlayer);
            }
            if (capeBytes != null && capeBytes.length > 0) {
                String capeUrl = new String(capeBytes, java.nio.charset.StandardCharsets.UTF_8);
                SkinSyncPayload.sendChunked(trackedPlayer.getUUID(), 1, capeUrl, false, processingPlayer);
            }
            if (hatBytes != null && hatBytes.length > 0) {
                String hatUrl = new String(hatBytes, java.nio.charset.StandardCharsets.UTF_8);
                SkinSyncPayload.sendChunked(trackedPlayer.getUUID(), 2, hatUrl, false, processingPlayer);
            }
        }
    }
}