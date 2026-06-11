package fr.stylobow.iyc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SteelBrewingStandBlockEntity extends BrewingStandBlockEntity {
    public SteelBrewingStandBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.STEEL_BREWING_STAND_BE.get();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.iyc.steel_brewing_stand");
    }

    public static void customServerTick(Level level, BlockPos pos, BlockState state, SteelBrewingStandBlockEntity entity) {
        BrewingStandBlockEntity.serverTick(level, pos, state, entity);

        if (entity.dataAccess.get(1) > 0) {
            BrewingStandBlockEntity.serverTick(level, pos, state, entity);
        }
    }
}