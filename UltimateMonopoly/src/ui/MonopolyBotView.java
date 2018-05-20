package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import domain.Observer;
import domain.PlayerObservable;

public class MonopolyBotView extends JPanel implements Observer {

	private Map<String, Image> images = new HashMap<>();
	private Timer animationTimer;
	private ArrayList<Animatable> animations;

	public MonopolyBotView() {
		setLayout(null);
		animations = new ArrayList<Animatable>();
		PlayerObservable.getInstance().addObserver(this);

		try {
			images.put("money", ImageIO.read(new File("assets/emojis/money.png")));
			images.put("bankrupt", ImageIO.read(new File("assets/emojis/bankrupt.png")));
			images.put("jail", ImageIO.read(new File("assets/emojis/jail.png")));
			images.put("idle", ImageIO.read(new File("assets/emojis/sleepy.png")));
			images.put("win", ImageIO.read(new File("assets/emojis/trophy.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		animationTimer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}

		});
		animationTimer.start();
	}

	@Override
	public void update(String key, String action, Object obj) {
		if (key == "MonopolyBotView") {
			if (action == "animate") {
				animations.add(
						AnimationFactory.getInstance().createRandomAnimation(getWidth(), getHeight(), obj.toString()));
			} else if (action == "stopIdleAnimation") {
				for (int i = animations.size() - 1; i >= 0; i--) {
					if (animations.get(i).getEmojiName().equals("idle")) {
						animations.remove(i);
					}
				}
			}
		}	
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = animations.size() - 1; i >= 0; i--) {
			Animatable a = animations.get(i);
			if (a.framesAvailable()) {
				g.drawImage(images.get(a.getEmojiName()), a.getX(), a.getY(), a.getWidth(), a.getHeight(), this);
				a.nextFrame();
			} else {
				animations.remove(i);
			}
		}

	}

}
