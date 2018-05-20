package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class RailRoadSquare extends Square {

	private int price;
	private int rent1;
	private int rent2;
	private int rent3;
	private int rent4;

	private Player owner;

	public RailRoadSquare(String name, Player owner) {
		super(name);
		this.price = 200;
		this.rent1 = 25;
		this.rent2 = 50;
		this.rent3 = 100;
		this.rent4 = 200;
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public RailRoadSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {
		// TODO Auto-generated method stub
		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPrice() {
		return price;
	}

	public int getRent1() {
		return rent1;
	}

	public int getRent2() {
		return rent2;
	}

	public int getRent3() {
		return rent3;
	}

	public int getRent4() {
		return rent4;
	}
}
