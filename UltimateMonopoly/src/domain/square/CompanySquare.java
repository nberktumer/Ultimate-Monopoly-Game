package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class CompanySquare extends Square {

	private int price;
	private int timesDice1;
	private int timesDice2;
	private int timesDice3;
	private int timesDice4;
	private int timesDice5;
	private int timesDice6;
	private int timesDice7;
	private int timesDice8;
	private Player owner;

	public CompanySquare(String name, Player owner) {
		super(name);
		this.price = 150;
		this.timesDice1 = 4;
		this.timesDice2 = 10;
		this.timesDice3 = 20;
		this.timesDice4 = 40;
		this.timesDice5 = 80;
		this.timesDice6 = 100;
		this.timesDice7 = 120;
		this.timesDice8 = 150;
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {

		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
