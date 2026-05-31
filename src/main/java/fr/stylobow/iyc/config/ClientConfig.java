package fr.stylobow.iyc.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {

    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue showKeystrokes;
    public static final ModConfigSpec.BooleanValue showCps;
    public static final ModConfigSpec.EnumValue<HudPosition> position;
    public static final ModConfigSpec.EnumValue<HudColor> textColor;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("keystrokes_hud");

        showKeystrokes = builder
                .comment("Show or hide HUD")
                .define("showKeystrokes", false);

        showCps = builder
                .comment("Show CPS")
                .define("showCps", false);

        position = builder
                .comment("HUD Position")
                .defineEnum("position", HudPosition.BOTTOM_RIGHT);

        textColor = builder
                .comment("Text color")
                .defineEnum("color", HudColor.WHITE);

        builder.pop();

        SPEC = builder.build();
    }

    public enum HudPosition {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    public enum HudColor {
        WHITE(0xFFFFFF),
        RED(0xFF0000),
        GREEN(0x00FF00),
        BLUE(0x0000FF),
        YELLOW(0xFFFF00),
        PURPLE(0xA020F0),
        GOLD(0xFFAA00),
        BLACK(0x000000),
        CHROMA(0x000000);

        public final int hex;
        HudColor(int hex) { this.hex = hex; }
    }
}