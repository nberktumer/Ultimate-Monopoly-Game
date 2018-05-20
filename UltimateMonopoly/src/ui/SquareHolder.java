package ui;

import java.awt.Image;

import javax.swing.JComponent;

public class SquareHolder {
	protected int posX;
	protected int posY;
	protected int width;
	protected int height;
	protected Image img;

	public SquareHolder(int x, int y, int w, int h, Image i) {
		posX = x;
		posY = y;
		width = w;
		height = h;
		img = i;
	}

}
