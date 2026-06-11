package fr.stylobow.iyc.block.custom;

import fr.stylobow.iyc.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class HopsCropBlock extends CropBlock {
    public HopsCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.HOPS_SEEDS.get();
    }
}