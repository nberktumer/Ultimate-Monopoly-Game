package domain;

import java.util.ArrayList;

import domain.square.Square;
import domain.square.TitleDeedSquare;

public class AuctionProcess {
	private int currentHighestBet = 0;
	private int currentBetIndex = 0;
	private int currentPlayerIndex = 0;

	private ArrayList<Player> players;
	private Square auctionedTitle;

	private String auctionWinner = "-";

	public AuctionProcess(ArrayList<Player> players, TitleDeedSquare auctionedTitle) {
		this.players = players;
		this.auctionedTitle = auctionedTitle;

	}

	public void nextPlayer() {
		Logger.getInstance().log(players.get(currentPlayerIndex).getName() + " passed!");
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}

	public boolean shouldTerminateAuction() {
		return currentPlayerIndex == currentBetIndex;
	}

	public boolean validateBetOffer(int offer) {
		int balance = players.get(currentPlayerIndex).getBalance();

		if (offer <= currentHighestBet || balance < offer) {
			return false;
		}
		return true;
	}

	public void makeNewOffer(int offer) {
		currentHighestBet = offer;
		currentBetIndex = currentPlayerIndex;
		auctionWinner = players.get(currentPlayerIndex).getName();

		Logger.getInstance().log(auctionWinner + " bid $" + offer + "!");
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

	}

	public String getFinalWinnerName() {
		return auctionWinner;
	}

	public int getFinalWinnerBid() {
		return currentHighestBet;
	}

	public int getCurrentPlayerIndez() {
		return currentPlayerIndex;
	}

}
