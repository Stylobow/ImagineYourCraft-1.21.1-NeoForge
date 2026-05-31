package fr.stylobow.iyc.util;

import fr.stylobow.iyc.ImagineYourCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SkinUploader {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static CompletableFuture<String> uploadSkin(File skinFile) {
        String webhookUrl = WebhookHandler.getActiveWebhook();
        if (webhookUrl.isEmpty()) {
            return CompletableFuture.failedFuture(new IllegalStateException("No valid webhooks found."));
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                String boundary = "X-IYC-BOUNDARY-" + UUID.randomUUID();
                byte[] fileBytes = Files.readAllBytes(skinFile.toPath());
                String fileName = skinFile.getName();

                Minecraft mc = Minecraft.getInstance();
                LocalPlayer player = mc.player;
                String username = player != null ? player.getGameProfile().getName() : "Inconnu";
                UUID uuid = player != null ? player.getGameProfile().getId() : UUID.randomUUID();
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

                String modVersion = ModList.get().getModContainerById(ImagineYourCraft.MOD_ID)
                        .map(ModContainer::getModInfo)
                        .map(info -> info.getVersion().toString())
                        .orElse("Unknown");
                if (ModList.get() != null && ModList.get().getModContainerById("iyc").isPresent()) {
                    modVersion = ModList.get().getModContainerById("iyc").get().getModInfo().getVersion().toString();
                }

                String context = mc.getSingleplayerServer() != null ? "Singleplayer" : "Multiplayer Server";

                String messageContent =
                          "**Player :** " + username + "\\n"
                        + "**UUID :** " + uuid.toString() + "\\n"
                        + "**Date :** " + timestamp + "\\n"
                        + "**Version :** " + modVersion + "\\n"
                        + "**Context :** " + context;

                String jsonPayload = "{\"content\": \"" + messageContent + "\"}";

                StringBuilder multipartBuilder = new StringBuilder();

                multipartBuilder.append("--").append(boundary).append("\r\n");
                multipartBuilder.append("Content-Disposition: form-data; name=\"payload_json\"\r\n");
                multipartBuilder.append("Content-Type: application/json\r\n\r\n");
                multipartBuilder.append(jsonPayload).append("\r\n");

                multipartBuilder.append("--").append(boundary).append("\r\n");
                multipartBuilder.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(fileName).append("\"\r\n");
                multipartBuilder.append("Content-Type: image/png\r\n\r\n");

                byte[] headerBytes = multipartBuilder.toString().getBytes();
                byte[] footerBytes = ("\r\n--" + boundary + "--\r\n").getBytes();

                byte[] requestBody = new byte[headerBytes.length + fileBytes.length + footerBytes.length];
                System.arraycopy(headerBytes, 0, requestBody, 0, headerBytes.length);
                System.arraycopy(fileBytes, 0, requestBody, headerBytes.length, fileBytes.length);
                System.arraycopy(footerBytes, 0, requestBody, headerBytes.length + fileBytes.length, footerBytes.length);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(webhookUrl))
                        .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                        .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                        .build();

                HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200 || response.statusCode() == 204) {
                    return extractImageUrlFromJson(response.body());
                } else {
                    throw new IOException("Discord HTTP Error: " + response.statusCode());
                }

            } catch (Exception e) {
                throw new RuntimeException("Skin upload failed", e);
            }
        });
    }

    private static String extractImageUrlFromJson(String jsonResponse) {
        int urlIndex = jsonResponse.indexOf("\"url\": \"");
        if (urlIndex == -1) return "";

        int start = urlIndex + 8;
        int end = jsonResponse.indexOf("\"", start);
        return jsonResponse.substring(start, end);
    }
}