package ui;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
	private static SoundManager instance;

	public SoundManager playSound(String name) {
		switch (name) {
		case "win":
			play("win.wav");
			break;
		case "jail":
			play("jail.wav");
			play("siren.wav");
			break;
		case "transaction":
			play("transaction.wav");
			break;
		case "idle":
			play("idle.wav");
			break;
		}

		return null;
	}

	public static SoundManager getInstance() {
		if (instance == null)
			instance = new SoundManager();
		return instance;
	}

	private synchronized void play(final String name) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("assets/sounds/" + name));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
