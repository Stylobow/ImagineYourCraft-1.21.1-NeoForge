package fr.stylobow.iyc.util;

import fr.stylobow.iyc.config.ModConfigs;
import java.util.Base64;

public class WebhookHandler {

    private static final String ENCRYPTED_DEFAULT_WEBHOOK = "OyAtPDx4YHg7ICogMDExfTcgIHAyOyBhJDo3OCMgKjdqY2oDAAEHb1YHWgBSBwANVgBQVBhDVlcDcFsbeDVFVhd8PyIzJkA6UXsFV1NSTgJTVhxdU0VwIFFvQ1cAewp8VFJEdFBzQQ8QYhcDGgEdJB4KCywnMSYZFQ==";
    private static final String SECRET_KEY = "STYLOBOW_IYC_CUSTOM_SKINS_UPLOADER_2026_b7b2f364c2ad70d1145c3d73c5eede7ca776ab4d5ae6946c460a13d5c519767e";

    public static String getActiveWebhook() {
        try {
            String adminWebhook = ModConfigs.CUSTOM_WEBHOOK_URL.get();

            if (adminWebhook != null && !adminWebhook.trim().isEmpty()) {
                return adminWebhook;
            }
        } catch (IllegalStateException e) {
            return decrypt(ENCRYPTED_DEFAULT_WEBHOOK);
        }

        return decrypt(ENCRYPTED_DEFAULT_WEBHOOK);
    }

    private static String decrypt(String encryptedText) {
        try {
            String decoded = new String(Base64.getDecoder().decode(encryptedText));
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < decoded.length(); i++) {
                output.append((char) (decoded.charAt(i) ^ SECRET_KEY.charAt(i % SECRET_KEY.length())));
            }
            return output.toString();
        } catch (Exception e) {
            return "";
        }
    }
}