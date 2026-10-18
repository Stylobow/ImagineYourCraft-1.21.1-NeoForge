package fr.stylobow.iyc.item.custom;

import fr.stylobow.iyc.world.gamerule.ModGameRules;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BaguetteJauneItem extends Item {

    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY_REQUIRED = 5;

    public BaguetteJauneItem(Properties properties) {
        super(properties.durability(MAX_ENERGY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.getGameRules().getBoolean(ModGameRules.ALLOW_SPECIAL_ITEMS)) {
            return InteractionResultHolder.fail(stack);
        }

        if (stack.getDamageValue() >= MAX_ENERGY - MIN_ENERGY_REQUIRED) {
            return InteractionResultHolder.fail(stack);
        }

        player.getTags().add("iyc_fall_protected");

        if (!level.isClientSide && level instanceof ServerLevel) {
            Vec3 currentMotion = player.getDeltaMovement();
            double clickBoost = 0.3;

            player.setDeltaMovement(currentMotion.x, currentMotion.y + clickBoost, currentMotion.z);
            player.hasImpulse = true;

            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
            }

            stack.setDamageValue(stack.getDamageValue() + 3);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if (entity instanceof Player player) {

            if (!level.getGameRules().getBoolean(ModGameRules.ALLOW_SPECIAL_ITEMS)) {
                player.stopUsingItem();
                return;
            }

            if (stack.getDamageValue() >= MAX_ENERGY - 1) {
                player.stopUsingItem();
                return;
            }

            player.getTags().add("iyc_fall_protected");
            player.resetFallDistance();

            if (!level.isClientSide && level instanceof ServerLevel) {

                Vec3 currentMotion = player.getDeltaMovement();
                double acceleration = 0.10D;

                player.setDeltaMovement(currentMotion.x, currentMotion.y + acceleration, currentMotion.z);
                player.hasImpulse = true;

                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                }

                stack.setDamageValue(stack.getDamageValue() + 1);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (entity instanceof Player player) {

            if (player.getTags().contains("iyc_fall_protected")) {
                player.resetFallDistance();

                if (player.onGround()) {
                    player.getTags().remove("iyc_fall_protected");
                }
            }

            if (!level.isClientSide) {
                if (player.getUseItem() != stack) {
                    if (level.getGameTime() % 2 == 0 && stack.getDamageValue() > 0) {
                        stack.setDamageValue(stack.getDamageValue() - 1);
                    }
                }
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}