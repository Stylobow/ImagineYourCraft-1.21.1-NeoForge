package fr.stylobow.iyc.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SpeedBoxBlock extends Block {

    public SpeedBoxBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isShiftKeyDown() && entity instanceof LivingEntity livingEntity) {
            if (livingEntity.xxa == 0 && livingEntity.zza == 0) {
                super.stepOn(level, pos, state, entity);
                return;
            }

            double baseSpeed = 2D;

            Vec3 lookAngle = livingEntity.getLookAngle();

            double moveX = lookAngle.x * livingEntity.zza + lookAngle.z * livingEntity.xxa;
            double moveZ = lookAngle.z * livingEntity.zza - lookAngle.x * livingEntity.xxa;

            double length = Math.sqrt(moveX * moveX + moveZ * moveZ);
            if (length > 0) {
                moveX = (moveX / length) * baseSpeed;
                moveZ = (moveZ / length) * baseSpeed;

                livingEntity.setDeltaMovement(moveX, livingEntity.getDeltaMovement().y, moveZ);
            }
        }

        super.stepOn(level, pos, state, entity);
    }
}