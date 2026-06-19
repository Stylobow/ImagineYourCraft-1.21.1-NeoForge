package fr.stylobow.iyc.client.gui;

import fr.stylobow.iyc.client.audio.RadioManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.awt.Color;

public class MusicScreen extends Screen {

    private Button nrjButton;
    private Button skyrockButton;
    private Button funRadioButton;
    private Button rtlButton;
    private Button tomorrowlandButton;
    private Button iycradioButton;
    private Button stopButton;
    private AbstractSliderButton volumeSlider;

    public MusicScreen() {
        super(Component.translatable("iyc.music.title"));
    }

    @Override
    protected void init() {
        int btnWidth = 100;
        int btnHeight = 20;
        int spacing = 10;

        int midCol = this.width / 2 - 50;
        int leftCol = midCol - btnWidth - spacing;
        int rightCol = midCol + btnWidth + spacing;

        int row1 = 60;
        int row2 = row1 + btnHeight + 5;
        int stopRow = row2 + btnHeight + 5;
        int volumeRow = stopRow + btnHeight + 5;

        this.nrjButton = Button.builder(Component.literal("NRJ"), button -> {
            RadioManager.play("https://streaming.nrjaudio.fm/oumvmk8fnozc", "NRJ");
            updateButtons();
        }).bounds(leftCol, row1, btnWidth, btnHeight).build();
        this.addRenderableWidget(this.nrjButton);

        this.skyrockButton = Button.builder(Component.literal("Skyrock"), button -> {
            RadioManager.play("http://icecast.skyrock.net/s/natio_mp3_128k", "Skyrock");
            updateButtons();
        }).bounds(midCol, row1, btnWidth, btnHeight).build();
        this.addRenderableWidget(this.skyrockButton);

        this.funRadioButton = Button.builder(Component.literal("Fun Radio"), button -> {
            RadioManager.play("http://streaming.radio.funradio.fr/fun-1-44-128?listen=webCwsBCggNCQgLDQUGBAcGBg", "FunRadio");
            updateButtons();
        }).bounds(rightCol, row1, btnWidth, btnHeight).build();
        this.addRenderableWidget(this.funRadioButton);

        this.rtlButton = Button.builder(Component.literal("RTL"), button -> {
            RadioManager.play("http://streaming.radio.rtl.fr/rtl-1-44-128?listen=webCwsBCggNCQgLDQUGBAcGBg", "RTL");
            updateButtons();
        }).bounds(leftCol, row2, btnWidth, btnHeight).build();
        this.addRenderableWidget(this.rtlButton);

        this.tomorrowlandButton = Button.builder(Component.literal("TomorrowLand"), button -> {
            RadioManager.play("https://playerservices.streamtheworld.com/api/livestream-redirect/OWR_INTERNATIONAL.mp3", "TomorrowLand");
            updateButtons();
        }).bounds(midCol, row2, btnWidth, btnHeight).build();
        this.addRenderableWidget(this.tomorrowlandButton);

        this.iycradioButton = Button.builder(Component.literal("IYC Radio"), button -> {
            RadioManager.play("", "IYC Radio");
            updateButtons();
        }).bounds(rightCol, row2, btnWidth, btnHeight).build();
        this.addRenderableWidget(this.iycradioButton);

        this.stopButton = Button.builder(Component.translatable("iyc.music.stop"), button -> {
            RadioManager.stop();
            updateButtons();
        }).bounds(midCol, stopRow, btnWidth, btnHeight).build();
        this.addRenderableWidget(this.stopButton);

        this.volumeSlider = new AbstractSliderButton(midCol, volumeRow, btnWidth, btnHeight, Component.translatable("iyc.music.volume", (int)(RadioManager.getVolume() * 100)), RadioManager.getVolume()) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.translatable("iyc.music.volume", (int) (this.value * 100)));
            }

            @Override
            protected void applyValue() {
                RadioManager.setVolume((float) this.value);
            }
        };
        this.addRenderableWidget(this.volumeSlider);

        this.addRenderableWidget(Button.builder(Component.translatable("iyc.music.done"), button -> {
            this.minecraft.setScreen(null);
        }).bounds(this.width / 2 - 60, this.height - 30, 120, 20).build());

        updateButtons();
    }

    private void updateButtons() {
        String current = RadioManager.getCurrentRadio();
        this.nrjButton.active = !"NRJ".equals(current);
        this.skyrockButton.active = !"Skyrock".equals(current);
        this.tomorrowlandButton.active = !"TomorrowLand".equals(current);
        this.rtlButton.active = !"RTL".equals(current);
        this.funRadioButton.active = !"FunRadio".equals(current);
        this.iycradioButton.active = !"IYC Radio".equals(current);

        this.stopButton.active = !current.isEmpty();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        float hue = (System.currentTimeMillis() % 5000L) / 5000.0f;
        int color = Color.HSBtoRGB(hue, 0.8f, 0.8f) & 0xFFFFFF;
        this.iycradioButton.setMessage(Component.literal("IYC Radio").withStyle(Style.EMPTY.withColor(color)));

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font, Component.translatable("iyc.music.disclaimer"), this.width / 2, 40, 0xAAAAAA);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
    }
}