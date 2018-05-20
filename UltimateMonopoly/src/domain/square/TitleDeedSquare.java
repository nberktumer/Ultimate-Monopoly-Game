package domain.square;

import java.awt.Color;
import java.util.ArrayList;

import domain.BankruptcyProcess;
import domain.Logger;
import domain.MonopolyGameController;
import domain.Player;

/**
 * @overview Represents the Title Deed Squares which are special squares of
 *           Monopoly game that are can be bought and upgraded.
 * @author Null
 */
public class TitleDeedSquare extends Square {

	private Player owner;
	private int price;
	private ArrayList<Integer> rent;
	private int numberOfHouses;
	private int housePrice;
	private int numberOfHotels;
	private int hotelPrice;
	private int numberOfSkyScrapers;
	private int skyScraperPrice;
	private String color;
	private boolean isStreet;
	private boolean lastPaymentFailed = false;

	public TitleDeedSquare(String name, Player owner, int price, ArrayList<Integer> rent, int numberOfHouses,
			int housePrice, int numberOfHotels, int hotelPrice, int numberOfSkyscrapers, int skyScraperPrice,
			String color, boolean isStreet) {
		super(name);
		this.owner = owner;
		this.price = price;
		this.rent = rent;
		this.numberOfHouses = numberOfHouses;
		this.housePrice = housePrice;
		this.numberOfHotels = numberOfHotels;
		this.hotelPrice = hotelPrice;
		this.numberOfSkyScrapers = numberOfSkyscrapers;
		this.skyScraperPrice = skyScraperPrice;
		this.color = color;
		this.isStreet = isStreet;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the number of skyscrapers.
	 *
	 */

	public int getNumberOfSkyScrapers() {
		return numberOfSkyScrapers;
	}

	/**
	 * 
	 * @requires -
	 * @modifies numberOfSkyScrapers
	 * @effects Sets the numberOfSkyScrapers field of class to the param
	 *          numberOfSkyScrapers.
	 *
	 */
	public void setNumberOfSkyScrapers(int numberOfSkyScrapers) {
		this.numberOfSkyScrapers = numberOfSkyScrapers;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the owner.
	 *
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * 
	 * @requires -
	 * @modifies owner
	 * @effects Sets the owner field of class to the param owner.
	 *
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns true if owner is null, false otherwise.
	 *
	 */
	public boolean isAvailable() {
		return owner == null;
	}

	/**
	 * 
	 * @requires -
	 * @modifies isAvailable
	 * @effects Sets isAvaiable field of class to the param isAvaiable.
	 *
	 */
	public void setAvailable(boolean isAvailable) {

	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the price.
	 *
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * 
	 * @requires -
	 * @modifies price
	 * @effects Sets the price field of class to the param price.
	 *
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the rent list.
	 *
	 */
	public ArrayList<Integer> getRentList() {
		return rent;
	}

	/**
	 * 
	 * @requires Rent list is not null. <br>
	 *           number of houses is between 0 and 4<br>
	 *           number of hotels is between 0 and 1<br>
	 *           number of skyscrapers is between 0 and 1
	 * @modifies -
	 * @effects Returns the rent cost calculated by number of houses, hotels and
	 *          skyscrapers.
	 *
	 */
	public int getRent() {

		int rentindex = numberOfHouses;
		if (numberOfHotels > 0) {
			rentindex = 5;
		} else if (numberOfSkyScrapers > 0) {
			rentindex = 6;
		}
		return rent.get(rentindex);
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the number of houses.
	 *
	 */
	public int getNumberOfHouses() {
		return numberOfHouses;
	}

	/**
	 * 
	 * @requires -
	 * @modifies numberOfHouses
	 * @effects Sets the numberOfHouses field of class to the param
	 *          numberOfHouses.
	 *
	 */
	public void setNumberOfHouses(int numberOfHouses) {
		this.numberOfHouses = numberOfHouses;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the house price.
	 *
	 */
	public int getHousePrice() {
		return housePrice;
	}

	/**
	 * 
	 * @requires -
	 * @modifies housePrice
	 * @effects Sets the housePrice field of class to the param housePrice.
	 *
	 */
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the number of hotels.
	 *
	 */
	public int getNumberOfHotels() {
		return numberOfHotels;
	}

	/**
	 * 
	 * @requires -
	 * @modifies numberOfHotels
	 * @effects Sets the numberOfHotels field of class to the param
	 *          numberOfHotels.
	 *
	 */
	public void setNumberOfHotels(int numberOfHotels) {
		this.numberOfHotels = numberOfHotels;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the hotel price
	 *
	 */
	public int getHotelPrice() {
		return hotelPrice;
	}

	/**
	 * 
	 * @requires -
	 * @modifies hotelPrice
	 * @effects Sets the hotelPrice field of class to the param hotelPrice.
	 *
	 */
	public void setHotelPrice(int hotelPrice) {
		this.hotelPrice = hotelPrice;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the skyscraper price.
	 *
	 */
	public int getSkyScraperPrice() {
		return skyScraperPrice;
	}

	/**
	 * 
	 * @requires -
	 * @modifies skyScraperPrice
	 * @effects Sets the skyScraperPrice field of class to the param
	 *          skyScraperPrice.
	 *
	 */
	public void setSkyScraperPrice(int skyScraperPrice) {
		this.skyScraperPrice = skyScraperPrice;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the color as String.
	 *
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 
	 * @requires -
	 * @modifies color
	 * @effects Sets the color field of class to the param color.
	 *
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns true if the instance is a street, returns false
	 *          otherwise.
	 *
	 */
	public boolean isStreet() {
		return isStreet;
	}

	/**
	 * 
	 * @requires -
	 * @modifies isStreet
	 * @effects Sets the isStreet field of class to the param isStreet.
	 *
	 */
	public void setStreet(boolean isStreet) {
		this.isStreet = isStreet;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns the cost of upgrade calculated by the number of houses,
	 *          hotels and skyscrapers. Returns -1 if the number of houses,
	 *          hotels and skyscrapers are corrupted.
	 *
	 */
	public int getUpgradeCost() {
		if (getNumberOfHouses() < 4 && getNumberOfHotels() == 0 && getNumberOfSkyScrapers() == 0) {
			return housePrice;
		} else if (getNumberOfHouses() == 4 && getNumberOfHotels() == 0 && getNumberOfSkyScrapers() == 0) {
			return hotelPrice;
		} else if (getNumberOfHouses() == 0 && getNumberOfHotels() == 1 && getNumberOfSkyScrapers() == 0) {
			return skyScraperPrice;
		}

		return -1;
	}

	/**
	 * 
	 * @requires currentPlayer is not null.
	 * @modifies currentPlayer
	 * @effects If the currentPlayer is not the owner of this square and has
	 *          enough money to pay, it pays the rent to the owner. If the
	 *          currentPlayer is not the owner of this square and has no money
	 *          to pay, it goes bankrupt. If the currentPlayer is the owner of
	 *          this square, nothing happens.
	 *
	 */
	@Override
	public void landedOn(MonopolyGameController monopolyGame) {

		if (this.isAvailable()) {
			monopolyGame.notifyObservers("game", "stateChange", "enableTransaction");
		} else {
			int multiplier = monopolyGame.getBoard().getPaymentMultiplier(this);
			this.payRent(monopolyGame.getCurrentPlayer(), multiplier);

			if (this.isLastPaymentFailed()) {
				monopolyGame.setBankruptcyProcess(new BankruptcyProcess(monopolyGame.getCurrentPlayer(),
						this.getOwner(), this.getRent() * multiplier));
				monopolyGame.startBankruptcyProcess(this.getRent() * multiplier);
				this.setLastPaymentFailed(false);
			}
		}

		monopolyGame.notifyObservers("game", "stateChange", "endOfTurn");

	}

	public void payRent(Player currentPlayer, int multiplier) {
		Player playerPlaying = currentPlayer;
		if (this.owner != null && this.owner != playerPlaying) {
			// playerPlaying has to pay rent
			int rentPrice = getRent() * multiplier;
			if (playerPlaying.getBalance() >= rentPrice) {
				// player has enough money
				// pay rent instantly
				owner.updateBalanceDelta(rentPrice);
				playerPlaying.updateBalanceDelta(-rentPrice);
				Logger.getInstance().log(playerPlaying.getName() + " has landed on " + getName() + " and paid $"
						+ rentPrice + " to " + owner.getName() + "!");

			} else {
				// player goes bankrupt
				setLastPaymentFailed(true);
			}
		}
	}

	public boolean repOk() {
		if (price > 0 && rent != null && rent.size() == 7 && numberOfHouses >= 0 && numberOfHouses <= 4
				&& housePrice > 0 && numberOfHotels >= 0 && numberOfHotels <= 1 && hotelPrice > 0
				&& numberOfSkyScrapers >= 0 && numberOfSkyScrapers <= 1 && skyScraperPrice > 0 && color != null) {
			for (int r : rent) {
				if (r <= 0)
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "TitleDeedSquare [owner=" + owner + ", price=" + price + ", rent=" + rent + ", numberOfHouses="
				+ numberOfHouses + ", housePrice=" + housePrice + ", numberOfHotels=" + numberOfHotels + ", hotelPrice="
				+ hotelPrice + ", numberOfSkyScrapers=" + numberOfSkyScrapers + ", skyScraperPrice=" + skyScraperPrice
				+ ", color=" + color + ", isStreet=" + isStreet + "]";
	}

	public int getSellAmount() {
		int total = getPrice();

		if (getNumberOfSkyScrapers() == 1) {
			total += 4 * getHousePrice() + 1 * getHotelPrice() + 1 * getSkyScraperPrice();
		} else if (getNumberOfHotels() == 1) {
			total += 4 * getHousePrice() + 1 * getHotelPrice() + 0 * getSkyScraperPrice();
		} else {
			total += getNumberOfHouses() * getHousePrice() + 0 * getHotelPrice() + 0 * getSkyScraperPrice();
		}

		total /= 2;

		return total;

	}

	public boolean isLastPaymentFailed() {
		return lastPaymentFailed;
	}

	public void setLastPaymentFailed(boolean lastPaymentFailed) {
		this.lastPaymentFailed = lastPaymentFailed;
	}

	public int downgrade() {
		int payment = -1;

		if (getNumberOfSkyScrapers() == 1) {
			setNumberOfSkyScrapers(0);
			setNumberOfHotels(1);
			payment = getSkyScraperPrice() / 2;
		} else if (getNumberOfHotels() == 1) {
			setNumberOfHotels(0);
			setNumberOfHouses(4);
			payment = getHotelPrice() / 2;
		} else if (getNumberOfHouses() > 0) {
			setNumberOfHouses(getNumberOfHouses() - 1);
			payment = getHousePrice() / 2;
		}

		return payment;
	}

	public int getDowngradePayment() {
		int payment = -1;

		if (getNumberOfSkyScrapers() == 1) {
			payment = getSkyScraperPrice() / 2;
		} else if (getNumberOfHotels() == 1) {
			payment = getHotelPrice() / 2;
		} else if (getNumberOfHouses() > 0) {
			payment = getHousePrice() / 2;
		}
		return payment;
	}

}
