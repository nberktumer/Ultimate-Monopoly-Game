package ui;

import java.util.Random;

public class BouncyAnimationGenerator implements Animatable {
	private int panelwidth;
	private int panelheight;
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double gravity;
	private double maxwidth;
	private double maxheight;
	private double currentwidth;
	private double currentheight;
	private boolean increasing;
	private String emoji;
	private int nextframecounter = 0;

	public BouncyAnimationGenerator() {
	}

	public void initAnimation(int x, int y, double maxwidth, double maxheight, int panelwidth, int panelheight,
			String emoji) {
		this.x = x;
		this.y = y;
		this.panelwidth = panelwidth;
		this.panelheight = panelheight;
		this.currentwidth = maxwidth;
		this.currentheight = maxheight;
		Random r = new Random();
		this.y = r.nextInt(panelheight / 2 + 1);
		this.gravity = -(0.3 + (0.5 - 0.3) * r.nextDouble());
		this.dx = 1.5 + (2.5 - 1.5) * r.nextDouble();
		this.dy = 0;
		this.emoji = emoji;

	}

	public void nextFrame() {
		dy -= gravity;
		x += dx;
		y += dy;
		nextframecounter++;
		if (y + currentheight >= panelheight && nextframecounter > 10) {
			dy = dy * 0.73;
			dy = -dy;
			nextframecounter = 0;
			if (Math.abs(dy) < 3) {
				dy = 0;
				gravity = 0;
			}
		}
	}

	public boolean framesAvailable() {
		return !(x > panelwidth);
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
