package ui;

import javax.swing.JComponent;

public class RectangleComponent extends JComponent {

	private SquareHolder squareHolder;

	public RectangleComponent(SquareHolder squareHolder) {
		this.squareHolder = squareHolder;
		setSize(squareHolder.width, squareHolder.height);
	}

	public int getX() {
		return squareHolder.posX;
	}

	public int getY() {
		return squareHolder.posY;
	}

}
