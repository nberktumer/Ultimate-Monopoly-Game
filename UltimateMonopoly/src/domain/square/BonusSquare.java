package domain.square;

import domain.MonopolyGameController;
import domain.MovementHandle;
import domain.Player;

public class BonusSquare extends Square implements Passable {

	public BonusSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {
		monopolyGame.getCurrentPlayer().updateBalanceDelta(300);
		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void passedBy(MonopolyGameController monopolyGame, int roll) {
		monopolyGame.getCurrentPlayer().updateBalanceDelta(250);
	}

}
