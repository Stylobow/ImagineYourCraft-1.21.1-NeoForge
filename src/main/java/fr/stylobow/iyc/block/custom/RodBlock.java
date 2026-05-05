package fr.stylobow.iyc.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RodBlock extends Block {
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty DOWN = PipeBlock.DOWN;

    public RodBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false).setValue(EAST, false)
                .setValue(SOUTH, false).setValue(WEST, false)
                .setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
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
        return state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(facing), this.canConnect(facingState));
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
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();

        if (state.getValue(DOWN) && state.getValue(UP)) {
            shape = Shapes.or(shape, Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D));
        } else if (state.getValue(DOWN)) {
            shape = Shapes.or(shape, Block.box(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D));
        } else if (state.getValue(UP)) {
            shape = Shapes.or(shape, Block.box(7.0D, 14.0D, 7.0D, 9.0D, 16.0D, 9.0D));
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

        return shape.isEmpty() ? Block.box(7.0D, 12.0D, 7.0D, 9.0D, 14.0D, 9.0D) : shape;
    }
}