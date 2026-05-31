package fr.stylobow.iyc.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinRenderManager {

    private static final Map<UUID, ResourceLocation> LOADED_SKINS = new HashMap<>();

    public static void registerPlayerSkin(UUID playerId, String url) {
        if (url == null || url.isEmpty()) return;

        ResourceLocation location = ResourceLocation.fromNamespaceAndPath("iyc", "skins/" + playerId);
        File cacheFile = new File(Minecraft.getInstance().gameDirectory, "assets/skins/" + playerId + ".png");

        HttpTexture texture = new HttpTexture(cacheFile, url,
                ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/player/wide/steve.png"),
                false, null);

        Minecraft.getInstance().getTextureManager().register(location, texture);
        LOADED_SKINS.put(playerId, location);
    }

    public static ResourceLocation getPlayerSkin(UUID playerId) {
        return LOADED_SKINS.get(playerId);
    }
}