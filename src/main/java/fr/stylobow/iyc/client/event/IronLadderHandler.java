package fr.stylobow.iyc.client.event;

import fr.stylobow.iyc.block.custom.IronLadderBlock;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class IronLadderHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();

        if (player.level().isClientSide && player.onClimbable() && !player.isCrouching()) {
            BlockState state = player.level().getBlockState(player.blockPosition());

            if (state.getBlock() instanceof IronLadderBlock && player instanceof LocalPlayer localPlayer) {
                boolean isMovingForward = localPlayer.zza > 0;
                boolean isJumping = localPlayer.input.jumping;

                double speedModifier = 0.25D;

                if (isMovingForward || isJumping) {
                    Vec3 move = new Vec3(0, speedModifier, 0);
                    localPlayer.move(MoverType.SELF, move);
                }
            }
        }
    }
}