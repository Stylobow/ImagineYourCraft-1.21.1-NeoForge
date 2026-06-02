package fr.stylobow.iyc.client;

import fr.stylobow.iyc.ImagineYourCraft;
import fr.stylobow.iyc.block.entity.ModBlockEntities;
import fr.stylobow.iyc.block.entity.ModEntities;
import fr.stylobow.iyc.client.model.HiddenDoorBakedModel;
import fr.stylobow.iyc.client.renderer.BombeRenderer;
import fr.stylobow.iyc.client.renderer.FloatingItemLayer;
import fr.stylobow.iyc.screen.BarrelScreen;
import fr.stylobow.iyc.screen.ModMenuTypes;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = ImagineYourCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BOMBE.get(), BombeRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {

//        for (PlayerSkin.Model skinName : event.getSkins()) {
//            var renderer = event.getSkin(skinName);
//
//        }
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> wideRenderer = event.getSkin(PlayerSkin.Model.WIDE);
        if (wideRenderer != null) {
            wideRenderer.addLayer(new FloatingItemLayer(wideRenderer));
        }

        LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> slimRenderer = event.getSkin(PlayerSkin.Model.SLIM);
        if (slimRenderer != null) {
            slimRenderer.addLayer(new FloatingItemLayer(slimRenderer));
        }
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BARREL_MENU.get(), BarrelScreen::new);
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        Map<ModelResourceLocation, BakedModel> registry = event.getModels();
        List<ModelResourceLocation> keysToReplace = new java.util.ArrayList<>();

        for (ModelResourceLocation modelLoc : registry.keySet()) {
            if (modelLoc.id().getNamespace().equals("iyc") && modelLoc.id().getPath().contains("hidden_door")) {
                keysToReplace.add(modelLoc);
            }
        }

        for (ModelResourceLocation loc : keysToReplace) {
            BakedModel existingModel = registry.get(loc);
            if (existingModel != null) {
                registry.put(loc, new HiddenDoorBakedModel(existingModel));
            }
        }
    }
}