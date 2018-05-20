package domain;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import domain.card.Card;
import domain.square.ChanceSquare;
import domain.square.CommunityChestSquare;
import domain.square.Square;
import domain.square.TitleDeedSquare;

/**
 * @overview This class is the container for the squares and the pieces similar
 *           to physical monopoly board.
 * @author Null
 * 
 */
public class Board {
	private ArrayList<Square> squares;
	private Map<String, ArrayList<Card>> cards;

	public Board() {
		squares = new ArrayList<Square>();
		cards = new HashMap<>();
		cards.put("Chance", new ArrayList<Card>());
		cards.put("CommunityChest", new ArrayList<Card>());
	}

	/**
	 * @requires squares is not null.
	 * @modifies squares
	 * @effects Adds sq to squares.
	 */
	public void addSquare(Square sq) {
		squares.add(sq);
	}

	public void addCard(Card card) {
		cards.get(card.getType()).add(card);
	}

	public Map<String, ArrayList<Card>> getCards() {
		return cards;
	}

	/**
	 * 
	 * @requires name is not null. squares is not null.
	 * @modifies -
	 * @effects Returns the particular square whose name is specified by given
	 *          string. Returns null if the given name is not found.
	 */
	public Square getSquareByName(String name) {
		for (Square s : squares) {
			if (s.getName().equals(name))
				return s;
		}
		return null;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns squares.
	 */
	public ArrayList<Square> getSquares() {
		return squares;
	}

	public void initSquares(BufferedReader rd, int lineNumber) {
		try {
			while (true) {
				String line = rd.readLine();
				if (line == null) {
					// rd.close();
					break;
				}
				Square s = SquareFactory.getInstance().createSquare(line, lineNumber);
				System.out.println(s);
				addSquare(s);

				lineNumber++;
			}
			squares.remove(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initCards() {
		Card c1 = CardFactory.getInstance().createCard("Mardi Gras");
		addCard(c1);

		Card c2 = CardFactory.getInstance().createCard("Advance to the Pay Corner");
		addCard(c2);

		Card c3 = CardFactory.getInstance().createCard("Advance to Illinois Ave");
		addCard(c3);

		Card c4 = CardFactory.getInstance().createCard("Hurricane Makes Landfall!");
		addCard(c4);

		Card c5 = CardFactory.getInstance().createCard("Bank Error in Your Favor!");
		addCard(c5);

		Card c6 = CardFactory.getInstance().createCard("Be Kind, Rewind");
		addCard(c6);

		Card c7 = CardFactory.getInstance().createCard("Go to Jail");
		addCard(c7);
	}

	/**
	 * 
	 * @requires currentPlayer and sq are not null.
	 * @modifies sq, currentPlayer
	 * @effects Returns true if sq is a title deed square and it's owner is null
	 *          and currentPlayer's balance is bigger than title deed's cost.
	 *          Otherwise, returns false and does nothing.
	 */
	public boolean buyProperty(Player currentPlayer, Square sq) {
		if (sq instanceof TitleDeedSquare) {

			int playerBalance = currentPlayer.getBalance();
			TitleDeedSquare property = (TitleDeedSquare) sq;
			int titleDeedCost = property.getPrice();

			if (playerBalance >= titleDeedCost && property.getOwner() == null) {
				currentPlayer.updateBalanceDelta(-titleDeedCost);
				property.setOwner(currentPlayer);
				Logger.getInstance().log(
						currentPlayer.getName() + " has bought " + property.getName() + " for $" + titleDeedCost + "!");
				return true;
			}

			Logger.getInstance().log(currentPlayer.getName() + " has failed to buy " + property.getName() + " for $"
					+ titleDeedCost + "!");
		}

		return false;
	}

	/**
	 * 
	 * @requires currentPlayer and sq are not null and currentPlayer owns sq.
	 * @modifies currentPlayer, sq
	 * @effects Returns true if upgrade is available and currentPlayer has the
	 *          relevent upgrade money. Otherwise returns false and does
	 *          nothing.
	 */
	public boolean upgradeTitleDeed(Player currentPlayer, TitleDeedSquare sq) {
		if (majorityOwner(currentPlayer, sq.getColor())) {
			if (sq.getNumberOfHouses() < 4 && sq.getNumberOfHotels() == 0 && sq.getNumberOfSkyScrapers() == 0
					&& currentPlayer.getBalance() >= sq.getHousePrice()) {
				currentPlayer.updateBalanceDelta(-sq.getHousePrice());
				sq.setNumberOfHouses(sq.getNumberOfHouses() + 1);
				Logger.getInstance().log(currentPlayer.getName() + " has built a house on " + sq.getName() + " for $"
						+ sq.getHousePrice());
				return true;
			} else if (sq.getNumberOfHouses() == 4 && sq.getNumberOfHotels() == 0 && sq.getNumberOfSkyScrapers() == 0
					&& currentPlayer.getBalance() >= sq.getHotelPrice()) {
				currentPlayer.updateBalanceDelta(-sq.getHotelPrice());
				sq.setNumberOfHouses(0);
				sq.setNumberOfHotels(sq.getNumberOfHotels() + 1);
				Logger.getInstance().log(currentPlayer.getName() + " has built a hotel on " + sq.getName() + " for $"
						+ sq.getHotelPrice());
				return true;
			} else if (sq.getNumberOfHouses() == 0 && sq.getNumberOfHotels() == 1 && sq.getNumberOfSkyScrapers() == 0
					&& currentPlayer.getBalance() >= sq.getSkyScraperPrice()
					&& skyscraperUpgradeAvailable(currentPlayer, sq.getColor())) {
				currentPlayer.updateBalanceDelta(-sq.getSkyScraperPrice());
				sq.setNumberOfHotels(0);
				sq.setNumberOfSkyScrapers(sq.getNumberOfSkyScrapers() + 1);
				Logger.getInstance().log(currentPlayer.getName() + " has built a skyscraper on " + sq.getName()
						+ " for $" + sq.getSkyScraperPrice());
				return true;
			}
		}

		return false;
	}

	private boolean skyscraperUpgradeAvailable(Player p, String color) {
		for (int i = 0; i < squares.size(); i++) {
			Square sq = squares.get(i);
			if (sq instanceof TitleDeedSquare) {
				TitleDeedSquare td = (TitleDeedSquare) sq;
				if (td.getColor().equals(color)) {
					if (!(td.getOwner() == p && (td.getNumberOfHotels() == 1) || td.getNumberOfSkyScrapers() == 1)) {
						return false;
					}
				}

			}

		}
		return true;
	}

	/**
	 * 
	 * @requires player is not null, color is valid color name.
	 * @modifies -
	 * @effects Returns true if player owns at least all but one square of the
	 *          given color. Otherwise, returns false.
	 */
	public boolean majorityOwner(Player player, String color) {
		int numOfColors = 0;
		int numPlayerOwns = 0;

		for (int i = 0; i < squares.size(); i++) {
			Square sq = squares.get(i);
			if (sq instanceof TitleDeedSquare && ((TitleDeedSquare) sq).getColor().equals(color)) {
				numOfColors++;
				if (((TitleDeedSquare) sq).getOwner() == player) {
					numPlayerOwns++;
				}
			}
		}

		// case where only 2 of the same color exist
		if (numOfColors == 2) {
			if (numPlayerOwns == 2) {
				return true;
			} else {
				return false;
			}
		}

		if (numOfColors - numPlayerOwns <= 1) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @requires sq and squares are not null and direction is either 1 or -1.
	 * @modifies -
	 * @effects Returns the next square whose owner is null. Otherwise, returns
	 *          null.
	 */
	public int getNextUnowned(Square sq, int direction) {
		int indexofSq = squares.indexOf(sq);
		int i = MovementHandle.getInstance().handleNextSquare(indexofSq, direction);
		i = i % 120;
		while (indexofSq != i) {

			Square nextSquare = squares.get(i);
			if (nextSquare instanceof TitleDeedSquare && ((TitleDeedSquare) nextSquare).getOwner() == null) {
				return i;
			}
			i = MovementHandle.getInstance().handleNextSquare(i, direction);
		}
		return getNextOwnedSquare(sq, direction);
	}

	public int getNextOwnedSquare(Square sq, int direction) {
		int indexofSq = squares.indexOf(sq);
		int i = MovementHandle.getInstance().handleNextSquare(indexofSq, direction);
		i = i % 120;
		while (indexofSq != i) {
			Square nextSquare = squares.get(i);
			if (nextSquare instanceof TitleDeedSquare && ((TitleDeedSquare) nextSquare).getOwner() != null) {
				return i;
			}
			i = MovementHandle.getInstance().handleNextSquare(i, direction);
		}
		return -1;
	}

	public int getNextCardSquare(Square sq, int direction) {
		int sqIndex = squares.indexOf(sq);
		int i = MovementHandle.getInstance().handleNextSquare(sqIndex, direction);
		i = i % 120;
		while (sqIndex != i) {
			Square nextSquare = squares.get(i);
			if (nextSquare instanceof CommunityChestSquare || nextSquare instanceof ChanceSquare) {
				return i;
			}
			i = MovementHandle.getInstance().handleNextSquare(i, direction);
		}
		return -1;
	}

	public boolean repOk() {
		if (squares != null) {
			if (squares.size() > 0)
				return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "Board [squares=" + squares + "]";
	}

	public int getPaymentMultiplier(TitleDeedSquare titleDeed) {
		int numberOfTitleDeedsOfColor = 0;
		int numberOfTitleDeedsOwned = 0;
		int multiplier = 1;

		if (titleDeed.getNumberOfHouses() == 0 && titleDeed.getNumberOfHotels() == 0
				&& titleDeed.getNumberOfSkyScrapers() == 0) {
			for (int i = 0; i < squares.size(); i++) {
				Square sq = squares.get(i);
				if (sq instanceof TitleDeedSquare) {
					TitleDeedSquare td = (TitleDeedSquare) sq;
					if (td.getColor().equals(titleDeed.getColor())) {
						numberOfTitleDeedsOfColor++;
						if (td.getOwner() == titleDeed.getOwner()) {
							numberOfTitleDeedsOwned++;
						}
					}
				}
			}
		}

		if (numberOfTitleDeedsOfColor == 2) {
			if (numberOfTitleDeedsOwned == 2) {
				multiplier = 3;
			}
		} else {
			if (numberOfTitleDeedsOwned == numberOfTitleDeedsOfColor - 1) {
				multiplier = 2;
			} else if (numberOfTitleDeedsOwned == numberOfTitleDeedsOfColor) {
				multiplier = 3;
			}
		}

		return multiplier;
	}

	public void downgradeAllPropertiesOfColor(int i) {
		String color = ((TitleDeedSquare) squares.get(i)).getColor();
		Logger.getInstance().log("All properties of color " + color + " are downgraded!");

		for (int n = 0; n < squares.size(); n++) {
			if (squares.get(n) instanceof TitleDeedSquare) {
				TitleDeedSquare td = (TitleDeedSquare) squares.get(n);
				if (td.getColor().equals(color)) {
					td.downgrade();
				}

			}

		}
	}

	public void sellTitleDeed(String titleDeedName) {
		TitleDeedSquare deed = (TitleDeedSquare) getSquareByName(titleDeedName);
		Player p = deed.getOwner();
		Logger.getInstance().log(p.getName() + " has sold " + deed.getName() + " for $" + deed.getSellAmount() + "!");
		p.updateBalanceDelta(deed.getSellAmount());
		deed.setNumberOfHotels(0);
		deed.setNumberOfHouses(0);
		deed.setNumberOfSkyScrapers(0);
		deed.setOwner(null);
		deed.setAvailable(true);
	}

	public boolean downgradeTitleDeed(String titleDeedName) {
		TitleDeedSquare deed = (TitleDeedSquare) getSquareByName(titleDeedName);
		Player p = deed.getOwner();
		int payment = deed.downgrade();
		if (payment == -1)
			return false;

		Logger.getInstance().log(p.getName() + " has downgraded " + deed.getName() + " for $" + payment + "!");
		p.updateBalanceDelta(payment);
		return true;
	}

}
