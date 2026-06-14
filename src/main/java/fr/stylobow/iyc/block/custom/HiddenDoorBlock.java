package fr.stylobow.iyc.block.custom;

import fr.stylobow.iyc.block.entity.HiddenDoorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;

public class HiddenDoorBlock extends DoorBlock implements EntityBlock {

    public HiddenDoorBlock(Properties properties) {
        super(BlockSetType.STONE, properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HiddenDoorBlockEntity(pos, state);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);

        if (!level.isClientSide && state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos targetPos = pos.below();
            BlockState blockBelow = level.getBlockState(targetPos);

            if (blockBelow.getBlock() instanceof HiddenDoorBlock) {
                targetPos = targetPos.below();
                blockBelow = level.getBlockState(targetPos);
            }

            if (!blockBelow.isAir()) {
                BlockEntity beLower = level.getBlockEntity(pos);
                BlockEntity beUpper = level.getBlockEntity(pos.above());

                if (beLower instanceof HiddenDoorBlockEntity hiddenDoorBELower) {
                    hiddenDoorBELower.setMimickedBlock(blockBelow);
                }

                if (beUpper instanceof HiddenDoorBlockEntity hiddenDoorBEUpper) {
                    hiddenDoorBEUpper.setMimickedBlock(blockBelow);
                }
            }
        }
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        super.triggerEvent(state, level, pos, id, param);
        BlockEntity blockentity = level.getBlockEntity(pos);
        return blockentity == null ? false : blockentity.triggerEvent(id, param);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        if (level instanceof BlockAndTintGetter tintGetter) {
            BlockEntity be = tintGetter.getBlockEntity(pos);
            if (be instanceof HiddenDoorBlockEntity doorBe) {
                return doorBe.getMimickedBlock().propagatesSkylightDown(level, pos);
            }
        }
        return true;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        if (level instanceof BlockAndTintGetter tintGetter) {
            BlockEntity be = tintGetter.getBlockEntity(pos);
            if (be instanceof HiddenDoorBlockEntity doorBe) {
                return doorBe.getMimickedBlock().getShadeBrightness(level, pos);
            }
        }
        return super.getShadeBrightness(state, level, pos);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return super.skipRendering(state, adjacentBlockState, side);
    }
}