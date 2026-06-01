package fr.stylobow.iyc.block.custom;

import fr.stylobow.iyc.entity.PrimedBombe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BombeBlock extends Block {
    public BombeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock())) {
            if (level.hasNeighborSignal(pos)) {
                catchFire(state, level, pos, null);
                level.removeBlock(pos, false);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.hasNeighborSignal(pos)) {
            catchFire(state, level, pos, null);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        catchFire(state, level, pos, igniter);
        level.removeBlock(pos, false);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!stack.is(Items.FLINT_AND_STEEL) && !stack.is(Items.FIRE_CHARGE)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        } else {
            catchFire(state, level, pos, player);
            level.removeBlock(pos, false);
            ItemStack itemStack = player.getItemInHand(hand);
            if (!player.isCreative()) {
                if (itemStack.is(Items.FLINT_AND_STEEL)) {
                    itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                } else {
                    itemStack.shrink(1);
                }
            }
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            return ItemInteractionResult.SUCCESS;
        }
    }

    public static void catchFire(BlockState state, Level level, BlockPos pos, @Nullable LivingEntity igniter) {
        if (!level.isClientSide) {
            PrimedBombe primedBombe = new PrimedBombe(level, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, igniter);
            level.addFreshEntity(primedBombe);
            level.playSound(null, primedBombe.getX(), primedBombe.getY(), primedBombe.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        if (!level.isClientSide) {
            PrimedBombe primedbombe = new PrimedBombe(level, (double)pos.getX() + (double)0.5F, (double)pos.getY(), (double)pos.getZ() + (double)0.5F, explosion.getIndirectSourceEntity());
            int i = primedbombe.getFuse();
            primedbombe.setFuse((short)(level.random.nextInt(i / 4) + i / 8));
            level.addFreshEntity(primedbombe);
        }
    }
}