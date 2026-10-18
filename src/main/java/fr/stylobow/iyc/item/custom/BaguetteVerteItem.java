package fr.stylobow.iyc.item.custom;

import fr.stylobow.iyc.world.gamerule.ModGameRules;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

public class BaguetteVerteItem extends Item {

    public BaguetteVerteItem(Properties properties) {
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
            Vec3 endPos = startPos.add(lookVec.scale(35));

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

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 1.0f, 1.0f);

            createFirework(serverLevel, impactPos);

            AABB effectBox = new AABB(impactPos, impactPos).inflate(4);
            List<Player> targetPlayers = level.getEntitiesOfClass(Player.class, effectBox);

            for (Player targetPlayer : targetPlayers) {
                boolean shouldApply = false;

                if (player.getTeam() == null) {
                    shouldApply = (targetPlayer == player);
                } else {
                    shouldApply = targetPlayer.isAlliedTo(player);
                }

                if (shouldApply) {
                    targetPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 140, 3));
                    targetPlayer.heal(8.0F);
                }
            }

            player.getCooldowns().addCooldown(this, 250);

            ItemStack stack = player.getItemInHand(hand);
            if (player instanceof ServerPlayer serverPlayer) {
                stack.hurtAndBreak(1, serverLevel, serverPlayer,
                        item -> player.onEquippedItemBroken(item, Player.getSlotForHand(hand))
                );
            }
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private void createFirework(Level level, Vec3 pos) {
        ItemStack rocketStack = new ItemStack(Items.FIREWORK_ROCKET);

        IntList colors = new IntArrayList(new int[]{
                0x009A00,
                0x00CD00,
        });

        FireworkExplosion explosion = new FireworkExplosion(
                FireworkExplosion.Shape.SMALL_BALL,
                colors,
                IntList.of(),
                true,
                false
        );

        Fireworks fireworksData = new Fireworks(0, List.of(explosion));
        rocketStack.set(DataComponents.FIREWORKS, fireworksData);

        FireworkRocketEntity rocketEntity = new FireworkRocketEntity(level, pos.x, pos.y, pos.z, rocketStack);
        rocketEntity.setDeltaMovement(Vec3.ZERO);
        rocketEntity.setNoGravity(true);

        level.addFreshEntity(rocketEntity);
        level.broadcastEntityEvent(rocketEntity, (byte) 17);
        rocketEntity.discard();
    }
}