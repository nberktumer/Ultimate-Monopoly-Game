package domain.card;

import domain.MonopolyGameController;

public abstract class Card {
	String title;
	String type;
	boolean immediate;
	String imgName;
	
	public Card(String title, String type, boolean immediate, String imgName) {
		this.title = title;
		this.type = type;
		this.immediate = immediate;
		this.imgName = imgName;
	}


	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public String getImgName() {
		return imgName;
	}

	public abstract void useCard(MonopolyGameController controller);
}
