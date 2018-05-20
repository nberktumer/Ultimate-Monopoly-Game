package domain.square;

import java.util.ArrayList;

import domain.MonopolyGameController;
import domain.Player;

public class SubwaySquare extends Square {

	public SubwaySquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < monopolyGame.getBoard().getSquares().size(); i++) {
			indices.add(i);
		}

		monopolyGame.notifyObservers("game", "highlightSquaresTeleport", indices);
		monopolyGame.notifyObservers("game", "stateChange", "waitForActionState");

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
