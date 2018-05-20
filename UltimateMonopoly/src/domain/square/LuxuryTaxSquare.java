package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class LuxuryTaxSquare extends Square {

	private int paymentAmount;

	public LuxuryTaxSquare(String name) {
		super(name);
		this.paymentAmount = 75;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {

		Player currentPlayer = monopolyGame.getCurrentPlayer();
		currentPlayer.updateBalanceDelta(-75);
		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
