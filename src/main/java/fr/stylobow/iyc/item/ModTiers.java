package fr.stylobow.iyc.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum ModTiers implements Tier {

    STEEL(
            750,
            7.0F,
            2.5F,
            12,
            () -> Ingredient.of(ModItems.STEEL_INGOT.get())
    ),

    SAPHIR(
            1650,        // Durability
            8.3F,              // Minin Speed
            3.2F,              // Damage Bonus
            24,                // Enchantability
            () -> Ingredient.of(ModItems.SAPHIR.get())
    ),

    RUBIS(
            1750,
            8.6F,
            3.5F,
            22,
            () -> Ingredient.of(ModItems.RUBIS.get())
    ),

    OBSIDIAN(
            1825,
            8.75F,
            3.65F,
            21,
            () -> Ingredient.of(ModItems.OBSIDIAN_INGOT.get())
    ),

    TOPAZE(
            1900,
            8.9F,
            3.8F,
            20,
            () -> Ingredient.of(ModItems.TOPAZE.get())
    ),

    ADAMANTIUM(
            2200,
            9.5F,
            4.5F,
            18,
            () -> Ingredient.of(ModItems.ADAMANTIUM_FRAGMENT.get())
    ),

    EMERALD(
            850,
                    7.0F,
                    2.5F,
                    18,
                    () -> Ingredient.of(Items.EMERALD)
    );

    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModTiers(int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override public int getUses() { return this.uses; }
    @Override public float getSpeed() { return this.speed; }
    @Override public float getAttackDamageBonus() { return this.damage; }
    @Override public int getEnchantmentValue() { return this.enchantmentValue; }
    @Override public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }

    @Override public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
    }
}