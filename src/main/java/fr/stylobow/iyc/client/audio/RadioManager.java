package fr.stylobow.iyc.client.audio;

import fr.stylobow.iyc.client.config.IYCConfig;
import fr.stylobow.iyc.sound.ModSounds;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RadioManager {

    private static Player player;
    private static Thread playerThread;
    private static String currentRadio = "";
    private static CustomAudioDevice customDevice;

    private static boolean isIycRadioActive = false;
    private static SimpleSoundInstance currentLocalSoundInstance = null;

    private static final List<ResourceLocation> playlist = new ArrayList<>();
    private static int playlistIndex = 0;

    private static void preparePlaylist() {
        playlist.clear();
        for (DeferredHolder<?, ?> holder : ModSounds.SOUND_EVENTS.getEntries()) {
            playlist.add(holder.getId());
        }
        Collections.shuffle(playlist);
        playlistIndex = 0;
    }

    public static void play(String urlStr, String radioName) {
        if ("IYC Radio".equals(radioName)) {
            isIycRadioActive = true;
            stopWebStream();
            stopLocalSound();
            currentRadio = radioName;

            preparePlaylist();
            playNextLocalTrack();
        } else {
            isIycRadioActive = false;
            stopLocalSound();
            playWebStream(urlStr, radioName);
        }
    }

    private static void playWebStream(String urlStr, String radioName) {
        stopWebStream();
        currentRadio = radioName;

        playerThread = new Thread(() -> {
            try {
                URL url = URI.create(urlStr).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                InputStream is = connection.getInputStream();

                if (!radioName.equals(currentRadio)) {
                    is.close();
                    return;
                }

                customDevice = new CustomAudioDevice();
                customDevice.setCustomVolume(IYCConfig.data.musicVolume);

                player = new Player(is, customDevice);

                if (!radioName.equals(currentRadio)) {
                    player.close();
                    return;
                }

                player.play();
            } catch (Exception e) {
                if (radioName.equals(currentRadio)) {
                    currentRadio = "";
                }
            }
        });

        playerThread.setContextClassLoader(RadioManager.class.getClassLoader());
        playerThread.setDaemon(true);
        playerThread.start();
    }

    private static void playLocalTrack(ResourceLocation location) {
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            float gameVolume = mc.options.getSoundSourceVolume(SoundSource.MASTER);
            float recordsVolume = mc.options.getSoundSourceVolume(SoundSource.RECORDS);

            float targetVolume = IYCConfig.data.musicVolume;
            if (gameVolume > 0 && recordsVolume > 0) {
                targetVolume = targetVolume / (gameVolume * recordsVolume);
            }

            currentLocalSoundInstance = new SimpleSoundInstance(
                    location,
                    SoundSource.RECORDS,
                    targetVolume,
                    1.0F,
                    SoundInstance.createUnseededRandom(),
                    false,
                    0,
                    SoundInstance.Attenuation.NONE,
                    0.0D,
                    0.0D,
                    0.0D,
                    true
            );
            mc.getSoundManager().play(currentLocalSoundInstance);
        });
    }

    public static void playNextLocalTrack() {
        if (!isIycRadioActive || playlist.isEmpty()) return;

        if (playlistIndex >= playlist.size()) {
            Collections.shuffle(playlist);
            playlistIndex = 0;
        }

        ResourceLocation nextSound = playlist.get(playlistIndex++);
        playLocalTrack(nextSound);
    }

    public static void tickIycRadio() {
        if (!isIycRadioActive) return;

        Minecraft mc = Minecraft.getInstance();
        if (currentLocalSoundInstance != null && !mc.getSoundManager().isActive(currentLocalSoundInstance)) {
            playNextLocalTrack();
        }
    }

    private static void stopLocalSound() {
        if (currentLocalSoundInstance != null) {
            Minecraft mc = Minecraft.getInstance();
            mc.execute(() -> mc.getSoundManager().stop(currentLocalSoundInstance));
            currentLocalSoundInstance = null;
        }
    }

    private static void stopWebStream() {
        if (player != null) {
            player.close();
            player = null;
        }
        if (playerThread != null) {
            playerThread.interrupt();
            playerThread = null;
        }
        customDevice = null;
    }

    public static void stop() {
        isIycRadioActive = false;
        playlist.clear();
        stopWebStream();
        stopLocalSound();
        currentRadio = "";
    }

    public static void setVolume(float volume) {
        IYCConfig.data.musicVolume = volume;
        IYCConfig.save();
        if (customDevice != null) {
            customDevice.setCustomVolume(volume);
        }
        if (currentLocalSoundInstance != null) {
            Minecraft mc = Minecraft.getInstance();
            float gameVolume = mc.options.getSoundSourceVolume(SoundSource.MASTER);
            float recordsVolume = mc.options.getSoundSourceVolume(SoundSource.RECORDS);

            float targetVolume = volume;
            if (gameVolume > 0 && recordsVolume > 0) {
                targetVolume = targetVolume / (gameVolume * recordsVolume);
            }
            mc.getSoundManager().updateSourceVolume(SoundSource.RECORDS, targetVolume);
        }
    }

    public static float getVolume() {
        return IYCConfig.data.musicVolume;
    }

    public static String getCurrentRadio() {
        return currentRadio;
    }

    private static class CustomAudioDevice extends JavaSoundAudioDevice {
        private float volume = 0.5f;

        public void setCustomVolume(float vol) {
            this.volume = Math.max(0.0f, Math.min(vol, 1.0f));
            updateVolume();
        }

        private void updateVolume() {
            try {
                Field sourceField = JavaSoundAudioDevice.class.getDeclaredField("source");
                sourceField.setAccessible(true);
                SourceDataLine source = (SourceDataLine) sourceField.get(this);
                if (source != null && source.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
                    float dB = (float) (Math.log(this.volume <= 0.0f ? 0.0001f : this.volume) / Math.log(10.0) * 20.0);
                    gainControl.setValue(Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), dB)));
                }
            } catch (Exception ignored) {
            }
        }

        @Override
        protected void createSource() throws JavaLayerException {
            super.createSource();
            updateVolume();
        }
    }
}