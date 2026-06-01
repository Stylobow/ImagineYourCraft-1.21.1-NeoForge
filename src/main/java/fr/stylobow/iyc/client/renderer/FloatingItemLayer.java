package fr.stylobow.iyc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import java.util.UUID;

public class FloatingItemLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final UUID DEV_UUID = UUID.fromString("ef3a9cb5-3544-4ba3-b0f5-2fe19d2363be");

    public FloatingItemLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (player.getUUID().equals(DEV_UUID) && !player.isInvisible() && player.getData(ModAttachmentTypes.TARDIS_VISIBLE)) {
            poseStack.pushPose();

            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));

            float heightOffset = player.isCrouching() ? -0.3F : .05f;
            poseStack.translate(0.0F, heightOffset, 0.0F);

            float gameTime = ageInTicks + partialTick;
            float rotationDeg = gameTime * 4.0F;
            poseStack.mulPose(Axis.YP.rotationDegrees(rotationDeg));

            float alphaFloat = (float) (Math.sin(rotationDeg / 22.5) * 0.40f + 0.45f);
            int a = (int) (alphaFloat * 300);

            int r = 0, g = 0, b = 128;

            com.mojang.blaze3d.systems.RenderSystem.lineWidth(1.0F);

            VertexConsumer consumer = buffer.getBuffer(RenderType.lines());
            float baseSize = 0.7f;

            drawTardisWireframe(poseStack, consumer, baseSize, r, g, b, a);

            com.mojang.blaze3d.systems.RenderSystem.lineWidth(2.0F);

            poseStack.popPose();
        }
    }

    private void drawTardisWireframe(PoseStack poseStack, VertexConsumer consumer, float baseSize, int r, int g, int b, int a) {
        PoseStack.Pose pose = poseStack.last();

        float f4 = baseSize * 1.7F;
        float f5 = baseSize / 2.0F;
        float f6 = baseSize * 0.4F;
        float f7 = baseSize * 0.9F;
        float f8 = baseSize * 0.35F;
        float f9 = baseSize * 0.02F;
        float f10 = baseSize * 0.036F;
        float f11 = baseSize * 0.052F;
        float f12 = baseSize * 0.2F;
        float f13 = baseSize * 0.06F;
        float f14 = baseSize * 0.15F;
        float f15 = baseSize * 0.06F;
        float f16 = f11;
        float f17 = baseSize * 0.16F;
        float f18 = baseSize * 0.07F;

        drawTardizedBoxRadius(consumer, pose, f5, f4, f4 + f9, r, g, b, a);
        drawTardizedBoxRadius(consumer, pose, f6, f4 + f9, f4 + f9 + f7, r, g, b, a);
        drawTardizedBoxRadius(consumer, pose, f8, f4 + f7 + f9, f4 + f7 + f10, r, g, b, a);
        drawTardizedBoxRadius(consumer, pose, f12, f4 + f7 + f10, f4 + f7 + f11, r, g, b, a);
        drawTardizedBoxRadius(consumer, pose, f15, f4 + f7 + f13, f4 + f7 + f14, 255, 255, 255, a);
        drawTardizedBoxRadius(consumer, pose, f18, f4 + f7 + f16, f4 + f7 + f17, r, g, b, a);

        poseStack.pushPose();
        for (byte face = 0; face < 4; face++) {
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            PoseStack.Pose poseMain = poseStack.last();

            float gridBaseY = f4 + f9 + baseSize * 0.05F;
            float gap = baseSize * 0.05F;
            float h = f6 * 0.6F;

            float x25 = (float)Math.cos(Math.toRadians(45.0D)) * f6 - f6 * 0.8F;
            float z25 = (float)Math.sin(Math.toRadians(45.0D)) * f6;
            float x26 = (float)Math.cos(Math.toRadians(135.0D)) * f6 + f6 * 0.1F;
            float z26 = (float)Math.sin(Math.toRadians(135.0D)) * f6;

            float x27 = (float)Math.cos(Math.toRadians(45.0D)) * f6 - f6 * 0.1F;
            float z27 = (float)Math.sin(Math.toRadians(45.0D)) * f6;
            float x28 = (float)Math.cos(Math.toRadians(135.0D)) * f6 + f6 * 0.8F;
            float z28 = (float)Math.sin(Math.toRadians(135.0D)) * f6;

            drawGridWindow(consumer, poseMain, x25, z25, x26, z26, gridBaseY, h, gap, r, g, b, a);
            drawGridWindow(consumer, poseMain, x27, z27, x28, z28, gridBaseY, h, gap, r, g, b, a);
        }
        poseStack.popPose();
    }

    private void drawTardizedBoxRadius(VertexConsumer consumer, PoseStack.Pose pose, float radius, float bottomY, float topY, int r, int g, int b, int a) {
        float x1 = (float)Math.cos(Math.toRadians(45.0D)) * radius;
        float z1 = (float)Math.sin(Math.toRadians(45.0D)) * radius;
        float x2 = (float)Math.cos(Math.toRadians(135.0D)) * radius;
        float z2 = (float)Math.sin(Math.toRadians(135.0D)) * radius;
        float x3 = (float)Math.cos(Math.toRadians(225.0D)) * radius;
        float z3 = (float)Math.sin(Math.toRadians(225.0D)) * radius;
        float x4 = (float)Math.cos(Math.toRadians(315.0D)) * radius;
        float z4 = (float)Math.sin(Math.toRadians(315.0D)) * radius;

        drawLine(consumer, pose, x1, bottomY, z1, x2, bottomY, z2, r, g, b, a);
        drawLine(consumer, pose, x2, bottomY, z2, x3, bottomY, z3, r, g, b, a);
        drawLine(consumer, pose, x3, bottomY, z3, x4, bottomY, z4, r, g, b, a);
        drawLine(consumer, pose, x4, bottomY, z4, x1, bottomY, z1, r, g, b, a);

        drawLine(consumer, pose, x1, topY, z1, x2, topY, z2, r, g, b, a);
        drawLine(consumer, pose, x2, topY, z2, x3, topY, z3, r, g, b, a);
        drawLine(consumer, pose, x3, topY, z3, x4, topY, z4, r, g, b, a);
        drawLine(consumer, pose, x4, topY, z4, x1, topY, z1, r, g, b, a);

        drawLine(consumer, pose, x1, bottomY, z1, x1, topY, z1, r, g, b, a);
        drawLine(consumer, pose, x2, bottomY, z2, x2, topY, z2, r, g, b, a);
        drawLine(consumer, pose, x3, bottomY, z3, x3, topY, z3, r, g, b, a);
        drawLine(consumer, pose, x4, bottomY, z4, x4, topY, z4, r, g, b, a);
    }

    private void drawGridWindow(VertexConsumer consumer, PoseStack.Pose pose, float x1, float z1, float x2, float z2, float gridBaseY, float h, float gap, int r, int g, int b, int a) {
        drawGridRect(consumer, pose, x1, z1, x2, z2, gridBaseY, gridBaseY + h, r, g, b, a);
        drawGridRect(consumer, pose, x1, z1, x2, z2, gridBaseY + h + gap, gridBaseY + 2 * h + gap, r, g, b, a);
        drawGridRect(consumer, pose, x1, z1, x2, z2, gridBaseY + 2 * h + 2 * gap, gridBaseY + 3 * h + 2 * gap, r, g, b, a);
    }

    private void drawGridRect(VertexConsumer consumer, PoseStack.Pose pose, float x1, float z1, float x2, float z2, float bottomY, float topY, int r, int g, int b, int a) {
        drawLine(consumer, pose, x1, bottomY, z1, x2, bottomY, z2, r, g, b, a);
        drawLine(consumer, pose, x1, topY, z1, x2, topY, z2, r, g, b, a);
        drawLine(consumer, pose, x1, bottomY, z1, x1, topY, z1, r, g, b, a);
        drawLine(consumer, pose, x2, bottomY, z2, x2, topY, z2, r, g, b, a);
    }

    private void drawLine(VertexConsumer consumer, PoseStack.Pose pose, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b, int a) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float dz = z2 - z1;
        float len = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
        dx /= len;
        dy /= len;
        dz /= len;

        consumer.addVertex(pose.pose(), x1, y1, z1).setColor(r, g, b, a).setNormal(pose, dx, dy, dz);
        consumer.addVertex(pose.pose(), x2, y2, z2).setColor(r, g, b, a).setNormal(pose, dx, dy, dz);
    }
}