package fr.stylobow.iyc.item;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.block.ModBlocks;
import fr.stylobow.iyc.item.custom.*;
import fr.stylobow.iyc.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ImagineYourCraft.MOD_ID);

    public static final DeferredItem<Item> HNS_ITEM = ITEMS.register("hns_item",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> KEY = ITEMS.register("key",
            () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> PIECE = ITEMS.register("piece",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> POKEBALL = ITEMS.register("pokeball",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> ROCK = ITEMS.register("rock",
            () -> new SnowballItem(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> TOURNEVIS = ITEMS.register("tournevis",
            () -> new TournevisItem(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> L118A = ITEMS.register("l118a",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> SW1911 = ITEMS.register("sw1911",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));

    public static final DeferredItem<Item> BAGUETTE_BLEUE = ITEMS.register("baguette_bleue",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_JAUNE = ITEMS.register("baguette_jaune",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_ORANGE = ITEMS.register("baguette_orange",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_ROUGE = ITEMS.register("baguette_rouge",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_VERTE = ITEMS.register("baguette_verte",
            () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
    public static final DeferredItem<Item> BAGUETTE_VIOLETTE = ITEMS.register("baguette_violette",
            () -> new BaguetteVioletteItem(new Item.Properties().stacksTo(1).durability(2000)));

    public static final DeferredItem<Item> OBSIDIAN_INGOT = ITEMS.register("obsidian_ingot",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> TOPAZE = ITEMS.register("topaze",
            () -> new ItemFoil(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> RUBIS = ITEMS.register("rubis",
            () -> new ItemFoil(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> SAPHIR = ITEMS.register("saphir",
            () -> new ItemFoil(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> ADAMANTIUM_FRAGMENT = ITEMS.register("adamantium_fragment",
            () -> new ItemFoil(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> STEEL_STICK = ITEMS.register("steel_stick",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHOPPE = ITEMS.register("choppe",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MAILLE = ITEMS.register("maille",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LIGNITE = ITEMS.register("lignite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HOPS_SEEDS = ITEMS.register("hops_seeds",
            () -> new ItemNameBlockItem(ModBlocks.HOPS_CROP.get(), new Item.Properties()));
    public static final DeferredItem<Item> HOPS = ITEMS.register("hops",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STONE_STICK = ITEMS.register("stone_stick",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CAFEINE = ITEMS.register("cafeine",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COLA = ITEMS.register("cola",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CANETTE = ITEMS.register("canette",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TAURINE = ITEMS.register("taurine",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EMPTY_GLASS = ITEMS.register("empty_glass",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SLATE_ITEM = ITEMS.register("slate_item",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RECORD_TAKE_BACK_NIGHT = ITEMS.register("record_take_back_night",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.TAKE_BACK_NIGHT_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_ZOMBIES = ITEMS.register("record_zombies",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.ZOMBIES_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_CHUNKS = ITEMS.register("record_chunks",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.CHUNKS_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_CREEP = ITEMS.register("record_creep",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.CREEP_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_NOFRAGAYS = ITEMS.register("record_nofragays",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.NOFRAGAYS_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_SEAN_KEVIN = ITEMS.register("record_sean_kevin",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.SEAN_KEVIN_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_GIVE = ITEMS.register("record_give",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.GIVE_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_PELLES = ITEMS.register("record_pelles",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.PELLES_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_GEEK = ITEMS.register("record_geek",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.GEEK_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_ACTA = ITEMS.register("record_acta",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.ACTA_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_TOUCH = ITEMS.register("record_touch",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.TOUCH_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_MC_STYLE = ITEMS.register("record_mc_style",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.MC_STYLE_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_BOUGE = ITEMS.register("record_bouge",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.BOUGE_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_SFR = ITEMS.register("record_sfr",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.SFR_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_REVENGE = ITEMS.register("record_revenge",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.REVENGE_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_FALLEN_KINGDOM = ITEMS.register("record_fallen_kingdom",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.FALLEN_KINGDOM_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_TNT = ITEMS.register("record_tnt",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.TNT_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_GAGAOUTAI = ITEMS.register("record_gagaoutai",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.GAGAOUTAI_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_THE_MINER = ITEMS.register("record_the_miner",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.THE_MINER_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_DONT_MINE_AT_NIGHT = ITEMS.register("record_dont_mine_at_night",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.DONT_MINE_AT_NIGHT_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_MAUVAISE = ITEMS.register("record_mauvaise",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.MAUVAISE_KEY).stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> RECORD_GIRL_CRAFTY = ITEMS.register("record_girl_crafty",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.GIRL_CRAFTY_KEY).stacksTo(1).rarity(Rarity.RARE)));


    public static final DeferredItem<Item> SAPHIR_SWORD = ITEMS.register("saphir_sword",
            () -> new SwordItem(ModTiers.SAPHIR, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.SAPHIR, 3, -2.4F))));
    public static final DeferredItem<Item> SAPHIR_PICKAXE = ITEMS.register("saphir_pickaxe",
            () -> new PickaxeItem(ModTiers.SAPHIR, new Item.Properties().attributes(PickaxeItem.createAttributes(ModTiers.SAPHIR, 1, -2.8F))));
    public static final DeferredItem<Item> SAPHIR_AXE = ITEMS.register("saphir_axe",
            () -> new AxeItem(ModTiers.SAPHIR, new Item.Properties().attributes(AxeItem.createAttributes(ModTiers.SAPHIR, 5, -3.0F))));
    public static final DeferredItem<Item> SAPHIR_SHOVEL = ITEMS.register("saphir_shovel",
            () -> new ShovelItem(ModTiers.SAPHIR, new Item.Properties().attributes(ShovelItem.createAttributes(ModTiers.SAPHIR, 1.5F, -3.0F))));
    public static final DeferredItem<Item> SAPHIR_HOE = ITEMS.register("saphir_hoe",
            () -> new HoeItem(ModTiers.SAPHIR, new Item.Properties().attributes(HoeItem.createAttributes(ModTiers.SAPHIR, -3, -1.0F))));
    public static final DeferredItem<Item> SAPHIR_HELMET = ITEMS.register("saphir_helmet", () -> new SaphirArmorItem(ModArmorMaterials.SAPHIR, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(34))));
    public static final DeferredItem<Item> SAPHIR_CHESTPLATE = ITEMS.register("saphir_chestplate", () -> new SaphirArmorItem(ModArmorMaterials.SAPHIR, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(34))));
    public static final DeferredItem<Item> SAPHIR_LEGGINGS = ITEMS.register("saphir_leggings", () -> new SaphirArmorItem(ModArmorMaterials.SAPHIR, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(34))));
    public static final DeferredItem<Item> SAPHIR_BOOTS = ITEMS.register("saphir_boots", () -> new SaphirArmorItem(ModArmorMaterials.SAPHIR, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(34))));

    public static final DeferredItem<Item> RUBIS_SWORD = ITEMS.register("rubis_sword",
            () -> new SwordItem(ModTiers.RUBIS, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.RUBIS, 3, -2.4F))));
    public static final DeferredItem<Item> RUBIS_PICKAXE = ITEMS.register("rubis_pickaxe",
            () -> new PickaxeItem(ModTiers.RUBIS, new Item.Properties().attributes(PickaxeItem.createAttributes(ModTiers.RUBIS, 1, -2.8F))));
    public static final DeferredItem<Item> RUBIS_AXE = ITEMS.register("rubis_axe",
            () -> new AxeItem(ModTiers.RUBIS, new Item.Properties().attributes(AxeItem.createAttributes(ModTiers.RUBIS, 5, -3.0F))));
    public static final DeferredItem<Item> RUBIS_SHOVEL = ITEMS.register("rubis_shovel",
            () -> new ShovelItem(ModTiers.RUBIS, new Item.Properties().attributes(ShovelItem.createAttributes(ModTiers.RUBIS, 1.5F, -3.0F))));
    public static final DeferredItem<Item> RUBIS_HOE = ITEMS.register("rubis_hoe",
            () -> new HoeItem(ModTiers.RUBIS, new Item.Properties().attributes(HoeItem.createAttributes(ModTiers.RUBIS, -3, -1.0F))));
    public static final DeferredItem<Item> RUBIS_HELMET = ITEMS.register("rubis_helmet", () -> new RubisArmorItem(ModArmorMaterials.RUBIS, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(35))));
    public static final DeferredItem<Item> RUBIS_CHESTPLATE = ITEMS.register("rubis_chestplate", () -> new RubisArmorItem(ModArmorMaterials.RUBIS, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(35))));
    public static final DeferredItem<Item> RUBIS_LEGGINGS = ITEMS.register("rubis_leggings", () -> new RubisArmorItem(ModArmorMaterials.RUBIS, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(35))));
    public static final DeferredItem<Item> RUBIS_BOOTS = ITEMS.register("rubis_boots", () -> new RubisArmorItem(ModArmorMaterials.RUBIS, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(35))));

    public static final DeferredItem<Item> OBSIDIAN_SWORD = ITEMS.register("obsidian_sword",
            () -> new SwordItem(ModTiers.OBSIDIAN, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.OBSIDIAN, 3, -2.4F))));
    public static final DeferredItem<Item> OBSIDIAN_PICKAXE = ITEMS.register("obsidian_pickaxe",
            () -> new PickaxeItem(ModTiers.OBSIDIAN, new Item.Properties().attributes(PickaxeItem.createAttributes(ModTiers.OBSIDIAN, 1, -2.8F))));
    public static final DeferredItem<Item> OBSIDIAN_AXE = ITEMS.register("obsidian_axe",
            () -> new AxeItem(ModTiers.OBSIDIAN, new Item.Properties().attributes(AxeItem.createAttributes(ModTiers.OBSIDIAN, 5, -3.0F))));
    public static final DeferredItem<Item> OBSIDIAN_SHOVEL = ITEMS.register("obsidian_shovel",
            () -> new ShovelItem(ModTiers.OBSIDIAN, new Item.Properties().attributes(ShovelItem.createAttributes(ModTiers.OBSIDIAN, 1.5F, -3.0F))));
    public static final DeferredItem<Item> OBSIDIAN_HOE = ITEMS.register("obsidian_hoe",
            () -> new HoeItem(ModTiers.OBSIDIAN, new Item.Properties().attributes(HoeItem.createAttributes(ModTiers.OBSIDIAN, -3, -1.0F))));
    public static final DeferredItem<Item> OBSIDIAN_HELMET = ITEMS.register("obsidian_helmet",
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(36))));
    public static final DeferredItem<Item> OBSIDIAN_CHESTPLATE = ITEMS.register("obsidian_chestplate",
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(36))));
    public static final DeferredItem<Item> OBSIDIAN_LEGGINGS = ITEMS.register("obsidian_leggings",
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(36))));
    public static final DeferredItem<Item> OBSIDIAN_BOOTS = ITEMS.register("obsidian_boots",
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(36))));

    public static final DeferredItem<Item> TOPAZE_SWORD = ITEMS.register("topaze_sword",
            () -> new SwordItem(ModTiers.TOPAZE, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.TOPAZE, 3, -2.4F))));
    public static final DeferredItem<Item> TOPAZE_PICKAXE = ITEMS.register("topaze_pickaxe",
            () -> new PickaxeItem(ModTiers.TOPAZE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModTiers.TOPAZE, 1, -2.8F))));
    public static final DeferredItem<Item> TOPAZE_AXE = ITEMS.register("topaze_axe",
            () -> new AxeItem(ModTiers.TOPAZE, new Item.Properties().attributes(AxeItem.createAttributes(ModTiers.TOPAZE, 5, -3.0F))));
    public static final DeferredItem<Item> TOPAZE_SHOVEL = ITEMS.register("topaze_shovel",
            () -> new ShovelItem(ModTiers.TOPAZE, new Item.Properties().attributes(ShovelItem.createAttributes(ModTiers.TOPAZE, 1.5F, -3.0F))));
    public static final DeferredItem<Item> TOPAZE_HOE = ITEMS.register("topaze_hoe",
            () -> new HoeItem(ModTiers.TOPAZE, new Item.Properties().attributes(HoeItem.createAttributes(ModTiers.TOPAZE, -3, 0.0F))));
    public static final DeferredItem<Item> TOPAZE_HELMET = ITEMS.register("topaze_helmet", () -> new TopazeArmorItem(ModArmorMaterials.TOPAZE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(36))));
    public static final DeferredItem<Item> TOPAZE_CHESTPLATE = ITEMS.register("topaze_chestplate", () -> new TopazeArmorItem(ModArmorMaterials.TOPAZE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(36))));
    public static final DeferredItem<Item> TOPAZE_LEGGINGS = ITEMS.register("topaze_leggings", () -> new TopazeArmorItem(ModArmorMaterials.TOPAZE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(36))));
    public static final DeferredItem<Item> TOPAZE_BOOTS = ITEMS.register("topaze_boots", () -> new TopazeArmorItem(ModArmorMaterials.TOPAZE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(36))));

    public static final DeferredItem<Item> ADAMANTIUM_SWORD = ITEMS.register("adamantium_sword",
            () -> new SwordItem(ModTiers.ADAMANTIUM, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.ADAMANTIUM, 3, -2.4F))));
    public static final DeferredItem<Item> ADAMANTIUM_PICKAXE = ITEMS.register("adamantium_pickaxe",
            () -> new PickaxeItem(ModTiers.ADAMANTIUM, new Item.Properties().attributes(PickaxeItem.createAttributes(ModTiers.ADAMANTIUM, 1, -2.8F))));
    public static final DeferredItem<Item> ADAMANTIUM_AXE = ITEMS.register("adamantium_axe",
            () -> new AxeItem(ModTiers.ADAMANTIUM, new Item.Properties().attributes(AxeItem.createAttributes(ModTiers.ADAMANTIUM, 5, -3.0F))));
    public static final DeferredItem<Item> ADAMANTIUM_SHOVEL = ITEMS.register("adamantium_shovel",
            () -> new ShovelItem(ModTiers.ADAMANTIUM, new Item.Properties().attributes(ShovelItem.createAttributes(ModTiers.ADAMANTIUM, 1.5F, -3.0F))));
    public static final DeferredItem<Item> ADAMANTIUM_HOE = ITEMS.register("adamantium_hoe",
            () -> new HoeItem(ModTiers.ADAMANTIUM, new Item.Properties().attributes(HoeItem.createAttributes(ModTiers.ADAMANTIUM, -4, 0.0F))));
    public static final DeferredItem<Item> ADAMANTIUM_HELMET = ITEMS.register("adamantium_helmet", () -> new AdamantiumArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(40))));
    public static final DeferredItem<Item> ADAMANTIUM_CHESTPLATE = ITEMS.register("adamantium_chestplate", () -> new AdamantiumArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(40))));
    public static final DeferredItem<Item> ADAMANTIUM_LEGGINGS = ITEMS.register("adamantium_leggings", () -> new AdamantiumArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(40))));
    public static final DeferredItem<Item> ADAMANTIUM_BOOTS = ITEMS.register("adamantium_boots", () -> new AdamantiumArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(40))));

    public static final DeferredItem<Item> EMERALD_SWORD = ITEMS.register("emerald_sword",
            () -> new SwordItem(ModTiers.EMERALD, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.EMERALD, 3, -2.4F))));
    public static final DeferredItem<Item> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe",
            () -> new PickaxeItem(ModTiers.EMERALD, new Item.Properties().attributes(PickaxeItem.createAttributes(ModTiers.EMERALD, 1, -2.8F))));
    public static final DeferredItem<Item> EMERALD_AXE = ITEMS.register("emerald_axe",
            () -> new AxeItem(ModTiers.EMERALD, new Item.Properties().attributes(AxeItem.createAttributes(ModTiers.EMERALD, 5, -3.0F))));
    public static final DeferredItem<Item> EMERALD_SHOVEL = ITEMS.register("emerald_shovel",
            () -> new ShovelItem(ModTiers.EMERALD, new Item.Properties().attributes(ShovelItem.createAttributes(ModTiers.EMERALD, 1.5F, -3.0F))));
    public static final DeferredItem<Item> EMERALD_HOE = ITEMS.register("emerald_hoe",
            () -> new HoeItem(ModTiers.EMERALD, new Item.Properties().attributes(HoeItem.createAttributes(ModTiers.EMERALD, -3, -1.0F))));
    public static final DeferredItem<Item> EMERALD_HELMET = ITEMS.register("emerald_helmet",
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(24))));
    public static final DeferredItem<Item> EMERALD_CHESTPLATE = ITEMS.register("emerald_chestplate",
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(24))));
    public static final DeferredItem<Item> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings",
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(24))));
    public static final DeferredItem<Item> EMERALD_BOOTS = ITEMS.register("emerald_boots",
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(24))));

    public static final DeferredItem<Item> CROSSBOW = ITEMS.register("crossbow",
            () -> new IYCCrossbowItem(new Item.Properties().stacksTo(1).durability(500)));
    public static final DeferredItem<Item> BOLT = ITEMS.register("bolt",
            () -> new ArrowItem(new Item.Properties()));

    public static final DeferredItem<Item> REDBULL = ITEMS.register("redbull",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationModifier(0.1f)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 0), 1.0f)
                    .usingConvertsTo(ModItems.CANETTE)
                    .alwaysEdible()
                    .build())) {

                @Override
                public UseAnim getUseAnimation(ItemStack stack) {
                    return UseAnim.DRINK;
                }

                @Override
                public SoundEvent getDrinkingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }

                @Override
                public SoundEvent getEatingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }
            });

    public static final DeferredItem<Item> COCA = ITEMS.register("coca",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder()
                    .nutrition(6)
                    .saturationModifier(0f)
                    .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 0), 1.0f)
                    .usingConvertsTo(ModItems.CANETTE)
                    .alwaysEdible()
                    .build())) {

                @Override
                public UseAnim getUseAnimation(ItemStack stack) {
                    return UseAnim.DRINK;
                }

                @Override
                public SoundEvent getDrinkingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }

                @Override
                public SoundEvent getEatingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }
            });

    public static final DeferredItem<Item> CHOCOLAT_CHAUD = ITEMS.register("chocolat_chaud",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder()
                    .nutrition(6)
                    .saturationModifier(0f)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1.0f)
                    .usingConvertsTo(ModItems.CANETTE)
                    .alwaysEdible()
                    .build())) {

                @Override
                public UseAnim getUseAnimation(ItemStack stack) {
                    return UseAnim.DRINK;
                }

                @Override
                public SoundEvent getDrinkingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }

                @Override
                public SoundEvent getEatingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }
            });

    public static final DeferredItem<Item> BIERE = ITEMS.register("biere",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder()
                    .nutrition(6)
                    .saturationModifier(0f)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 1.0f)
                    .usingConvertsTo(ModItems.CHOPPE)
                    .alwaysEdible()
                    .build())) {

                @Override
                public UseAnim getUseAnimation(ItemStack stack) {
                    return UseAnim.DRINK;
                }

                @Override
                public SoundEvent getDrinkingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }

                @Override
                public SoundEvent getEatingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }
            });

    public static final DeferredItem<Item> TARTINE = ITEMS.register("tartine",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
                    .build())));

    public static final DeferredItem<Item> TARTINE_NUTELLA = ITEMS.register("tartine_nutella",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(14)
                    .saturationModifier(0f)
                    .build())));

    public static final DeferredItem<Item> NUTELLA = ITEMS.register("nutella",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(10)
                    .saturationModifier(0f)
                    .build())));

    public static final DeferredItem<Item> APPLE_JUICE = ITEMS.register("apple_juice",
            () -> new Item(new Item.Properties().stacksTo(1).food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0f)
                    .effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 0), 1.0f)
                    .usingConvertsTo(ModItems.EMPTY_GLASS)
                    .alwaysEdible()
                    .build())) {

                @Override
                public UseAnim getUseAnimation(ItemStack stack) {
                    return UseAnim.DRINK;
                }

                @Override
                public SoundEvent getDrinkingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }

                @Override
                public SoundEvent getEatingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }
            });

    public static final DeferredItem<Item> CHERRY = ITEMS.register("cherry",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(0f)
                    .build())));

    public static final DeferredItem<Item> HIDDEN_DOOR_ITEM = ITEMS.register("hidden_door",
            () -> new DoubleHighBlockItem(ModBlocks.HIDDEN_DOOR.get(), new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
