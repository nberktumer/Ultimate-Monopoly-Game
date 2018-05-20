package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class IncomeTaxSquare extends Square {

	public IncomeTaxSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {

		Player currentPlayer = monopolyGame.getCurrentPlayer();
		int temp = currentPlayer.getBalance();
		if (temp < 2000) {
			currentPlayer.updateBalanceDelta((int) (-temp * 0.1));
		} else {
			currentPlayer.updateBalanceDelta(-200);
		}
		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
