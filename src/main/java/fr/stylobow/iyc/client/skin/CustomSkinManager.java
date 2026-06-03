package fr.stylobow.iyc.client.skin;

import com.mojang.blaze3d.platform.NativeImage;
import fr.stylobow.iyc.client.config.IYCConfig;
import fr.stylobow.iyc.network.SkinSyncPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@EventBusSubscriber(modid = "iyc", bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class CustomSkinManager {

    private static final Map<UUID, ResourceLocation> playerSkins = new HashMap<>();
    private static final Map<UUID, ResourceLocation> playerCapes = new HashMap<>();
    private static final Map<UUID, ResourceLocation> playerHats = new HashMap<>();

    private static String loadedSkinPath = "";
    private static String loadedCapePath = "";
    private static String loadedHatPath = "";

    @SubscribeEvent
    public static void onPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (Exception ignored) {}

            Minecraft.getInstance().execute(() -> {
                loadedSkinPath = "";
                loadedCapePath = "";
                loadedHatPath = "";
                applySkin();
                applyCape();
                applyHat();
            });
        }).start();
    }

    private static byte[] compress(byte[] data) {
        if (data == null || data.length == 0) return data;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
            gzip.write(data);
            gzip.finish();
            return bos.toByteArray();
        } catch (IOException e) {
            return data;
        }
    }

    private static byte[] decompress(byte[] data) {
        if (data == null || data.length == 0) return data;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             GZIPInputStream gzip = new GZIPInputStream(bis)) {
            return gzip.readAllBytes();
        } catch (IOException e) {
            return data;
        }
    }

    private static NativeImage convertLegacySkin(NativeImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        if (width == height || width == height * 2) {
            int baseSize = width;
            NativeImage modernImage;

            if (width == height * 2) {
                baseSize = width;
                modernImage = new NativeImage(baseSize, baseSize, true);
                modernImage.copyFrom(inputImage);
                inputImage.close();
                modernImage.fillRect(0, height, baseSize, height, 0);
            } else {
                modernImage = inputImage;
            }

            int scale = baseSize / 64;

            boolean hasLeftLeg = false;
            for (int y = 52 * scale; y < 64 * scale; y++) {
                for (int x = 16 * scale; x < 32 * scale; x++) {
                    if ((modernImage.getPixelRGBA(x, y) >> 24 & 255) > 0) {
                        hasLeftLeg = true;
                        break;
                    }
                }
            }

            if (!hasLeftLeg) {
                copyRectangle(modernImage, 4 * scale, 16 * scale, 20 * scale, 48 * scale, 4 * scale, 4 * scale, true, false);
                copyRectangle(modernImage, 8 * scale, 16 * scale, 24 * scale, 48 * scale, 4 * scale, 4 * scale, true, false);
                copyRectangle(modernImage, 0 * scale, 20 * scale, 24 * scale, 52 * scale, 4 * scale, 12 * scale, true, false);
                copyRectangle(modernImage, 4 * scale, 20 * scale, 20 * scale, 52 * scale, 4 * scale, 12 * scale, true, false);
                copyRectangle(modernImage, 8 * scale, 20 * scale, 16 * scale, 52 * scale, 4 * scale, 12 * scale, true, false);
                copyRectangle(modernImage, 12 * scale, 20 * scale, 28 * scale, 52 * scale, 4 * scale, 12 * scale, true, false);

                copyRectangle(modernImage, 44 * scale, 16 * scale, 36 * scale, 48 * scale, 4 * scale, 4 * scale, true, false);
                copyRectangle(modernImage, 48 * scale, 16 * scale, 40 * scale, 48 * scale, 4 * scale, 4 * scale, true, false);
                copyRectangle(modernImage, 40 * scale, 20 * scale, 40 * scale, 52 * scale, 4 * scale, 12 * scale, true, false);
                copyRectangle(modernImage, 44 * scale, 20 * scale, 36 * scale, 52 * scale, 4 * scale, 12 * scale, true, false);
                copyRectangle(modernImage, 48 * scale, 20 * scale, 32 * scale, 52 * scale, 4 * scale, 12 * scale, true, false);
                copyRectangle(modernImage, 52 * scale, 20 * scale, 44 * scale, 52 * scale, 4 * scale, 12 * scale, true, false);
            }

            return modernImage;
        }
        return inputImage;
    }

    private static void copyRectangle(NativeImage img, int srcX, int srcY, int destX, int destY, int width, int height, boolean mirrorX, boolean mirrorY) {
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int pixelX = mirrorX ? (width - 1 - x) : x;
                int pixelY = mirrorY ? (height - 1 - y) : y;
                pixels[y * width + x] = img.getPixelRGBA(srcX + pixelX, srcY + pixelY);
            }
        }
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                img.setPixelRGBA(destX + x, destY + y, pixels[y * width + x]);
            }
        }
    }

    public static void registerRemoteCosmetic(UUID playerId, int cosmeticType, String url) {
        receiveSkinPacket(playerId, cosmeticType, url);
    }

    public static void applySkin() {
        String path = IYCConfig.data.customSkinPath;
        if (path == null || path.isEmpty()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) return;

        if (!path.equals(loadedSkinPath)) {
            try {
                File file = new File(path);
                if (!file.exists()) return;
                byte[] imageData = Files.readAllBytes(file.toPath());

                try (InputStream is = new ByteArrayInputStream(imageData)) {
                    NativeImage image = NativeImage.read(is);

                    image = convertLegacySkin(image);

                    int width = image.getWidth();
                    int height = image.getHeight();
                    if (!(width % 64 == 0) || height != width) {
                        image.close();
                        return;
                    }

                    DynamicTexture texture = new DynamicTexture(image);
                    ResourceLocation rl = ResourceLocation.fromNamespaceAndPath("iyc", "custom_skin_" + mc.player.getUUID() + "_" + System.currentTimeMillis());

                    mc.execute(() -> {
                        mc.getTextureManager().register(rl, texture);
                        playerSkins.put(mc.player.getUUID(), rl);
                        loadedSkinPath = path;
                        refreshSpecificPlayer(mc, mc.player.getUUID());

                        String base64Data = Base64.getEncoder().encodeToString(compress(imageData));
                        SkinSyncPayload.sendChunked(mc.player.getUUID(), 0, base64Data, true, null);
                    });
                }
            } catch (Exception e) { e.printStackTrace(); }
        } else if (playerSkins.containsKey(mc.player.getUUID())) {
            mc.execute(() -> refreshSpecificPlayer(mc, mc.player.getUUID()));
        }
    }

    public static void applyCape() {
        String path = IYCConfig.data.customCapePath;
        if (path == null || path.isEmpty()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) return;

        if (!path.equals(loadedCapePath)) {
            try {
                File file = new File(path);
                if (!file.exists()) return;
                byte[] imageData = Files.readAllBytes(file.toPath());

                try (InputStream is = new ByteArrayInputStream(imageData)) {
                    NativeImage image = NativeImage.read(is);
                    DynamicTexture texture = new DynamicTexture(image);
                    ResourceLocation rl = ResourceLocation.fromNamespaceAndPath("iyc", "custom_cape_" + mc.player.getUUID() + "_" + System.currentTimeMillis());

                    mc.execute(() -> {
                        mc.getTextureManager().register(rl, texture);
                        playerCapes.put(mc.player.getUUID(), rl);
                        loadedCapePath = path;
                        refreshSpecificPlayer(mc, mc.player.getUUID());

                        String base64Data = Base64.getEncoder().encodeToString(compress(imageData));
                        SkinSyncPayload.sendChunked(mc.player.getUUID(), 1, base64Data, true, null);
                    });
                }
            } catch (Exception e) { e.printStackTrace(); }
        } else if (playerCapes.containsKey(mc.player.getUUID())) {
            mc.execute(() -> refreshSpecificPlayer(mc, mc.player.getUUID()));
        }
    }

    public static void applyHat() {
        String path = IYCConfig.data.customHatPath;
        if (path == null || path.isEmpty()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) return;

        if (!path.equals(loadedHatPath)) {
            try {
                File file = new File(path);
                if (!file.exists()) return;
                byte[] imageData = Files.readAllBytes(file.toPath());

                try (InputStream is = new ByteArrayInputStream(imageData)) {
                    NativeImage image = NativeImage.read(is);
                    DynamicTexture texture = new DynamicTexture(image);
                    ResourceLocation rl = ResourceLocation.fromNamespaceAndPath("iyc", "custom_hat_" + mc.player.getUUID() + "_" + System.currentTimeMillis());

                    mc.execute(() -> {
                        mc.getTextureManager().register(rl, texture);
                        playerHats.put(mc.player.getUUID(), rl);
                        loadedHatPath = path;

                        String base64Data = Base64.getEncoder().encodeToString(compress(imageData));
                        SkinSyncPayload.sendChunked(mc.player.getUUID(), 2, base64Data, true, null);
                    });
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public static void resetSkin() {
        IYCConfig.data.customSkinPath = "";
        IYCConfig.save();
        loadedSkinPath = "";
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        playerSkins.remove(mc.player.getUUID());
        refreshSpecificPlayer(mc, mc.player.getUUID());

        SkinSyncPayload.sendChunked(mc.player.getUUID(), 0, "RESET", true, null);
    }

    public static void resetCape() {
        IYCConfig.data.customCapePath = "";
        IYCConfig.save();
        loadedCapePath = "";
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        playerCapes.remove(mc.player.getUUID());
        refreshSpecificPlayer(mc, mc.player.getUUID());

        SkinSyncPayload.sendChunked(mc.player.getUUID(), 1, "RESET", true, null);
    }

    public static void resetHat() {
        IYCConfig.data.customHatPath = "";
        IYCConfig.save();
        loadedHatPath = "";
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        playerHats.remove(mc.player.getUUID());

        SkinSyncPayload.sendChunked(mc.player.getUUID(), 2, "RESET", true, null);
    }

    public static void receiveSkinPacket(UUID playerId, int cosmeticType, String textureData) {
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (textureData == null || textureData.isEmpty() || "RESET".equals(textureData)) {
                if (cosmeticType == 1) playerCapes.remove(playerId);
                else if (cosmeticType == 2) playerHats.remove(playerId);
                else playerSkins.remove(playerId);

                refreshSpecificPlayer(mc, playerId);
                return;
            }

            if (textureData.startsWith("http")) {
                return;
            }

            try {
                byte[] decoded = Base64.getDecoder().decode(textureData);
                byte[] rawData = decompress(decoded);

                try (InputStream is = new ByteArrayInputStream(rawData)) {
                    NativeImage image = NativeImage.read(is);

                    if (cosmeticType == 0) {
                        image = convertLegacySkin(image);
                    }

                    DynamicTexture texture = new DynamicTexture(image);
                    String typeStr = cosmeticType == 1 ? "cape" : (cosmeticType == 2 ? "hat" : "skin");
                    ResourceLocation rl = ResourceLocation.fromNamespaceAndPath("iyc", "custom_" + typeStr + "_" + playerId + "_" + System.currentTimeMillis());

                    mc.getTextureManager().register(rl, texture);

                    if (cosmeticType == 0) playerSkins.put(playerId, rl);
                    else if (cosmeticType == 1) playerCapes.put(playerId, rl);
                    else if (cosmeticType == 2) playerHats.put(playerId, rl);

                    refreshSpecificPlayer(mc, playerId);
                }
            } catch (Exception e) { e.printStackTrace(); }
        });
    }

    private static void refreshSpecificPlayer(Minecraft mc, UUID playerId) {
        if (mc.getConnection() == null) return;
        PlayerInfo info = mc.getConnection().getPlayerInfo(playerId);
        if (info == null) return;

        try {
            PlayerSkin originalSkin = mc.getSkinManager().getInsecureSkin(info.getProfile());

            ResourceLocation finalSkin = playerSkins.containsKey(playerId) ? playerSkins.get(playerId) : (originalSkin != null ? originalSkin.texture() : null);
            ResourceLocation finalCape = playerCapes.containsKey(playerId) ? playerCapes.get(playerId) : (originalSkin != null ? originalSkin.capeTexture() : null);
            ResourceLocation finalElytra = playerCapes.containsKey(playerId) ? playerCapes.get(playerId) : (originalSkin != null ? originalSkin.elytraTexture() : null);

            PlayerSkin.Model model = (originalSkin != null) ? originalSkin.model() : PlayerSkin.Model.WIDE;
            boolean secure = (originalSkin != null) && originalSkin.secure();
            String url = (originalSkin != null) ? originalSkin.textureUrl() : "";

            PlayerSkin newSkin = new PlayerSkin(finalSkin, url, finalCape, finalElytra, model, secure);

            for (Field field : PlayerInfo.class.getDeclaredFields()) {
                if (field.getType() == Supplier.class) {
                    field.setAccessible(true);
                    field.set(info, (Supplier<PlayerSkin>) () -> newSkin);
                    break;
                } else if (field.getType() == PlayerSkin.class) {
                    field.setAccessible(true);
                    field.set(info, newSkin);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResourceLocation getCapeTexture(UUID playerId) {
        return playerCapes.get(playerId);
    }

    public static ResourceLocation getHatTexture(UUID playerId) {
        return playerHats.get(playerId);
    }
}