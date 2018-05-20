package domain;

import java.util.ArrayList;
import java.util.List;

import domain.card.Card;

/**
 * @overview This class holds the required properties for a real-world Monopoly
 *           Player.
 * @author Null
 * 
 */
public class Player {

	private int balance;
	private boolean inJail = false;
	private boolean isBankrupt = false;
	private int jailTurnLeft = 3;
	private String name;
	private Piece piece;
	private int direction;
	private List<Card> cards = new ArrayList<Card>();

	public Player(String name, int startingBalance, boolean inJail, List<Card> cards, Piece piece, int direction,
			int jailTurnLeft, boolean isBankrupt) {
		this.name = name;
		this.balance = startingBalance;
		this.inJail = inJail;
		this.cards = cards;
		this.piece = piece;
		this.direction = direction;
		this.jailTurnLeft = jailTurnLeft;
		this.isBankrupt = isBankrupt;
	}

	public Player() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @requires chestCard is not null.
	 * @modifies communityChestCards
	 * @effects Adds chestCard to communityChestCards.
	 */
	public boolean addChestCard(Card chestCard) {
		// this.communityChestCards.add(chestCard);
		return true;
	}

	/**
	 * @requires balance and deltaChange are not null.
	 * @modifies balance
	 * @effects updates the balance via changing it by deltaChange amount,
	 *          notifies MonopolyBot
	 */
	public int updateBalanceDelta(int delta) {
		balance = balance + delta;
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "transaction", this);
		if (balance < 0) {

		}

		return balance;
	}

	/**
	 * @requires -
	 * @modifies -
	 * @effects returns piece.
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * @requires -
	 * @modifies piece field
	 * @effects sets the piece field to have the value piece.
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * @requires -
	 * @modifies -
	 * @effects returns direction.
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @requires direction must be either 1 or -1
	 * @modifies direction field
	 * @effects sets the direction field to have the value direction.
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @requires -
	 * @modifies -
	 * @effects returns cards.
	 */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * @requires -
	 * @modifies -
	 * @effects returns balance.
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * @requires -
	 * @modifies -
	 * @effects returns the boolean value that represents if the player is in
	 *          jail or not.
	 */
	public boolean isInJail() {
		return inJail;
	}

	public int getJailTurnLeft() {
		return jailTurnLeft;
	}

	public void setJailTurnLeft(int turn) {
		jailTurnLeft = turn;
	}

	public void resetJailTurnLeft() {
		jailTurnLeft = 3;
	}

	public void decrementJailTurnLeft() {
		jailTurnLeft--;
		if (jailTurnLeft == 0) {
			resetJailTurnLeft();
			inJail = false;
		}
	}

	/**
	 * @requires -
	 * @modifies inJail
	 * @effects sets the player as he is inJail, notifies MonopolyBot.
	 */
	public void setInJail(boolean playerInJail) {
		this.inJail = playerInJail;
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "jail", this);
	}

	/**
	 * @requires -
	 * @modifies -
	 * @effects returns name.
	 */
	public String getName() {
		return name;
	}

	public boolean repOk() {
		if (balance >= 0 && name != null && piece != null && (direction == 1 || direction == -1) && cards != null) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Player [balance=" + balance + ", inJail=" + inJail + ", name=" + name + ", piece=" + piece
				+ ", direction=" + direction + ", cards=" + cards + "]";
	}

	public boolean isBankrupt() {
		return isBankrupt;
	}

	public void setBankrupt(boolean isBankrupt) {
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "bankrupt", this);
		this.isBankrupt = isBankrupt;
	}

}
