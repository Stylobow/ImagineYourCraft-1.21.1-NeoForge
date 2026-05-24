package fr.stylobow.iyc.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class CustomBlockMarkerParticle extends TextureSheetParticle {

    public CustomBlockMarkerParticle(ClientLevel level, double x, double y, double z, Block block) {
        super(level, x, y, z);

        this.setSprite(Minecraft.getInstance().getItemRenderer().getModel(
                new ItemStack(block), level, null, 0).getParticleIcon());

        this.gravity = 0.0F;
        this.lifetime = 80;
        this.hasPhysics = false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.TERRAIN_SHEET;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return 0.5F;
    }
}