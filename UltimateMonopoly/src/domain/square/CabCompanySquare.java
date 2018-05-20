package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class CabCompanySquare extends Square {

	int price;
	int cab1Price;
	int cab2Price;
	int cab3Price;
	int cab4Price;
	private Player owner;

	public CabCompanySquare(String name, Player owner) {
		super(name);
		this.price = 300;
		this.cab1Price = 30;
		this.cab2Price = 60;
		this.cab3Price = 120;
		this.cab4Price = 240;
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
