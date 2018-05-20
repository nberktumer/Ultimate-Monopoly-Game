package domain.square;

import java.util.ArrayList;

import domain.Logger;
import domain.MonopolyGameController;
import domain.Player;

public class GoToJailSquare extends Square {

	public GoToJailSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {
		Player currentPlayer = monopolyGame.getCurrentPlayer();
		Logger.getInstance().log(currentPlayer.getName() + " has been sent to jail and is now in jail!");
		currentPlayer.setInJail(true);
		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");
		monopolyGame.teleport(86);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName() + " ";
	}
}
