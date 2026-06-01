package fr.stylobow.iyc.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LampBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    public LampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIT, false)
                .setValue(NORTH, false).setValue(EAST, false)
                .setValue(SOUTH, false).setValue(WEST, false)
                .setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return this.defaultBlockState()
                .setValue(NORTH, this.canConnect(level.getBlockState(pos.north())))
                .setValue(EAST, this.canConnect(level.getBlockState(pos.east())))
                .setValue(SOUTH, this.canConnect(level.getBlockState(pos.south())))
                .setValue(WEST, this.canConnect(level.getBlockState(pos.west())))
                .setValue(UP, this.canConnect(level.getBlockState(pos.above())))
                .setValue(DOWN, this.canConnect(level.getBlockState(pos.below())));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return switch (facing) {
            case NORTH -> state.setValue(NORTH, this.canConnect(facingState));
            case EAST -> state.setValue(EAST, this.canConnect(facingState));
            case SOUTH -> state.setValue(SOUTH, this.canConnect(facingState));
            case WEST -> state.setValue(WEST, this.canConnect(facingState));
            case UP -> state.setValue(UP, this.canConnect(facingState));
            case DOWN -> state.setValue(DOWN, this.canConnect(facingState));
        };
    }

    private boolean canConnect(BlockState state) {
        Block block = state.getBlock();

        if (block instanceof RodBlock || block instanceof LampBlock || block instanceof WallBlock) {
            return true;
        }
        if (block instanceof FlowerBlock || state.is(BlockTags.FLOWERS) || block instanceof BushBlock) {
            return false;
        }
        return state.isSolid();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(Items.FLINT_AND_STEEL) && !state.getValue(LIT)) {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.setBlock(pos, state.setValue(LIT, true), 11);

            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                stack.hurtAndBreak(1, serverPlayer, LivingEntity.getSlotForHand(hand));
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT) && random.nextInt(3) == 0) {
            double x = (double) pos.getX() + 0.5D;
            double y = (double) pos.getY() + 0.6D;
            double z = (double) pos.getZ() + 0.5D;
            level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Block.box(5.0D, 4.0D, 5.0D, 11.0D, 14D, 11.0D);

        if (state.getValue(DOWN)) {
            shape = Shapes.or(shape, Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4D, 11.0D));
        }
        if (state.getValue(UP)) {
            shape = Shapes.or(shape, Block.box(5.0D, 14.0D, 5.0D, 11.0D, 16D, 11.0D));
        }
        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, Block.box(7.0D, 14.0D, 0.0D, 9.0D, 16.0D, 9.0D));
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, Block.box(7.0D, 14.0D, 7.0D, 9.0D, 16.0D, 16.0D));
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, Block.box(0.0D, 14.0D, 7.0D, 9.0D, 16.0D, 9.0D));
        }
        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, Block.box(7.0D, 14.0D, 7.0D, 16.0D, 16.0D, 9.0D));
        }

        return shape;
    }
}