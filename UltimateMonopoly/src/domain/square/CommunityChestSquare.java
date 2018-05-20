package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class CommunityChestSquare extends Square {

	public CommunityChestSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {
		monopolyGame.notifyObservers("game", "highlightCommunityChestCards", null);
		monopolyGame.notifyObservers("game", "stateChange", "waitForActionState");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
