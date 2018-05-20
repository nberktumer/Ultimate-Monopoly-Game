package ui;

import java.util.Random;

public class ZoomInOutAnimationGenerator implements Animatable {
	private int panelwidth;
	private int panelheight;
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double gravity = -0.4;
	private double maxwidth;
	private double maxheight;
	private double currentwidth;
	private double currentheight;
	private boolean increasing;
	private String emoji;

	public ZoomInOutAnimationGenerator() {
	}

	public void initAnimation(int x, int y, double maxwidth, double maxheight, int panelwidth, int panelheight,
			String emoji) {
		Random r = new Random();
		this.x = r.nextInt((int) (panelwidth - (maxwidth / 2) - maxwidth / 2)) + maxwidth / 2;
		this.y = r.nextInt((int) (panelheight - (maxheight / 2) - maxheight / 2)) + maxheight / 2;
		this.maxwidth = maxwidth;
		this.maxheight = maxheight;
		this.panelwidth = panelwidth;
		this.panelheight = panelheight;
		this.increasing = true;
		this.currentwidth = 1;
		this.currentheight = 1;
		this.emoji = emoji;

	}

	public void nextFrame() {
		if (increasing) {
			currentwidth += maxwidth / 33;
			currentheight += maxheight / 33;

			if (currentwidth >= maxwidth || currentheight >= maxheight)
				increasing = false;
		} else {
			currentwidth -= maxwidth / 33;
			currentheight -= maxheight / 33;
		}
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
		return (int) x - (getWidth() / 2);
	}

	public int getY() {
		return (int) y - (getHeight() / 2);
	}

	@Override
	public String getEmojiName() {
		return emoji;
	}
}
