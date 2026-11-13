package fr.stylobow.iyc.recipe;

import fr.stylobow.iyc.ImagineYourCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ImagineYourCraft.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<TippedBoltRecipe>> TIPPED_BOLT_SERIALIZER =
            RECIPE_SERIALIZERS.register("tipped_bolt_crafting",
                    () -> new SimpleCraftingRecipeSerializer<>(TippedBoltRecipe::new));

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
