package domain.square;

import java.util.ArrayList;

import domain.Logger;
import domain.MonopolyGameController;
import domain.Player;

public class AuctionSquare extends Square {

	public AuctionSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(MonopolyGameController monopolyGame) {

		Player currentPlayer = monopolyGame.getCurrentPlayer();
		Logger.getInstance().log(currentPlayer.getName()
				+ " has landed on Auction Square and can select an unowned title deed to auction!");

		ArrayList<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < monopolyGame.getBoard().getSquares().size(); i++) {
			Square s = monopolyGame.getBoard().getSquares().get(i);

			if (s instanceof TitleDeedSquare) {
				if (((TitleDeedSquare) s).getOwner() == null) {
					indices.add(i);

				}
			}
		}
		monopolyGame.notifyObservers("game", "highlightSquaresAuction", indices);
		monopolyGame.notifyObservers("game", "stateChange", "waitForActionState");

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
