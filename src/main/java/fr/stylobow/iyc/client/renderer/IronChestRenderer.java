package fr.stylobow.iyc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.block.custom.IronChestBlock;
import fr.stylobow.iyc.block.entity.IronChestBlockEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class IronChestRenderer implements BlockEntityRenderer<IronChestBlockEntity> {
    public static final Material IRON_CHEST_MATERIAL = new Material(
            Sheets.CHEST_SHEET,
            ResourceLocation.fromNamespaceAndPath(ImagineYourCraft.MOD_ID, "entity/chest/iron_chest")
    );

    private final ModelPart lid;
    private final ModelPart bottom;
    private final ModelPart lock;

    public IronChestRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(ModelLayers.CHEST);
        this.bottom = root.getChild("bottom");
        this.lid = root.getChild("lid");
        this.lock = root.getChild("lock");
    }

    @Override
    public void render(IronChestBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        BlockState blockState = level != null ? blockEntity.getBlockState() : null;
        Direction facing = (blockState != null && blockState.hasProperty(IronChestBlock.FACING))
                ? blockState.getValue(IronChestBlock.FACING)
                : Direction.SOUTH;

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
        poseStack.translate(-0.5D, -0.5D, -0.5D);

        float openness = blockEntity.getOpenNess(partialTick);
        openness = 1.0F - openness;
        openness = 1.0F - openness * openness * openness;

        VertexConsumer vertexConsumer = IRON_CHEST_MATERIAL.buffer(bufferSource, RenderType::entityCutout);

        this.lid.xRot = -(openness * ((float) Math.PI / 2F));
        this.lock.xRot = this.lid.xRot;

        this.lid.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        this.lock.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        this.bottom.render(poseStack, vertexConsumer, packedLight, packedOverlay);

        poseStack.popPose();
    }
}
