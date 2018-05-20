package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class JailSquare extends Square {

	// TODO: Keep Jailed People

	public JailSquare(String name, String description) {
		super(name);
	}

	public JailSquare(String name) {
		super(name);
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {

		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName() + " ";
	}

}
