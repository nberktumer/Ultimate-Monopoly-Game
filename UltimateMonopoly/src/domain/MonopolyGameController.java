package domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import domain.card.Card;
import domain.square.Passable;
import domain.square.RailRoadSquare;
import domain.square.Square;
import domain.square.TitleDeedSquare;
import ui.PropertyInformation;
import ui.SoundManager;

public class MonopolyGameController implements Observable {
	List<Player> players = new ArrayList<>();
	Board board;
	Cup cup;
	Player currentPlayer;
	String gamename;
	AuctionProcess auctionProcess;
	BankruptcyProcess bankruptcyProcess;
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	private String nextMoveType = "regular";
	private boolean allowEnd = true;

	public MonopolyGameController() {
		board = new Board();
		cup = new Cup();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Board getBoard() {
		return board;
	}

	public Cup getCup() {
		return cup;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public String getGamename() {
		return gamename;
	}

	public void setBankruptcyProcess(BankruptcyProcess bankruptcyProcess) {
		this.bankruptcyProcess = bankruptcyProcess;
	}

	public BankruptcyProcess getBankruptcyProcess() {
		return bankruptcyProcess;
	}

	@Override
	public void notifyObservers(String key, String action, Object obj) {
		for (Observer o : observers) {
			o.update(key, action, obj);
		}
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	public void saveGame() {
		SaveLoadManager.saveGame(this);
	}

	public void loadGame(String fileName) {
		SaveLoadManager.loadGame(fileName, this);
	}

	public void quitGame() {
		System.exit(0);
	}

	public void nextTurn() {
		if (currentPlayer.isInJail()) {
			currentPlayer.decrementJailTurnLeft();

		} else if (nextMoveType == "bus") {
			int i = board.getNextCardSquare(currentPlayer.getPiece().getLocation(), currentPlayer.getDirection());
			if (i > -1)
				teleport(i);
			nextMoveType = "regular";
			return;
		} else if (nextMoveType == "mrmonopoly") {
			int i = board.getNextUnowned(currentPlayer.getPiece().getLocation(), currentPlayer.getDirection());
			if (i > -1)
				teleport(i);
			nextMoveType = "regular";
			return;
		}
		Logger.getInstance().log(currentPlayer.getName() + " has ended his turn!");
		while (true) {
			currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
			if (!currentPlayer.isBankrupt())
				break;
		}

		Logger.getInstance().log(currentPlayer.getName() + "'s turn now!");

		notifyObservers("game", "highlightSquare", board.getSquares().indexOf(currentPlayer.getPiece().getLocation()));

		if (currentPlayer.isInJail()) {
			notifyObservers("game", "stateChange", "disableAll");
			notifyObservers("game", "inJail", null);
			Logger.getInstance().log(
					currentPlayer.getName() + " is still in jail for " + currentPlayer.getJailTurnLeft() + " turns!");
			return;
		}

		notifyObservers("game", "stateChange", "outOfJailState");
		notifyObservers("game", "outJail", null);
	}

	public Cup rollDice() {
		cup.flush();
		cup.roll();
		return cup;
	}

	public void purchaseSquare() {
		boolean bought = buyProperty(currentPlayer.getPiece().getLocation());
		if (bought) {
			notifyObservers("game", "stateChange", "disableTransaction");

		} else {
			notifyObservers("game", "outOfBalanceMessage", null);
		}
	}

	public void auctionSquare() {
		startAuction(board.getSquares().indexOf(currentPlayer.getPiece().getLocation()));
		notifyObservers("game", "stateChange", "disableTransaction");
	}

	public void handleJail() {
		Cup c = rollDice();
		List<DieValue> dice = c.getResults();

		if (dice.get(0).getValue() == dice.get(1).getValue()) {
			currentPlayer.setInJail(false);
			Logger.getInstance().log(currentPlayer.getName() + " is out of jail.");
			notifyObservers("game", "stateChange", "startOfTurn");
		} else {
			notifyObservers("game", "stateChange", "endOfTurn");
		}
		notifyObservers("game", "outJail", null);
	}

	public void rollDiceAndMove() {
		if (currentPlayer.isInJail()) {
			handleJail();
			return;
		}

		Cup c = rollDice();
		List<DieValue> dice = c.getResults();

		/*
		 * Triple Dice Action
		 */
		if (dice.get(0).getValue() == dice.get(1).getValue() && dice.get(0).getValue() == dice.get(2).getValue()) {
			c.setNumberOfDuplicates(0);
			ArrayList<Integer> indices = new ArrayList<Integer>();

			for (int i = 0; i < board.getSquares().size(); i++) {
				indices.add(i);
			}
			notifyObservers("game", "stateChange", "disableAll");
			notifyObservers("game", "highlightSquaresTeleport", indices);
			Logger.getInstance()
					.log(currentPlayer.getName() + " has rolled TRIPLES!, he can move to any position he wants!");
			return;
		}

		/*
		 * Double Dice Action
		 */
		if (dice.get(0).getValue() == dice.get(1).getValue()) {
			if (c.getNumberOfDuplicates() == 2) {
				Logger.getInstance()
						.log(currentPlayer.getName() + " has rolled doubles three times and is now in jail!");
				teleport(14);
				c.setNumberOfDuplicates(0);
				return;
			}
			c.incrementNumberOfDuplicates();
			Logger.getInstance().log(currentPlayer.getName() + " has rolled doubles, he can roll and move again! ("
					+ c.getNumberOfDuplicates() + ")");
			notifyObservers("game", "stateChange", "doubleState");

		} else {
			c.setNumberOfDuplicates(0);
		}

		if (dice.contains(DieValue.MRMONOPOLY)) {
			int total = dice.get(0).getValue() + dice.get(1).getValue();
			notifyObservers("game", "stateChange", "mrMonopolyState");
			move(total);
			nextMoveType = "mrmonopoly";
			Logger.getInstance().log(currentPlayer.getName()
					+ " has rolled MR. MONOPOLY and will be teleported to the nearest unowned title deed at the end of his turn and play again!");
		} else if (dice.contains(DieValue.BUS)) {
			int total = dice.get(0).getValue() + dice.get(1).getValue();
			notifyObservers("game", "stateChange", "busState");
			move(total);
			nextMoveType = "bus";
			Logger.getInstance().log(currentPlayer.getName()
					+ " has rolled BUS ICON and will be teleported to the nearest community/chest card square at the end of his turn!");
		} else {
			int total = dice.get(0).getValue() + dice.get(1).getValue() + dice.get(2).getValue();
			move(total);
			nextMoveType = "regular";
			Logger.getInstance().log(currentPlayer.getName() + " has rolled a total of " + total + " and moved!");
		}

	}

	public void move(int total) {
		int playerLocationIndex = board.getSquares().indexOf(currentPlayer.getPiece().getLocation());
		int nextPosition;
		if (board.getSquares().get(playerLocationIndex) instanceof RailRoadSquare) {
			if (total % 2 == 0) {
				nextPosition = MovementHandle.getInstance().handleRailRoad(playerLocationIndex,
						currentPlayer.getDirection());
			} else {
				nextPosition = MovementHandle.getInstance().handleNextSquare(playerLocationIndex,
						currentPlayer.getDirection());
			}
		} else {
			nextPosition = MovementHandle.getInstance().handleNextSquare(playerLocationIndex,
					currentPlayer.getDirection());
		}

		for (int i = 1; i <= total; i++) {
			Square newLocation = board.getSquares().get(nextPosition);
			currentPlayer.getPiece().setLocation(newLocation);

			if (newLocation instanceof Passable && i != total) {
				((Passable) newLocation).passedBy(this, total);
			}

			if (newLocation instanceof RailRoadSquare && total % 2 == 0) {
				nextPosition = MovementHandle.getInstance().handleRailRoad(nextPosition, currentPlayer.getDirection());
			} else {
				nextPosition = MovementHandle.getInstance().handleNextSquare(nextPosition,
						currentPlayer.getDirection());
			}

		}

		Square finalSquare = currentPlayer.getPiece().getLocation();
		finalSquare.landedOn(this);
		notifyObservers("game", "highlightSquare", board.getSquares().indexOf(currentPlayer.getPiece().getLocation()));

	}

	public void teleportPlayer(int teleportIndex, int i) {

		int nextPosition = teleportIndex;

		Square finalSquare = board.getSquares().get(nextPosition);
		players.get(i).getPiece().setLocation(finalSquare);
	}

	public void teleport(int teleportIndex) {
		notifyObservers("game", "stateChange", "disableAll");

		int nextPosition = teleportIndex;

		Square finalSquare = board.getSquares().get(nextPosition);
		currentPlayer.getPiece().setLocation(finalSquare);

		finalSquare.landedOn(this);

		notifyObservers("game", "highlightSquare", board.getSquares().indexOf(currentPlayer.getPiece().getLocation()));
	}

	public boolean buyProperty(Square sq) {
		return board.buyProperty(currentPlayer, sq);
	}

	public boolean upgradeTitleDeed(String sqName) {
		return board.upgradeTitleDeed(currentPlayer, (TitleDeedSquare) board.getSquareByName(sqName));
	}

	public void downgradeAllPropertiesOfColor(int i) {
		board.downgradeAllPropertiesOfColor(i);
		notifyObservers("game", "stateChange", "endOfTurn");
	}

	public void initNewGamePlayers(ArrayList<String> usernames, String newGameName) {
		SaveLoadManager.initNewGamePlayer(usernames, newGameName, this);
	}

	public void reset() {
		players.clear();
		cup = new Cup();
		board = new Board();
	}

	public boolean isDuplicateGameName(String gameName) {
		return SaveLoadManager.isDuplicateGameName(gameName);
	}

	public void startAuction(Integer index) {

		HashMap<String, Object> params = new HashMap<>();

		ArrayList<String> playerInformation = getPlayerInformation(false);
		for (int i = playerInformation.size() - 1; i >= 0; i--) {
			if (playerInformation.get(i).split(" ")[3].equals("true"))
				playerInformation.remove(i);
		}
		params.put("players", playerInformation);
		params.put("square", board.getSquares().get(index).getName());

		ArrayList<Player> nonJailedPlayers = new ArrayList<Player>();

		for (Player p : players) {
			if (!p.isInJail() && !p.isBankrupt())
				nonJailedPlayers.add(p);
		}

		auctionProcess = new AuctionProcess(nonJailedPlayers, (TitleDeedSquare) board.getSquares().get(index));
		notifyObservers("game", "initAuction", params);
		Logger.getInstance().log("Auction for " + board.getSquares().get(index).getName() + " has begun!");
	}

	public void terminateAuction(String titleDeedName) {
		notifyObservers("game", "stateChange", "endOfTurn");
		String winningPlayerName = auctionProcess.getFinalWinnerName();
		int winningBet = auctionProcess.getFinalWinnerBid();

		if (winningPlayerName.equals("-")) {
			Logger.getInstance().log("Auction terminated with no one bidding.");
			return;
		}
		Player p = getPlayerByName(winningPlayerName);
		TitleDeedSquare sq = (TitleDeedSquare) board.getSquareByName(titleDeedName);
		p.updateBalanceDelta(-winningBet);
		sq.setOwner(p);

		Logger.getInstance().log("Auction ended.");
		Logger.getInstance()
				.log(winningPlayerName + " has won the auction for " + sq.getName() + " with $" + winningBet + "!");

	}

	public boolean auctionValidateBetOffer(int offer) {
		boolean valid = auctionProcess.validateBetOffer(offer);
		if (!valid) {
			notifyObservers("game", "invalidBidMessage", null);
		}
		return valid;
	}

	public void auctionMakeNewHighestOffer(int offer) {
		auctionProcess.makeNewOffer(offer);
	}

	public void auctionNextPlayer() {
		auctionProcess.nextPlayer();
	}

	public boolean auctionShouldTerminate() {
		return auctionProcess.shouldTerminateAuction();
	}

	public String auctionGetHighestBetName() {
		return auctionProcess.getFinalWinnerName();
	}

	public int auctionGetHighestBid() {
		return auctionProcess.getFinalWinnerBid();
	}

	public int auctionGetCurrentPlayerIndex() {
		return auctionProcess.getCurrentPlayerIndez();
	}

	public void sellTitleDeed(String titleDeedName) {
		board.sellTitleDeed(titleDeedName);
	}

	public boolean downgradeTitleDeed(String titleDeedName) {
		return board.downgradeTitleDeed(titleDeedName);
	}

	public String bankruptedPlayerCurrentMoney() {
		return "" + currentPlayer.getBalance();
	}

	public void startBankruptcyProcess(int moneyOwed) {
		Logger.getInstance().log("Oops, " + currentPlayer.getName() + " is about to go bankrupt. Quick, sell!");
		notifyObservers("game", "startBankruptcy", moneyOwed);
	}

	public boolean bankruptcyCanDebtBePaid() {
		return bankruptcyProcess.canDebtBePaid();
	}

	public void terminateBankruptcyProcess() {
		bankruptcyProcess.terminateBankruptcyProcess();
	}

	public void bankruptPlayer(String playername) {
		Player p = getPlayerByName(playername);
		p.setBankrupt(true);
		Logger.getInstance().log(p.getName() + " has gone bankrupt failing to pay his debt!");
		if (gameEnded()) {
			nextTurn();
			PlayerObservable.getInstance().notifyObservers("MonopolyBot", "win", null);
			notifyObservers("game", "gameEnded", currentPlayer.getName());
			Logger.getInstance().log(currentPlayer.getName() + " HAS WON THE GAME!!!!");
		} else {
			nextTurn();
		}

	}

	public boolean gameEnded() {
		int remainingplayers = 0;

		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).isBankrupt())
				remainingplayers++;
		}

		return remainingplayers == 1;
	}

	public int currentPlayerIndex() {
		return board.getSquares().indexOf(currentPlayer.getPiece().getLocation());
	}

	public Player getPlayerByName(String name) {
		for (int i = 0; i < players.size(); i++) {
			if (name.equals(players.get(i).getName())) {
				return players.get(i);
			}
		}
		return null;
	}

	public int getCurrentPlayerIndex() {
		return players.indexOf(currentPlayer);
	}

	public ArrayList<BufferedImage> getPieces() {
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		for (Player p : players) {
			images.add(p.getPiece().getImage());
		}

		return images;
	}

	public ArrayList<Integer> getIndices() {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for (Player p : players) {
			indices.add(board.getSquares().indexOf(p.getPiece().getLocation()));
		}

		return indices;

	}

	public int getTitleDeedPayment(String titleDeedName) {
		TitleDeedSquare deed = (TitleDeedSquare) board.getSquareByName(titleDeedName);
		return deed.getDowngradePayment();
	}

	public ArrayList<String> getPlayerInformation(boolean allowJailedPlayers) {
		ArrayList<String> playerInformation = new ArrayList<String>();

		for (int i = 0; i < players.size(); i++) {
			String info = "";
			if (currentPlayer == players.get(i)) {
				info += "true ";
			} else {
				info += "false ";
			}

			info += players.get(i).getName() + " ";
			info += players.get(i).getBalance() + " ";
			info += players.get(i).isBankrupt();

			if (allowJailedPlayers) {
				playerInformation.add(info);
			} else {
				if (!players.get(i).isInJail())
					playerInformation.add(info);
			}
		}

		return playerInformation;
	}

	public ArrayList<PropertyInformation> getCurrentPlayerPropertiesInformation() {
		ArrayList<PropertyInformation> propertyInfo = new ArrayList<>();
		for (Square s : board.getSquares()) {
			if (s instanceof TitleDeedSquare) {

				TitleDeedSquare ts = (TitleDeedSquare) s;
				if (ts.getOwner() == currentPlayer) {
					PropertyInformation pInfo = new PropertyInformation("TitleDeedSquare", ts.getName(),
							ts.getUpgradeCost());
					pInfo.setHouse(ts.getNumberOfHouses());
					pInfo.setHotel(ts.getNumberOfHotels());
					pInfo.setSkyscraper(ts.getNumberOfSkyScrapers());
					pInfo.setCurrentRentPrice(ts.getRent());
					pInfo.setSellamount(ts.getSellAmount());
					pInfo.setDowngradeamount(ts.getDowngradePayment());
					propertyInfo.add(pInfo);
				}
			}
		}

		return propertyInfo;

	}

	public ArrayList<String> getDiceResultsAsString() {
		ArrayList<String> diceResults = new ArrayList<String>();
		if (!cup.getResults().isEmpty()) {
			for (int i = 0; i < cup.getResults().size(); i++) {
				diceResults.add(cup.getResults().get(i).toString());
			}
		} else {
			for (int i = 0; i < 3; i++) {
				diceResults.add("NONE");
			}
		}

		return diceResults;

	}

	public String getRandomCard(String cardType) {
		ArrayList<Card> cardDeck = board.getCards().get(cardType);
		Collections.shuffle(cardDeck);
		return cardDeck.get(0).getImgName();
	}

	public void playCard(String imgName) {
		notifyObservers("game", "afterCardPlayed", null);
		notifyObservers("game", "stateChange", "endOfTurn");

		for (String cardType : board.getCards().keySet()) {
			ArrayList<Card> cards = board.getCards().get(cardType);
			for (Card c : cards) {
				if (c.getImgName().equals(imgName)) {
					c.useCard(this);
					Logger.getInstance().log(currentPlayer.getName() + " has played \"" + c.getTitle() + "\" card.");
					return;
				}
			}
		}
	}

	public boolean tradeWithYourself(String selectedItem) {
		return selectedItem.equals(currentPlayer.getName());
	}

	public boolean attemptToTrade(String titleDeedName, String selectedItem, String tradeAmount) {
		Player playerBuying = getPlayerByName(selectedItem);
		int tradePrice = validateTradeAmount(tradeAmount, playerBuying);
		if (tradePrice == -1)
			return false;

		Player playerSelling = currentPlayer;
		Square s = board.getSquareByName(titleDeedName);
		TitleDeedSquare td = (TitleDeedSquare) s;
		td.setOwner(playerBuying);
		playerBuying.updateBalanceDelta(-tradePrice);
		currentPlayer.updateBalanceDelta(tradePrice);
		Logger.getInstance().log(playerSelling.getName() + " has sold " + td.getName() + " to " + playerBuying.getName()
				+ " for $" + tradePrice + "!");
		return true;
	}

	public int validateTradeAmount(String tradeAmount, Player playerBuying) {
		int amt = -1;

		try {
			amt = Integer.parseInt(tradeAmount);
			if (playerBuying.getBalance() < amt) {
				amt = -1;
			}

		} catch (NumberFormatException ex) {

		}

		return amt;

	}

	public boolean playerIsBankrupt(String selectedPlayer) {
		return getPlayerByName(selectedPlayer).isBankrupt();
	}
}
