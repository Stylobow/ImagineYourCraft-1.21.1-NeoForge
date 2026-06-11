package fr.stylobow.iyc.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FireCampBlock extends Block {
    public static final MapCodec<FireCampBlock> CODEC = simpleCodec(FireCampBlock::new);

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);

    public FireCampBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = (double) pos.getX() + 0.5D;

        double y = (double) pos.getY() + 0.8D;

        double z = (double) pos.getZ() + 0.5D;

        level.addParticle(ParticleTypes.SMOKE, x + (random.nextDouble() - 0.5) * 0.3, y, z + (random.nextDouble() - 0.5) * 0.3, 0.0D, 0.01D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, x + (random.nextDouble() - 0.5) * 0.4, y - 0.2, z + (random.nextDouble() - 0.5) * 0.4, 0.0D, 0.01D, 0.0D);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide()) {
            if (entity instanceof ItemEntity) {
                entity.hurt(level.damageSources().inFire(), 100F);
            } else if (entity instanceof LivingEntity) {
                entity.hurt(level.damageSources().campfire(), 1.0F);
            }
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}