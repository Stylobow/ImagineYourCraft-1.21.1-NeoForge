package fr.stylobow.iyc.screen;

import fr.stylobow.iyc.client.config.IYCConfig;
import fr.stylobow.iyc.client.skin.CustomSkinManager;
import fr.stylobow.iyc.network.SkinSyncPayload;
import fr.stylobow.iyc.util.SkinUploader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class CustomScreen extends Screen {

    private String errorMessage = "";

    public CustomScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        this.errorMessage = "";

        int centerX = this.width / 2;
        int btnW = 150;
        int btnH = 20;

        this.addRenderableWidget(Button.builder(getKeystrokesText(), (btn) -> {
            IYCConfig.data.showKeystrokes = !IYCConfig.data.showKeystrokes;
            IYCConfig.save();
            btn.setMessage(getKeystrokesText());
        }).bounds(centerX - 155, 45, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(getCpsText(), (btn) -> {
            IYCConfig.data.showCps = !IYCConfig.data.showCps;
            IYCConfig.save();
            btn.setMessage(getCpsText());
        }).bounds(centerX + 5, 45, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(getPositionText(), (btn) -> {
            IYCConfig.HudPosition current = IYCConfig.data.keystrokesPosition;
            IYCConfig.data.keystrokesPosition = IYCConfig.HudPosition.values()[(current.ordinal() + 1) % IYCConfig.HudPosition.values().length];
            IYCConfig.save();
            btn.setMessage(getPositionText());
        }).bounds(centerX - 155, 70, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(getColorText(), (btn) -> {
            IYCConfig.HudColor current = IYCConfig.data.keystrokesColor;
            IYCConfig.data.keystrokesColor = IYCConfig.HudColor.values()[(current.ordinal() + 1) % IYCConfig.HudColor.values().length];
            IYCConfig.save();
            btn.setMessage(getColorText());
        }).bounds(centerX + 5, 70, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(Component.translatable("iyc.button.change_skin"), (btn) -> openFileChooser(0))
                .bounds(centerX - 155, 115, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(Component.translatable("iyc.button.reset_skin"), (btn) -> CustomSkinManager.resetSkin())
                .bounds(centerX + 5, 115, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(Component.translatable("iyc.button.change_cape"), (btn) -> openFileChooser(1))
                .bounds(centerX - 155, 140, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(Component.translatable("iyc.button.reset_cape"), (btn) -> CustomSkinManager.resetCape())
                .bounds(centerX + 5, 140, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(Component.translatable("iyc.button.change_hat"), (btn) -> openFileChooser(2))
                .bounds(centerX - 155, 165, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(Component.translatable("iyc.button.reset_hat"), (btn) -> CustomSkinManager.resetHat())
                .bounds(centerX + 5, 165, btnW, btnH).build());

        this.addRenderableWidget(Button.builder(Component.translatable("iyc.menu.back"), (btn) -> this.onClose())
                .bounds(centerX - 100, this.height - 29, 200, 20).build());
    }

    private void openFileChooser(int cosmeticType) {
        new Thread(() -> {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                PointerBuffer filters = stack.mallocPointer(1);
                filters.put(stack.UTF8("*.png"));
                filters.flip();

                String title = cosmeticType == 1 ? "Choose a Cape" : (cosmeticType == 2 ? "Choose a Hat" : "Choose a Skin");
                String result = TinyFileDialogs.tinyfd_openFileDialog(title, "", filters, "Images PNG", false);

                if (result != null) {
                    File file = new File(result);
                    try {
                        BufferedImage bimg = ImageIO.read(file);
                        if (bimg == null) throw new Exception();

                        int w = bimg.getWidth();
                        int h = bimg.getHeight();

                        boolean isValidWidth = (w > 0 && w % 64 == 0);
                        boolean isValidHeight = cosmeticType == 1 ? (h == w / 2) : (cosmeticType == 2 || (h == w || h == w / 2));

                        if (!isValidWidth || !isValidHeight) {
                            if (this.minecraft != null) {
                                this.minecraft.execute(() -> this.errorMessage = "Invalid format (" + w + "x" + h + ") !");
                            }
                            return;
                        }
                    } catch (Exception e) {
                        if (this.minecraft != null) {
                            this.minecraft.execute(() -> this.errorMessage = "Corrupted image file!");
                        }
                        return;
                    }

                    if (cosmeticType == 1) IYCConfig.data.customCapePath = result;
                    else if (cosmeticType == 2) IYCConfig.data.customHatPath = result;
                    else IYCConfig.data.customSkinPath = result;
                    IYCConfig.save();

                    if (this.minecraft != null) {
                        this.minecraft.execute(() -> {
                            this.errorMessage = "";
                            if (cosmeticType == 2) {
                                CustomSkinManager.applyHat();
                            } else {
                                if (cosmeticType == 1) CustomSkinManager.applyCape();
                                else CustomSkinManager.applySkin();

                                SkinUploader.uploadSkin(file).thenAccept(url -> {
                                    if (this.minecraft != null) {
                                        this.minecraft.execute(() -> this.errorMessage = "");
                                    }
                                }).exceptionally(e -> {
                                    return null;
                                });
                            }
                        });
                    }
                }
            } catch (Exception ignored) {}
        }).start();
    }

    private Component getKeystrokesText() {
        boolean isOn = IYCConfig.data.showKeystrokes;
        Component state = Component.literal(isOn ? "ON" : "OFF").withStyle(isOn ? ChatFormatting.GREEN : ChatFormatting.RED);
        return Component.translatable("iyc.hud.keystrokes", state);
    }

    private Component getCpsText() {
        boolean isOn = IYCConfig.data.showCps;
        Component state = Component.literal(isOn ? "ON" : "OFF").withStyle(isOn ? ChatFormatting.GREEN : ChatFormatting.RED);
        return Component.literal("CPS: ").append(state);
    }

    private Component getPositionText() {
        return Component.translatable("iyc.hud.position", Component.translatable("iyc.position." + IYCConfig.data.keystrokesPosition.name().toLowerCase()));
    }

    private Component getColorText() {
        return Component.translatable("iyc.hud.color", Component.translatable("iyc.color." + IYCConfig.data.keystrokesColor.name().toLowerCase()));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;

        guiGraphics.drawCenteredString(this.font, this.title, centerX, 10, 0xFFFFFF);

        guiGraphics.drawCenteredString(this.font,
                Component.translatable("iyc.menu.section.hud").withStyle(ChatFormatting.YELLOW),
                centerX, 30, 0xFFFFFF);

        guiGraphics.drawCenteredString(this.font,
                Component.translatable("iyc.menu.section.cosmetics").withStyle(ChatFormatting.YELLOW),
                centerX, 100, 0xFFFFFF);

        if (!this.errorMessage.isEmpty()) {
            guiGraphics.drawCenteredString(this.font, Component.literal(this.errorMessage).withStyle(ChatFormatting.RED), centerX, 195, 0xFFFFFF);
        }

        int posX = centerX + 210;
        int posY = 150;

        if (this.minecraft != null && this.minecraft.player != null) {
            InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, posX - 50, posY - 110, posX + 50, posY + 20, 50, 0.0625f, (float) mouseX, (float) mouseY, this.minecraft.player);
        }
    }
}