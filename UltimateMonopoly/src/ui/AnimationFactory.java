package ui;

import java.util.Random;

public class AnimationFactory {

	private static AnimationFactory instance = null;

	private AnimationFactory() {

	}

	public synchronized static AnimationFactory getInstance() {
		if (instance == null)
			instance = new AnimationFactory();

		return instance;
	}

	public Animatable createRandomAnimation(int panelWidth, int panelHeight, String emojiName) {

		Random r = new Random();
		int rand = r.nextInt(3);
		Animatable anim;
		if (rand == 0) {
			anim = new BouncyAnimationGenerator();
			anim.initAnimation(0, 0, 60, 60, panelWidth, panelHeight, emojiName);
		} else if (rand == 1) {
			anim = new SpringAnimationGenerator();
			anim.initAnimation(0, 0, 100, 100, panelWidth, panelHeight, emojiName);
		} else {
			anim = new ZoomInOutAnimationGenerator();
			anim.initAnimation(0, 0, 100, 100, panelWidth, panelHeight, emojiName);
		}

		return anim;
	}

}
