package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
	
	private Clip clip;
    private FloatControl volumeControl;
    String filePath;
    boolean loop;

    public Audio(String filePath, boolean loop) {
    	try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            this.filePath = filePath;
            this.loop = loop;

            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            clip.setFramePosition(0);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.stop();
                    clip.flush();
                    clip.setFramePosition(0);

                    if (loop) {
                        clip.start();
                    }
                }
            });
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip.isRunning()) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
        }
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
