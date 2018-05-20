package domain;

import java.awt.image.BufferedImage;

import domain.square.Square;

/**
 * @overview This class represents avatar of the Player, similar to game pieces
 *           in monopoly which players use to represent themselves on the board.
 * @author Null
 * 
 */

public class Piece {

	private Square location;
	private BufferedImage image;

	public Piece(Square location, BufferedImage image) {
		super();
		this.location = location;
		this.image = image;
	}

	/**
	 * @requires -
	 * @modifies -
	 * @effects returns the current location of the object
	 */
	public Square getLocation() {
		return location;
	}

	/**
	 * @requires location is not null
	 * @modifies location
	 * @effects changes the current location of the object to location parameter
	 */
	public void setLocation(Square location) {
		this.location = location;
	}

	/**
	 * @requires -
	 * @modifies -
	 * @effects returns the image of the object
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @requires image is not null
	 * @modifies image
	 * @effects changes the current image of the object to image parameter
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean repOk() {
		return !(location == null || image == null);
	}

	@Override
	public String toString() {
		return "Piece [location=" + location + ", image=" + image + "]";
	}

}
