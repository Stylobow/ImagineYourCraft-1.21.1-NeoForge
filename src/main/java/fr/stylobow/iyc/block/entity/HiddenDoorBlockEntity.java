package fr.stylobow.iyc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;

public class HiddenDoorBlockEntity extends BlockEntity {
    public static final ModelProperty<BlockState> MIMIC_PROPERTY = new ModelProperty<>();

    private BlockState mimickedBlock = Blocks.STONE.defaultBlockState();

    public HiddenDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HIDDEN_DOOR.get(), pos, state);
    }

    public BlockState getMimickedBlock() {
        return this.mimickedBlock;
    }

    public void setMimickedBlock(BlockState state) {
        this.mimickedBlock = state;
        this.setChanged();
        if (this.level != null) {
            if (!this.level.isClientSide) {
                this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            } else {
                this.requestModelDataUpdate();
                this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 8);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("MimickedBlock", NbtUtils.writeBlockState(this.mimickedBlock));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("MimickedBlock", 10)) {
            this.mimickedBlock = NbtUtils.readBlockState(registries.lookupOrThrow(Registries.BLOCK), tag.getCompound("MimickedBlock"));
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        super.onDataPacket(net, pkt, registries);
        if (pkt.getTag() != null) {
            this.loadAdditional(pkt.getTag(), registries);
        }
        if (this.level != null && this.level.isClientSide) {
            this.requestModelDataUpdate();
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 8);
        }
    }

    @Override
    public @NotNull ModelData getModelData() {
        BlockState stateToMimic = this.mimickedBlock;
        if (this.getBlockState().getValue(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
            BlockEntity belowBe = this.level.getBlockEntity(this.worldPosition.below());
            if (belowBe instanceof HiddenDoorBlockEntity hiddenDoorBE) {
                stateToMimic = hiddenDoorBE.getMimickedBlock();
            }
        }
        return ModelData.builder().with(HiddenDoorBlockEntity.MIMIC_PROPERTY, stateToMimic).build();
    }
}