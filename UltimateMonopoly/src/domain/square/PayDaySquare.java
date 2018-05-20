package domain.square;

import domain.Logger;
import domain.MonopolyGameController;
import domain.MovementHandle;
import domain.Player;

public class PayDaySquare extends Square implements Passable {

	private String description;

	public PayDaySquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public PayDaySquare(String name, String description) {
		super(name);
		this.description = description;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {
		monopolyGame.getCurrentPlayer().updateBalanceDelta(400);
		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void passedBy(MonopolyGameController monopolyGame, int roll) {
		if (roll % 2 == 0) {
			monopolyGame.getCurrentPlayer().updateBalanceDelta(400);
			Logger.getInstance().log("Player received 400 because of PayDay Square!");
		} else {
			monopolyGame.getCurrentPlayer().updateBalanceDelta(300);
			Logger.getInstance().log("Player received 300 because of PayDay Square!");
		}

	}

}
