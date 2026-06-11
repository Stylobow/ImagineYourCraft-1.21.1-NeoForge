package fr.stylobow.iyc.item;

import fr.stylobow.iyc.ImagineYourCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, ImagineYourCraft.MOD_ID);

    public static final Holder<ArmorMaterial> SAPHIR = register("saphir",
            buildDefense(3, 6, 8, 3), 24, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.2F, 0.0F, () -> Ingredient.of(ModItems.SAPHIR.get()));

    public static final Holder<ArmorMaterial> RUBIS = register("rubis",
            buildDefense(3, 6, 8, 3), 22, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5F, 0.0F, () -> Ingredient.of(ModItems.RUBIS.get()));

    public static final Holder<ArmorMaterial> OBSIDIAN = register("obsidian",
            buildDefense(3, 6, 8, 3), 21, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.65F, 0.02F, () -> Ingredient.of(ModItems.OBSIDIAN_INGOT.get()));

    public static final Holder<ArmorMaterial> TOPAZE = register("topaze",
            buildDefense(3, 6, 8, 3), 20, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.8F, 0.05F, () -> Ingredient.of(ModItems.TOPAZE.get()));
    public static final Holder<ArmorMaterial> ADAMANTIUM = register("adamantium",
            buildDefense(4, 6, 9, 4), 18, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5F, 0.2F, () -> Ingredient.of(ModItems.ADAMANTIUM_FRAGMENT.get()));
    public static final Holder<ArmorMaterial> EMERALD = register("emerald",
            buildDefense(2, 6, 7, 3), 18, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0F, 0.0F, () -> Ingredient.of(Items.EMERALD));

    private static EnumMap<ArmorItem.Type, Integer> buildDefense(int boots, int leggings, int chestplate, int helmet) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.BOOTS, boots);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.CHESTPLATE, chestplate);
        map.put(ArmorItem.Type.HELMET, helmet);
        return map;
    }

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ImagineYourCraft.MOD_ID, name)));
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(defense, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance));
    }
}