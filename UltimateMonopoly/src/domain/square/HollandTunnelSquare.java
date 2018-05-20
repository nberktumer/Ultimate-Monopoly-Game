package domain.square;

import domain.MonopolyGameController;
import domain.Player;

public class HollandTunnelSquare extends Square {
	
	public HollandTunnelSquare(String name) {
		super(name);
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {
		Player currentPlayer = monopolyGame.getCurrentPlayer();
		int index = monopolyGame.getBoard().getSquares().indexOf(currentPlayer.getPiece().getLocation());
		if(index == 42) {
			monopolyGame.teleportPlayer(102, monopolyGame.getPlayers().indexOf(currentPlayer));
		} else if(index == 102) {
			monopolyGame.teleportPlayer(42, monopolyGame.getPlayers().indexOf(currentPlayer));
		}
		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");
	}

	@Override
	public String toString() {
		return null;
	}

}
