package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class ChanceSquare extends Square {

	public ChanceSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {
		monopolyGame.notifyObservers("game", "highlightChanceCards", null);
		monopolyGame.notifyObservers("game", "stateChange", "waitForActionState");

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
