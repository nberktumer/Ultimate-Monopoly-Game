package domain.square;

import domain.MonopolyGameController;

public abstract class Square {

	private String name;

	public Square(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract void landedOn(MonopolyGameController monopolyGame);

	public abstract String toString();

}
