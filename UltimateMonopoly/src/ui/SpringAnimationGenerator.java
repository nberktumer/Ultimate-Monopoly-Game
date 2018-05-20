package ui;

import java.util.Random;

public class SpringAnimationGenerator implements Animatable {
	private int panelwidth;
	private int panelheight;
	private double x;
	private double y;
	private double maxwidth;
	private double maxheight;
	private double currentwidth;
	private double currentheight;
	private String emoji;

	private double velocity;
	private double acceleration;
	private int direction;
	private boolean changeAccDir = true;
	private boolean didEnd = false;
	
	private static final double initialVelocity = 0.1;
	private static final double initialAcceleration = 0.1;

	@Override
	public void initAnimation(int x, int y, double maxwidth, double maxheight, int panelwidth, int panelheight,
			String emoji) {
		Random r = new Random();
		this.panelwidth = panelwidth;
		this.panelheight = panelheight;
		this.currentwidth = maxwidth;
		this.currentheight = maxheight;
		this.maxwidth = maxwidth;
		this.maxheight = maxheight;
		this.emoji = emoji;
		this.direction = r.nextBoolean() ? 1 : -1;
		this.x = direction == 1 ? 0 : panelwidth;
		this.y = r.nextInt((int) (panelheight - maxwidth) > 0 ? (int) (panelheight - maxwidth) : 1);
		this.velocity = direction == 1 ? initialVelocity : -initialVelocity;
		this.acceleration = direction == 1 ? initialAcceleration : -initialAcceleration;
	}
	
	@Override
	public void nextFrame() {
		if (x >= ((panelwidth - maxwidth) / 2) - velocity * direction && x <= ((panelwidth - maxwidth) / 2) + velocity * direction) {
			if (changeAccDir) {
				acceleration *= -2;
				changeAccDir = false;
			}
			if(velocity * direction <= Math.abs(acceleration)) {
				acceleration = 0;
				velocity = 0;
				didEnd = true;
			}
		}
		
		if(didEnd) {
			currentwidth -= maxwidth / 100;
			currentheight -= maxheight / 100;
			return;
		}
		
		if (direction == 1 && velocity < 0 && x > (panelwidth - maxwidth) / 2) {
			direction = -1;
			changeAccDir = true;
		} else if (direction == -1 && velocity > 0 && x < (panelwidth - maxwidth) / 2) {
			direction = 1;
			changeAccDir = true;
		}
		x += velocity;
		velocity += acceleration;
	}

	public boolean framesAvailable() {
		return currentwidth > 0 || currentheight > 0;
	}

	public int getWidth() {
		return (int) currentwidth;
	}

	public int getHeight() {
		return (int) currentheight;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	@Override
	public String getEmojiName() {
		return emoji;
	}

}
