package fr.stylobow.iyc.event;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.block.ModBlocks;
import fr.stylobow.iyc.block.entity.ModEntities;
import fr.stylobow.iyc.client.model.HatModel;
import fr.stylobow.iyc.client.renderer.layer.HatLayer;
import fr.stylobow.iyc.entity.BoltEntity;
import fr.stylobow.iyc.item.ModItems;
import fr.stylobow.iyc.item.custom.TournevisItem;
import fr.stylobow.iyc.overlay.KeystrokesOverlay;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = "iyc", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
                ResourceLocation.fromNamespaceAndPath("iyc", "keystrokes"),
                new KeystrokesOverlay()
        );
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HatModel.LAYER_LOCATION, HatModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skinType : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skinType);
            if (renderer != null) {
                renderer.addLayer(new HatLayer(renderer, event.getEntityModels()));
            }
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, seed) -> {
                if (entity == null) return 0.0F;
                return CrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(stack, entity);
            });

            ItemProperties.register(ModItems.CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pulling"), (stack, level, entity, seed) -> {
                return entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
            });

            ItemProperties.register(ModItems.CROSSBOW.get(), ResourceLocation.withDefaultNamespace("charged"), (stack, level, entity, seed) -> {
                return entity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
            });
        });
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.STEEL_BREWING_STAND.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BOLT.get(), context -> new ArrowRenderer<BoltEntity>(context) {
            @Override
            public ResourceLocation getTextureLocation(BoltEntity entity) {
                return ResourceLocation.fromNamespaceAndPath("iyc", "textures/entity/bolt.png");
            }
        });
    }

    @EventBusSubscriber(modid = ImagineYourCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public class ModEvents {

        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.containerMenu != serverPlayer.inventoryMenu) {
                    return;
                }

                ItemStack stack = serverPlayer.getMainHandItem();
                if (stack.getItem() instanceof TournevisItem tournevis) {
                    tournevis.performTeleport(serverPlayer, stack);
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
            if (event.getHand() == InteractionHand.MAIN_HAND && event.getEntity() instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.containerMenu != serverPlayer.inventoryMenu) {
                    return;
                }

                ItemStack stack = serverPlayer.getMainHandItem();
                if (stack.getItem() instanceof TournevisItem tournevis) {
                    tournevis.performTeleport(serverPlayer, stack);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerFall(net.neoforged.neoforge.event.entity.living.LivingFallEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (player.getTags().contains("iyc_fall_protected")) {
                    event.setCanceled(true);
                    player.removeTag("iyc_fall_protected");
                }
            }
        }
    }
}