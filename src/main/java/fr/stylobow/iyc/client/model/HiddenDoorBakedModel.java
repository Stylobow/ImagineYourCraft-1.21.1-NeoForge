package fr.stylobow.iyc.client.model;

import fr.stylobow.iyc.block.custom.HiddenDoorBlock;
import fr.stylobow.iyc.block.entity.HiddenDoorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HiddenDoorBakedModel implements IDynamicBakedModel {
    private final BakedModel originalModel;

    public static final ModelProperty<net.minecraft.world.level.BlockAndTintGetter> LEVEL_PROPERTY = new ModelProperty<>();
    public static final ModelProperty<net.minecraft.core.BlockPos> POS_PROPERTY = new ModelProperty<>();

    public HiddenDoorBakedModel(BakedModel originalModel) {
        this.originalModel = originalModel;
    }

    private @Nullable BakedModel getMimicModel(@NotNull ModelData modelData) {
        if (!modelData.has(HiddenDoorBlockEntity.MIMIC_PROPERTY)) {
            return null;
        }
        BlockState mimicState = modelData.get(HiddenDoorBlockEntity.MIMIC_PROPERTY);
        if (mimicState == null || mimicState.isAir() || (mimicState.getBlock() instanceof DoorBlock && !(mimicState.getBlock() instanceof HiddenDoorBlock))) {
            return null;
        }
        return Minecraft.getInstance().getBlockRenderer().getBlockModel(mimicState);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData modelData, @Nullable RenderType renderType) {
        if (state == null) {
            return this.originalModel.getQuads(state, side, rand, modelData, renderType);
        }

        List<BakedQuad> doorQuads = this.originalModel.getQuads(state, side, rand, modelData, renderType);
        if (doorQuads.isEmpty()) {
            return doorQuads;
        }

        BlockState mimicState = modelData.get(HiddenDoorBlockEntity.MIMIC_PROPERTY);
        BakedModel mimicModel = getMimicModel(modelData);
        if (mimicState == null || mimicModel == null) {
            return doorQuads;
        }

        TextureAtlasSprite mimicSprite = null;
        int extractedTintIndex = -1;

        try {
            FluidState fluidState = mimicState.getFluidState();
            if (!fluidState.isEmpty()) {
                IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidState.getType());
                var textureLocation = extensions.getStillTexture(fluidState, modelData.get(LEVEL_PROPERTY), modelData.get(POS_PROPERTY));
                if (textureLocation != null) {
                    mimicSprite = Minecraft.getInstance().getTextureAtlas(net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS).apply(textureLocation);
                }
                extractedTintIndex = 0;
            } else {
                Block mimicBlock = mimicState.getBlock();
                Direction targetFace;

                if (mimicBlock == Blocks.GRASS_BLOCK || mimicBlock == Blocks.PODZOL || mimicBlock == Blocks.MYCELIUM) {
                    targetFace = Direction.DOWN;
                } else {
                    targetFace = side != null ? side : Direction.NORTH;
                }

                RenderType targetLayer = renderType != null ? renderType : RenderType.solid();
                List<BakedQuad> mimicQuads = mimicModel.getQuads(mimicState, targetFace, rand, ModelData.EMPTY, targetLayer);

                if (mimicQuads.isEmpty()) {
                    for (Direction dir : Direction.values()) {
                        if (dir == targetFace) continue;
                        mimicQuads = mimicModel.getQuads(mimicState, dir, rand, ModelData.EMPTY, targetLayer);
                        if (!mimicQuads.isEmpty()) break;
                    }
                }

                if (mimicQuads.isEmpty()) {
                    for (RenderType type : RenderType.chunkBufferLayers()) {
                        mimicQuads = mimicModel.getQuads(mimicState, targetFace, rand, ModelData.EMPTY, type);
                        if (!mimicQuads.isEmpty()) break;
                    }
                }

                if (!mimicQuads.isEmpty()) {
                    for (BakedQuad q : mimicQuads) {
                        if (q.getSprite() != null && !q.getSprite().contents().name().getPath().contains("missingno")) {
                            mimicSprite = q.getSprite();
                            extractedTintIndex = q.getTintIndex();
                            break;
                        }
                    }
                }
            }

            if (mimicSprite == null) {
                mimicSprite = mimicModel.getParticleIcon(ModelData.EMPTY);
            }
        } catch (Exception e) {
            return doorQuads;
        }

        if (mimicSprite == null || mimicSprite.contents().name().getPath().contains("missingno")) {
            return doorQuads;
        }

        List<BakedQuad> retexturedQuads = new ArrayList<>();
        for (BakedQuad doorQuad : doorQuads) {
            TextureAtlasSprite originalSprite = doorQuad.getSprite();
            if (originalSprite == null) {
                retexturedQuads.add(doorQuad);
                continue;
            }

            int[] vertices = doorQuad.getVertices().clone();
            Direction quadDirection = doorQuad.getDirection();

            for (int i = 0; i < 4; i++) {
                int offset = i * 8;

                float x = Float.intBitsToFloat(vertices[offset]);
                float y = Float.intBitsToFloat(vertices[offset + 1]);
                float z = Float.intBitsToFloat(vertices[offset + 2]);

                float relativeU = 0.0F;
                float relativeV = 0.0F;

                if (quadDirection == Direction.UP || quadDirection == Direction.DOWN) {
                    relativeU = x;
                    relativeV = z;
                } else if (quadDirection == Direction.NORTH) {
                    relativeU = 1.0F - x;
                    relativeV = 1.0F - y;
                } else if (quadDirection == Direction.SOUTH) {
                    relativeU = x;
                    relativeV = 1.0F - y;
                } else if (quadDirection == Direction.WEST) {
                    relativeU = z;
                    relativeV = 1.0F - y;
                } else if (quadDirection == Direction.EAST) {
                    relativeU = 1.0F - z;
                    relativeV = 1.0F - y;
                }

                relativeU = Math.max(0.0F, Math.min(1.0F, relativeU));
                relativeV = Math.max(0.0F, Math.min(1.0F, relativeV));

                vertices[offset + 4] = Float.floatToRawIntBits(mimicSprite.getU(relativeU));
                vertices[offset + 5] = Float.floatToRawIntBits(mimicSprite.getV(relativeV));
            }

            retexturedQuads.add(new BakedQuad(
                    vertices,
                    extractedTintIndex,
                    doorQuad.getDirection(),
                    mimicSprite,
                    doorQuad.isShade()
            ));
        }

        return retexturedQuads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.originalModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.originalModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return this.originalModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return this.originalModel.isCustomRenderer();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return this.originalModel.getParticleIcon();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon(@NotNull ModelData modelData) {
        BakedModel mimicModel = getMimicModel(modelData);
        if (mimicModel != null) {
            return mimicModel.getParticleIcon(ModelData.EMPTY);
        }
        return this.originalModel.getParticleIcon(modelData);
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return this.originalModel.getOverrides();
    }
}