package fr.stylobow.iyc.block.entity;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SteelBrewingStandBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private static final int INGREDIENT_SLOT = 3;
    private static final int FUEL_SLOT = 4;
    private static final int[] SLOTS_FOR_UP = new int[]{3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{0, 1, 2, 3};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0, 1, 2, 4};
    public static final int FUEL_USES = 20;
    public static final int DATA_BREW_TIME = 0;
    public static final int DATA_FUEL_USES = 1;
    public static final int NUM_DATA_VALUES = 2;

    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    int brewTime;
    private boolean[] lastPotionCount;
    private Item ingredient;
    int fuel;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SteelBrewingStandBlockEntity.this.brewTime;
                case 1 -> SteelBrewingStandBlockEntity.this.fuel;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    SteelBrewingStandBlockEntity.this.brewTime = value;
                    break;
                case 1:
                    SteelBrewingStandBlockEntity.this.fuel = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public SteelBrewingStandBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEEL_BREWING_STAND_BE.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.iyc.steel_brewing_stand");
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    public static void customServerTick(Level level, BlockPos pos, BlockState state, SteelBrewingStandBlockEntity entity) {
        tickBrewing(level, pos, state, entity);

        if (entity.dataAccess.get(1) > 0 || entity.dataAccess.get(0) > 0) {
            tickBrewing(level, pos, state, entity);
        }
    }

    private static void tickBrewing(Level level, BlockPos pos, BlockState state, SteelBrewingStandBlockEntity blockEntity) {
        ItemStack fuelStack = blockEntity.items.get(FUEL_SLOT);
        if (blockEntity.fuel <= 0 && fuelStack.is(Items.BLAZE_POWDER)) {
            blockEntity.fuel = FUEL_USES;
            fuelStack.shrink(1);
            setChanged(level, pos, state);
        }

        boolean flag = isBrewable(level.potionBrewing(), blockEntity.items);
        boolean flag1 = blockEntity.brewTime > 0;
        ItemStack ingredientStack = blockEntity.items.get(INGREDIENT_SLOT);
        if (flag1) {
            blockEntity.brewTime--;
            boolean flag2 = blockEntity.brewTime == 0;
            if (flag2 && flag) {
                doBrew(level, pos, blockEntity.items);
            } else if (!flag || !ingredientStack.is(blockEntity.ingredient)) {
                blockEntity.brewTime = 0;
            }

            setChanged(level, pos, state);
        } else if (flag && blockEntity.fuel > 0) {
            blockEntity.fuel--;
            blockEntity.brewTime = 400;
            blockEntity.ingredient = ingredientStack.getItem();
            setChanged(level, pos, state);
        }

        boolean[] potionBits = blockEntity.getPotionBits();
        if (!Arrays.equals(potionBits, blockEntity.lastPotionCount)) {
            blockEntity.lastPotionCount = potionBits;
            BlockState blockstate = state;
            if (!(state.getBlock() instanceof BrewingStandBlock)) {
                return;
            }

            for (int i = 0; i < BrewingStandBlock.HAS_BOTTLE.length; i++) {
                blockstate = blockstate.setValue(BrewingStandBlock.HAS_BOTTLE[i], Boolean.valueOf(potionBits[i]));
            }

            level.setBlock(pos, blockstate, 2);
        }
    }

    private boolean[] getPotionBits() {
        boolean[] bits = new boolean[3];
        for (int i = 0; i < 3; i++) {
            if (!this.items.get(i).isEmpty()) {
                bits[i] = true;
            }
        }
        return bits;
    }

    private static boolean isBrewable(PotionBrewing potionBrewing, NonNullList<ItemStack> items) {
        ItemStack ingredientStack = items.get(INGREDIENT_SLOT);
        if (ingredientStack.isEmpty()) {
            return false;
        } else if (!potionBrewing.isIngredient(ingredientStack)) {
            return false;
        } else {
            for (int i = 0; i < 3; i++) {
                ItemStack potionStack = items.get(i);
                if (!potionStack.isEmpty() && potionBrewing.hasMix(potionStack, ingredientStack)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static void doBrew(Level level, BlockPos pos, NonNullList<ItemStack> items) {
        if (net.neoforged.neoforge.event.EventHooks.onPotionAttemptBrew(items)) return;
        ItemStack ingredientStack = items.get(INGREDIENT_SLOT);
        PotionBrewing potionBrewing = level.potionBrewing();

        for (int i = 0; i < 3; i++) {
            items.set(i, potionBrewing.mix(ingredientStack, items.get(i)));
        }

        net.neoforged.neoforge.event.EventHooks.onPotionBrewed(items);
        if (ingredientStack.hasCraftingRemainingItem()) {
            ItemStack remainingItem = ingredientStack.getCraftingRemainingItem();
            ingredientStack.shrink(1);
            if (ingredientStack.isEmpty()) {
                ingredientStack = remainingItem;
            } else {
                Containers.dropItemStack(level, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), remainingItem);
            }
        } else {
            ingredientStack.shrink(1);
        }

        items.set(INGREDIENT_SLOT, ingredientStack);
        level.levelEvent(1035, pos, 0);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);
        this.brewTime = tag.getShort("BrewTime");
        if (this.brewTime > 0) {
            this.ingredient = this.items.get(INGREDIENT_SLOT).getItem();
        }
        this.fuel = tag.getByte("Fuel");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putShort("BrewTime", (short)this.brewTime);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        tag.putByte("Fuel", (byte)this.fuel);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        PotionBrewing potionBrewing = this.level != null ? this.level.potionBrewing() : PotionBrewing.EMPTY;
        if (index == INGREDIENT_SLOT) {
            return potionBrewing.isIngredient(stack);
        } else if (index == FUEL_SLOT) {
            return stack.is(Items.BLAZE_POWDER);
        } else {
            return (potionBrewing.isInput(stack) || stack.is(Items.GLASS_BOTTLE)) && this.getItem(index).isEmpty();
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.UP) {
            return SLOTS_FOR_UP;
        } else {
            return side == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == INGREDIENT_SLOT ? stack.is(Items.GLASS_BOTTLE) : true;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new BrewingStandMenu(id, player, this, this.dataAccess);
    }
}