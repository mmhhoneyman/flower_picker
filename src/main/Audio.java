package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Audio {
	
	private Clip clip;
    private FloatControl volumeControl;
    String filePath;
    boolean loop;

    public Audio(String filePath, boolean loop) {
    	try {
    		URL audioURL = getClass().getResource(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            this.filePath = filePath;
            this.loop = loop;

            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            clip.setFramePosition(0);
            clip.setFramePosition(0);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
    	if(clip.isRunning()) {
    		clip.stop();
    	}
        clip.flush();
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float value = min + (max - min) * volume;
            volumeControl.setValue(value);
        }
    }

    // Close resources
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
  
}
