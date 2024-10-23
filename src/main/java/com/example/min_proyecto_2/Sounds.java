package com.example.min_proyecto_2;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sounds {

    private Clip clip;
    public void loadSound(String path) {
        try {
            File soundfile = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundfile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        if(clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stopSound() {
        if(clip != null) {
            clip.stop();
        }
    }
}
