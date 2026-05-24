package fr.stylobow.iyc.client;

import fr.stylobow.iyc.block.ModBlocks;
import fr.stylobow.iyc.client.particle.CustomBlockMarkerParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = "iyc", value = Dist.CLIENT)
public class ClientRenderEvents {

    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        ClientLevel level = mc.level;

        if (player != null && level != null && player.isHolding(ModBlocks.TRANSPARENT_LIGHT_BLOCK.get().asItem())) {

            tickCounter++;
            if (tickCounter % 20 != 0) return;

            BlockPos playerPos = player.blockPosition();
            int radius = 16;

            for (BlockPos pos : BlockPos.betweenClosed(
                    playerPos.offset(-radius, -radius, -radius),
                    playerPos.offset(radius, radius, radius))) {

                BlockState state = level.getBlockState(pos);

                if (state.is(ModBlocks.TRANSPARENT_LIGHT_BLOCK.get())) {
                    CustomBlockMarkerParticle particle = new CustomBlockMarkerParticle(
                            level,
                            pos.getX() + 0.5D,
                            pos.getY() + 0.5D,
                            pos.getZ() + 0.5D,
                            state.getBlock()
                    );

                    mc.particleEngine.add(particle);
                }
            }
        }
    }
}