package domain.square;

import domain.Logger;
import domain.MonopolyGameController;
import domain.Player;

public class ReverseDirectionSquare extends Square {

	public ReverseDirectionSquare(String name) {
		super(name);
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {

		Player currentPlayer = monopolyGame.getCurrentPlayer();
		currentPlayer.setDirection(-currentPlayer.getDirection());
		String direction = currentPlayer.getDirection() == 1 ? "clockwise" : "counterclockwise";
		Logger.getInstance().log(monopolyGame.getCurrentPlayer().getName()
				+ " has landed on reverse direction and is now going " + direction);

		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
