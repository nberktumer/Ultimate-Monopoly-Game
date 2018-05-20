package ui;

public interface Animatable {
	public void initAnimation(int x, int y, double maxwidth, double maxheight, int panelwidth, int panelheight,
			String emoji);

	public void nextFrame();

	public boolean framesAvailable();

	public int getWidth();

	public int getHeight();

	public int getX();

	public int getY();

	public String getEmojiName();
}
