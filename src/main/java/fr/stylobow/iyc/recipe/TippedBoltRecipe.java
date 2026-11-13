package fr.stylobow.iyc.recipe;

import fr.stylobow.iyc.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class TippedBoltRecipe extends CustomRecipe {

    public TippedBoltRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() == 3 && input.height() == 3) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    ItemStack stack = input.getItem(col, row);
                    if (stack.isEmpty()) return false;
                    if (col == 1 && row == 1) {
                        if (!stack.is(Items.LINGERING_POTION)) return false;
                    } else {
                        if (!stack.is(ModItems.BOLT.get())) return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack potion = input.getItem(1, 1);
        if (!potion.is(Items.LINGERING_POTION)) return ItemStack.EMPTY;

        ItemStack result = new ItemStack(ModItems.TIPPED_BOLT.get(), 8);
        result.set(DataComponents.POTION_CONTENTS, potion.get(DataComponents.POTION_CONTENTS));
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.TIPPED_BOLT_SERIALIZER.get();
    }
}
