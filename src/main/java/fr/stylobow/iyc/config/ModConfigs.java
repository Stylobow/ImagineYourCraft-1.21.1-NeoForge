package fr.stylobow.iyc.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfigs {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<String> CUSTOM_WEBHOOK_URL;

    static {
        BUILDER.comment("IYC Custom Skins").push("skins");

        CUSTOM_WEBHOOK_URL = BUILDER
                .comment("Discord webhook URL for storing skins & capes. If empty, use the mod's default storage.")
                .define("webhook_url", "");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}