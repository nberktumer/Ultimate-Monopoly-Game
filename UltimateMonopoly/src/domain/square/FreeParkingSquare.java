package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class FreeParkingSquare extends Square {

	public FreeParkingSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
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
