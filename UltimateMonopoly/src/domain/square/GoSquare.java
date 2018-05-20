package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class GoSquare extends Square implements Passable {

	public GoSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {
		Player currentPlayer = monopolyGame.getCurrentPlayer();
		currentPlayer.updateBalanceDelta(200);

		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void passedBy(MonopolyGameController monopolyGame, int roll) {
		landedOn(monopolyGame);
	}

}
