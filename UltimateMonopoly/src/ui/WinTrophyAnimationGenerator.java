package ui;

import java.util.Random;

public class WinTrophyAnimationGenerator implements Animatable {
	private int panelwidth;
	private int panelheight;
	private double x;
	private double y;
	private double maxwidth;
	private double maxheight;
	private double defaultWidth;
	private double defaultHeight;
	private double currentwidth;
	private double currentheight;
	private boolean increasing;
	private String emojiName;
	private int labelSize;
	
	private int changeLabelSizeCounter = 0;
	
	private static final double xScale = 1.2;
	private static final double yScale = 1.2;

	public void initAnimation(int x, int y, double maxwidth, double maxheight, int panelwidth, int panelheight,
			String emoji) {
		this.x = x;
		this.y = y;
		this.maxwidth = maxwidth * xScale;
		this.maxheight = maxheight * yScale;
		this.panelwidth = panelwidth;
		this.panelheight = panelheight;
		this.increasing = true;
		this.currentwidth = maxwidth;
		this.currentheight = maxheight;
		this.defaultHeight = maxheight;
		this.defaultWidth = maxwidth;
		this.emojiName = emoji;
		this.labelSize = 20;
	}

	public void nextFrame() {
		if (increasing) {
			currentwidth += 1;
			currentheight += 1;
			labelSize += 1;
			
			if (currentwidth >= maxwidth || currentheight >= maxheight)
				increasing = false;
		} else {
			currentwidth -= 1;
			currentheight -= 1;
			labelSize -= 1;
			
			if (currentwidth <= defaultWidth || currentheight <= defaultHeight)
				increasing = true;
		}
	}

	public boolean framesAvailable() {
		return true;
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
		return emojiName;
	}
	
	public int getLabelSize() {
		return labelSize;
	}
}
