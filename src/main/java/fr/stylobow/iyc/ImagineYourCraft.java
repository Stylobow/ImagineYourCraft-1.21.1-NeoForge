package fr.stylobow.iyc;

import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.block.ModBlocks;
import fr.stylobow.iyc.block.entity.ChairRenderer;
import fr.stylobow.iyc.block.entity.ModBlockEntities;
import fr.stylobow.iyc.block.entity.ModEntities;
import fr.stylobow.iyc.client.audio.RadioManager;
import fr.stylobow.iyc.client.config.IYCConfig;
import fr.stylobow.iyc.client.event.IronLadderHandler;
import fr.stylobow.iyc.config.ModConfigs;
import fr.stylobow.iyc.item.ModArmorMaterials;
import fr.stylobow.iyc.item.ModCreativeModeTabs;
import fr.stylobow.iyc.item.ModItems;
import fr.stylobow.iyc.loot.ModLootModifiers;
import fr.stylobow.iyc.network.SkinSyncPayload;
import fr.stylobow.iyc.network.SyncTardisStatusPacket;
import fr.stylobow.iyc.network.ToggleTardisPacket;
import fr.stylobow.iyc.screen.ModMenuTypes;
import fr.stylobow.iyc.sound.ModSounds;
import fr.stylobow.iyc.worldgen.feature.ModFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

@Mod(ImagineYourCraft.MOD_ID)
public class ImagineYourCraft {
    public static final String MOD_ID = "iyc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ImagineYourCraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::onConfigLoad);

        ModAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);
        EVENT_BUS.register(IronLadderHandler.class);

        ModMenuTypes.MENUS.register(modEventBus);

        ModEntities.ENTITIES.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, ModConfigs.SPEC, "iyc-common.toml");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FireBlock fireblock = (FireBlock) Blocks.FIRE;
            fireblock.setFlammable(ModBlocks.OAK_REVERSED_PLANKS_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_LOG_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_LOG.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_LEAVES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_LANTERN.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_LANTERN.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_LANTERN.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_LANTERN.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_LANTERN.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_LANTERN.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_LANTERN.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_LANTERN.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_LANTERN.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUMPBOX.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.LOG_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_REVERSED_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_REVERSED_PLANKS_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_LOG_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_LOG_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_LOG_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_LOG_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_LOG_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_LOG_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_LOG_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_LOG_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_BLOCK_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_STEM_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_STEM_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_PLANKS_WALL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_PLANKS_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_LOG_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_LOG_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_LOG_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_LOG_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_LOG_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_LOG_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_LOG_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_LOG_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_BLOCK_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_STEM_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_STEM_SLOPES.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.LOG_FENCE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_SHELF.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_HIGH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_STOOL.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_LITTLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_BENCH.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_CHAIR.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.OAK_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.SPRUCE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BIRCH_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JUNGLE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ACACIA_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DARK_OAK_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MANGROVE_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CHERRY_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BAMBOO_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRIMSON_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WARPED_TABLE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.LOG_FENCE_GATE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WHITE_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.WHITE_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.WHITE_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.ORANGE_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.ORANGE_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.ORANGE_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.MAGENTA_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.MAGENTA_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.MAGENTA_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.LIGHT_BLUE_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.LIGHT_BLUE_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.LIGHT_BLUE_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.YELLOW_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.YELLOW_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.YELLOW_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.LIME_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.LIME_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.LIME_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.PINK_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.PINK_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.PINK_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.GRAY_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.GRAY_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.GRAY_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.LIGHT_GRAY_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.LIGHT_GRAY_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.LIGHT_GRAY_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.CYAN_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.CYAN_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.CYAN_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.PURPLE_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.PURPLE_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.PURPLE_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BLUE_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BLUE_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BLUE_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BROWN_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BROWN_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BROWN_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.GREEN_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.GREEN_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.GREEN_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.RED_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.RED_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.RED_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BLACK_WOOL_SLAB.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BLACK_WOOL_STAIRS.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BLACK_CLOUD.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ORANGE_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MAGENTA_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.LIGHT_BLUE_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.YELLOW_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.LIME_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.PINK_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.GRAY_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.LIGHT_GRAY_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CYAN_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.PURPLE_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BLUE_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BROWN_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.GREEN_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.RED_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BLACK_ROD.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WHITE_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.ORANGE_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MAGENTA_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.LIGHT_BLUE_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.YELLOW_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.LIME_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.PINK_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.GRAY_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.LIGHT_GRAY_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CYAN_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.PURPLE_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BLUE_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BROWN_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.GREEN_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.RED_LAMP.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.BLACK_LAMP.get(), 5, 20);

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CHERRY_SAPLING.getId(), ModBlocks.POTTED_CHERRY_SAPLING);
        });
    }

    private void onConfigLoad(final ModConfigEvent event) {
        if (event.getConfig().getModId().equals(MOD_ID)) {
            LOGGER.info("[IYC] Configuration successfully loaded!");
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @EventBusSubscriber(modid = ImagineYourCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {

        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            IYCConfig.load();
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHERRY_SAPLING.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_CHERRY_SAPLING.get(), RenderType.cutout());
            });

            EVENT_BUS.addListener(ClientModEvents::onClientTick);
        }

        @SubscribeEvent
        static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.CHAIR_ENTITY.get(), ChairRenderer::new);
        }
        private static void onClientTick(ClientTickEvent.Post event) {
            RadioManager.tickIycRadio();
        }
    }

    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("iyc").versioned("1.0.0");

        registrar.playBidirectional(
                SkinSyncPayload.TYPE,
                SkinSyncPayload.CODEC,
                SkinSyncPayload::handle
        );

        registrar.playToServer(
                ToggleTardisPacket.TYPE,
                ToggleTardisPacket.STREAM_CODEC,
                ToggleTardisPacket::handle
        );

        registrar.playToClient(
                SyncTardisStatusPacket.TYPE,
                SyncTardisStatusPacket.STREAM_CODEC,
                SyncTardisStatusPacket::handle
        );
    }
}