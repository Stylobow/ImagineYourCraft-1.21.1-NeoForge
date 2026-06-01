package fr.stylobow.iyc.event;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.network.SkinSyncPayload;
import fr.stylobow.iyc.network.SyncTardisStatusPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.UUID;

@EventBusSubscriber(modid = ImagineYourCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerSyncEvents {

    private static final UUID DEV_UUID = UUID.fromString("ef3a9cb5-3544-4ba3-b0f5-2fe19d2363be");

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

        if (event.getTarget() instanceof ServerPlayer targetPlayer && event.getEntity() instanceof ServerPlayer trackingPlayer) {
            if (targetPlayer.getUUID().equals(DEV_UUID)) {
                boolean currentStatus = targetPlayer.getData(ModAttachmentTypes.TARDIS_VISIBLE);
                PacketDistributor.sendToPlayer(trackingPlayer, new SyncTardisStatusPacket(DEV_UUID, currentStatus));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getUUID().equals(DEV_UUID)) {
                boolean currentStatus = serverPlayer.getData(ModAttachmentTypes.TARDIS_VISIBLE);
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(serverPlayer, new SyncTardisStatusPacket(DEV_UUID, currentStatus));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getUUID().equals(DEV_UUID)) {
                boolean currentStatus = serverPlayer.getData(ModAttachmentTypes.TARDIS_VISIBLE);
                PacketDistributor.sendToPlayer(serverPlayer, new SyncTardisStatusPacket(DEV_UUID, currentStatus));
            }
        }
    }
}