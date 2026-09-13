package breakout;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;

public class SoundManager {
    private static final Map<String, Clip> soundMap = new HashMap<>();
    private static Clip bgmClip;
    private static boolean isInitialized = false;

    public static void init() {
        if (isInitialized) return;

        System.out.println("正在預載打磚塊遊戲音效...");
        // 載入倒數與基本音效
        preloadSound("countdown", "sounds/countdown.wav");
        preloadSound("start",     "sounds/start.wav");
        preloadSound("hit",       "sounds/hit.wav"); // 可以複用切水果的聲音當作撞擊聲
        preloadSound("bounce",       "sounds/bounce.wav"); // 可以複用切水果的聲音當作撞擊聲
        preloadSound("gameover",       "sounds/gameover.wav"); // 可以複用切水果的聲音當作撞擊聲

        preloadBGM("sounds/bgm.wav");
        
        isInitialized = true;
    }

    private static void preloadSound(String key, String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) return;
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            soundMap.put(key, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void preloadBGM(String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) return;
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSound(String key) {
        Clip clip = soundMap.get(key);
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public static void playBGM() {
        if (bgmClip == null) return;
        if (bgmClip.isRunning()) bgmClip.stop();
        bgmClip.setFramePosition(0);
        bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) bgmClip.stop();
    }
}
