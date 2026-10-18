package fr.stylobow.iyc.item.custom;

import fr.stylobow.iyc.event.TaskScheduler;
import fr.stylobow.iyc.world.gamerule.ModGameRules;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.List;

public class TournevisItem extends Item {

    public static final int RIGHT_CLICK_COOLDOWN_TICKS = 30;
    public static final int LEFT_CLICK_COOLDOWN_TICKS = 140;

    public TournevisItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.getGameRules().getBoolean(ModGameRules.ALLOW_SPECIAL_ITEMS)) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {

            Vec3 startPos = player.getEyePosition();
            Vec3 lookVec = player.getLookAngle();
            double maxDistance = 75.0D;
            Vec3 endPos = startPos.add(lookVec.scale(maxDistance));

            BlockHitResult blockHit = level.clip(new ClipContext(
                    startPos,
                    endPos,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            ));

            Vec3 impactPos = blockHit.getType() != HitResult.Type.MISS ? blockHit.getLocation() : endPos;

            AABB searchBox = player.getBoundingBox()
                    .expandTowards(lookVec.scale(startPos.distanceTo(impactPos)))
                    .inflate(1.0D);

            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                    player,
                    startPos,
                    impactPos,
                    searchBox,
                    (entity) -> !entity.isSpectator() && entity.isPickable(),
                    0.0f
            );

            if (entityHit != null) {
                impactPos = entityHit.getLocation();
            }

            double distance = startPos.distanceTo(impactPos);
            for (double i = 0; i < distance; i += 0.5) {
                Vec3 particlePos = startPos.add(lookVec.scale(i));
                serverLevel.sendParticles(ParticleTypes.FIREWORK,
                        particlePos.x, particlePos.y, particlePos.z,
                        1, 0, 0, 0, 0);
            }

            createFirework(serverLevel, impactPos);

            AABB damageBox = new AABB(impactPos, impactPos).inflate(2.5D);
            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, damageBox);

            for (LivingEntity target : targets) {
                if (target != player) {
                    target.hurt(level.damageSources().fireworks(null, player), 7.5f);
                }
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 1.0f, 1.0f);

            player.getCooldowns().addCooldown(this, RIGHT_CLICK_COOLDOWN_TICKS);

            ItemStack stack = player.getItemInHand(hand);
            if (player instanceof ServerPlayer serverPlayer) {
                stack.hurtAndBreak(1, serverLevel, serverPlayer,
                        item -> player.onEquippedItemBroken(item, LivingEntity.getSlotForHand(hand))
                );
            }
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    public void performTeleport(ServerPlayer player, ItemStack stack) {
        ServerLevel serverLevel = player.serverLevel();

        if (!serverLevel.getGameRules().getBoolean(ModGameRules.ALLOW_SPECIAL_ITEMS)) {
            return;
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return;
        }

        player.getCooldowns().addCooldown(this, LEFT_CLICK_COOLDOWN_TICKS);

        stack.hurtAndBreak(1, serverLevel, player,
                item -> player.onEquippedItemBroken(item, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND))
        );

        Vec3 startPos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();
        double maxDistance = 75.0D;
        Vec3 endPos = startPos.add(lookVec.scale(maxDistance));

        BlockHitResult blockHit = serverLevel.clip(new ClipContext(
                startPos,
                endPos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        Vec3 targetTpPos = blockHit.getType() != HitResult.Type.MISS ? blockHit.getLocation() : endPos;

        double distance = startPos.distanceTo(targetTpPos);
        for (double i = 0; i < distance; i += 0.5) {
            Vec3 particlePos = startPos.add(lookVec.scale(i));
            serverLevel.sendParticles(ParticleTypes.FIREWORK,
                    particlePos.x, particlePos.y, particlePos.z,
                    1, 0, 0, 0, 0);
        }

        TaskScheduler.schedule(10, () -> {
            if (player.isAlive() && !player.hasDisconnected()) {
                player.teleportTo(targetTpPos.x, targetTpPos.y, targetTpPos.z);
                player.addTag("iyc_fall_protected");
                player.fallDistance = 0.0F;
            }
        });
    }

    private void createFirework(Level level, Vec3 pos) {
        ItemStack rocketStack = new ItemStack(Items.FIREWORK_ROCKET);

        FireworkExplosion explosion = new FireworkExplosion(
                FireworkExplosion.Shape.SMALL_BALL,
                IntList.of(0x00FF00),
                IntList.of(0x00AA00),
                false,
                false
        );

        Fireworks fireworksData = new Fireworks(0, List.of(explosion));
        rocketStack.set(DataComponents.FIREWORKS, fireworksData);

        FireworkRocketEntity rocketEntity = new FireworkRocketEntity(level, pos.x, pos.y, pos.z, rocketStack);
        level.addFreshEntity(rocketEntity);
        level.broadcastEntityEvent(rocketEntity, (byte) 17);
        rocketEntity.discard();
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            performTeleport(serverPlayer, stack);
        }
        return false;
    }
}