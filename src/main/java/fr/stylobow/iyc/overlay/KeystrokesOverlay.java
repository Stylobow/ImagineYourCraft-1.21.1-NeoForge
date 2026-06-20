package fr.stylobow.iyc.overlay;

import fr.stylobow.iyc.client.config.IYCConfig;
import fr.stylobow.iyc.util.CpsSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;

public class KeystrokesOverlay implements LayeredDraw.Layer {

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (!IYCConfig.data.showKeystrokes) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui) return;

        int boxSize = 20;
        int gap = 2;
        int totalWidth = (boxSize * 3) + (gap * 2);
        int totalHeight = (boxSize * 2) + gap + 15;

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();
        int x = 10, y = 10;

        switch (IYCConfig.data.keystrokesPosition) {
            case TOP_LEFT -> { x = 10; y = 10; }
            case TOP_RIGHT -> { x = screenWidth - totalWidth - 10; y = 10; }
            case BOTTOM_LEFT -> { x = 10; y = screenHeight - totalHeight - 10; }
            case BOTTOM_RIGHT -> { x = screenWidth - totalWidth - 10; y = screenHeight - totalHeight - 10; }
        }

        drawKey(guiGraphics, mc.options.keyUp, x + boxSize + gap, y, boxSize, boxSize);

        int yRow2 = y + boxSize + gap;
        drawKey(guiGraphics, mc.options.keyLeft, x, yRow2, boxSize, boxSize);
        drawKey(guiGraphics, mc.options.keyDown, x + boxSize + gap, yRow2, boxSize, boxSize);
        drawKey(guiGraphics, mc.options.keyRight, x + (boxSize + gap) * 2, yRow2, boxSize, boxSize);

        int yRow3 = yRow2 + boxSize + gap;
        int mouseWidth = (totalWidth - gap) / 2;
        int mouseHeight = 22;

        long windowHandle = mc.getWindow().getWindow();

        drawMouseBox(guiGraphics, x, yRow3, mouseWidth, mouseHeight,
                GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS,
                Component.translatable("iyc.key.mouse.left").getString(),
                CpsSystem.getLeftCps());

        drawMouseBox(guiGraphics, x + mouseWidth + gap, yRow3, mouseWidth, mouseHeight,
                GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS,
                Component.translatable("iyc.key.mouse.right").getString(),
                CpsSystem.getRightCps());
    }

    private void drawKey(GuiGraphics gfx, KeyMapping key, int x, int y, int w, int h) {
        boolean pressed = key.isDown();
        String keyName = key.getTranslatedKeyMessage().getString().toUpperCase();
        if(keyName.length() > 1) keyName = keyName.substring(0, 1);

        drawBoxBackground(gfx, x, y, w, h, pressed);

        int color = pressed ? 0x000000 : getTextColor();
        gfx.drawCenteredString(Minecraft.getInstance().font, keyName, x + w / 2, y + (h - 8) / 2, color);
    }

    private void drawMouseBox(GuiGraphics gfx, int x, int y, int w, int h, boolean pressed, String label, int cps) {
        drawBoxBackground(gfx, x, y, w, h, pressed);

        int labelColor = pressed ? 0x000000 : getTextColor();
        int cpsColor = pressed ? 0x000000 : 0xFFFFFF;

        Minecraft mc = Minecraft.getInstance();
        boolean showCps = IYCConfig.data.showCps;

        if (!showCps) {
            gfx.drawCenteredString(mc.font, label, x + w / 2, y + (h - 8) / 2, labelColor);
        } else {
            gfx.drawCenteredString(mc.font, label, x + w / 2, y + 3, labelColor);

            String cpsText = cps + " CPS";
            float scale = 0.7f;

            gfx.pose().pushPose();

            float centerX = x + (w / 2.0f);
            float textY = y + 13;

            gfx.pose().translate(centerX, textY, 0);
            gfx.pose().scale(scale, scale, scale);

            gfx.drawCenteredString(mc.font, cpsText, 0, 0, cpsColor);

            gfx.pose().popPose();
        }
    }

    private void drawBoxBackground(GuiGraphics gfx, int x, int y, int w, int h, boolean pressed) {
        int bgColor = pressed ? 0x99FFFFFF : 0x90000000;
        gfx.fill(x, y, x + w, y + h, bgColor);
    }

    private int getTextColor() {
        if (IYCConfig.data.keystrokesColor == IYCConfig.HudColor.CHROMA) {
            return getRainbowColor();
        }
        return IYCConfig.data.keystrokesColor.hex;
    }

    private int getRainbowColor() {
        float speed = 4000f;
        float hue = (System.currentTimeMillis() % (int)speed) / speed;
        return Color.HSBtoRGB(hue, 1.0f, 1.0f);
    }
}