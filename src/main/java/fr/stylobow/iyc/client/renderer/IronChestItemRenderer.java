package fr.stylobow.iyc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.stylobow.iyc.block.ModBlocks;
import fr.stylobow.iyc.block.entity.IronChestBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class IronChestItemRenderer extends BlockEntityWithoutLevelRenderer {
    public static final IronChestItemRenderer INSTANCE = new IronChestItemRenderer();
    private final IronChestBlockEntity ironChestBlockEntity;

    public IronChestItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.ironChestBlockEntity = new IronChestBlockEntity(BlockPos.ZERO, ModBlocks.IRON_CHEST.get().defaultBlockState());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(this.ironChestBlockEntity, poseStack, buffer, packedLight, packedOverlay);
    }
}
