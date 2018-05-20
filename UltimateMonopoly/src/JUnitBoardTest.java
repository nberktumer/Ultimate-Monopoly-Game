import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.Board;
import domain.Player;
import domain.square.GoSquare;
import domain.square.Square;
import domain.square.TaxRefundSquare;
import domain.square.TitleDeedSquare;

public class JUnitBoardTest {

	@Test
	public void getSquareByNameValidName() {
		Board b = new Board();
		Square s1 = new GoSquare("Go Square");
		Square s2 = new TaxRefundSquare("Refund Tax Square");
		b.addSquare(s1);
		b.addSquare(s2);

		assertEquals(s1, b.getSquareByName("Go Square"));

	}

	@Test
	public void getSquareByNameInvalidName() {
		Board b = new Board();
		Square s1 = new GoSquare("Go Square");
		Square s2 = new TaxRefundSquare("Refund Tax Square");
		b.addSquare(s1);
		b.addSquare(s2);

		assertNull(b.getSquareByName("Istanbul Square"));

	}

	@Test
	public void buyPropertyWhereSquareIsNotBuyable() {
		Board b = new Board();
		GoSquare s1 = new GoSquare("Go Square");
		Player p1 = new Player("Zahit", 300, false, null, null, 1, 3, false);

		assertFalse(b.buyProperty(p1, s1));
	}

	@Test
	public void buyPropertySuccessfully() {
		Board b = new Board();
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", null, 200, null, 0, 0, 0, 0, 0, 0, "RED", false);
		Player p1 = new Player("Zahit", 300, false, null, null, 1, 3, false);

		// check if method successful
		assertTrue(b.buyProperty(p1, s1));
		// check if player balance is updated
		assertEquals(p1.getBalance(), 100);
		// check if square's owner is updated
		assertEquals(s1.getOwner(), p1);

	}

	@Test
	public void buyPropertyOutOfBalance() {
		Board b = new Board();
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", null, 200, null, 0, 0, 0, 0, 0, 0, "RED", false);
		Player p1 = new Player("Zahit", 100, false, null, null, 1, 3, false);

		// check if method unsuccessful
		assertFalse(b.buyProperty(p1, s1));
		// check if player balance is initial value
		assertEquals(p1.getBalance(), 100);
		// check if square's owner is still null
		assertNull(s1.getOwner());

	}

	@Test
	public void buyPropertyAlreadyHasOwner() {
		Board b = new Board();
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", new Player(), 200, null, 0, 0, 0, 0, 0, 0, "RED",
				false);
		Player p1 = new Player("Zahit", 300, false, null, null, 1, 3, false);

		// check if method unsuccessful
		assertFalse(b.buyProperty(p1, s1));
		// check if player balance is initial value
		assertEquals(p1.getBalance(), 300);
		// check if square's owner is not equal to player attempting to buy
		assertNotEquals(s1.getOwner(), p1);
	}

	@Test
	public void upgradeTitleDeedNotMajorityOwner() {
		Board b = new Board();
		Player p1 = new Player("Zahit", 300, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 0, 100, 0, 200, 0, 300, "RED",
				false);
		TitleDeedSquare s2 = new TitleDeedSquare("Ankara Square", null, 200, null, 0, 100, 0, 200, 0, 300, "RED",
				false);
		TitleDeedSquare s3 = new TitleDeedSquare("Izmir Square", null, 200, null, 0, 100, 0, 200, 0, 300, "RED", false);

		b.addSquare(s1);
		b.addSquare(s2);
		b.addSquare(s3);

		// check if method unsuccessful
		assertFalse(b.upgradeTitleDeed(p1, s1));
		// check if player's balance is initial value
		assertEquals(p1.getBalance(), 300);
		// check if the number of houses is initial value
		assertEquals(s1.getNumberOfHouses(), 0);

	}

	@Test
	public void upgradeTitleDeedPlayerOutOfBalance() {
		Board b = new Board();
		Player p1 = new Player("Zahit", 300, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 0, 500, 0, 600, 0, 700, "RED",
				false);

		// check if method unsuccessful
		assertFalse(b.upgradeTitleDeed(p1, s1));
		// check if player's balance is initial value
		assertEquals(p1.getBalance(), 300);
		// check if the number of houses is initial value
		assertEquals(s1.getNumberOfHouses(), 0);
	}

	@Test
	public void upgradeTitleDeedSuccessfulHouseBuilt() {
		Board b = new Board();
		Player p1 = new Player("Zahit", 1000, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 0, 100, 0, 200, 0, 300, "RED",
				false);
		// first house upgrade
		assertTrue(b.upgradeTitleDeed(p1, s1));
		assertEquals(p1.getBalance(), 900);
		assertEquals(s1.getNumberOfHouses(), 1);

		// second house upgrade
		assertTrue(b.upgradeTitleDeed(p1, s1));
		assertEquals(p1.getBalance(), 800);
		assertEquals(s1.getNumberOfHouses(), 2);

		// third house upgrade
		assertTrue(b.upgradeTitleDeed(p1, s1));
		assertEquals(p1.getBalance(), 700);
		assertEquals(s1.getNumberOfHouses(), 3);

		// fourth house upgrade
		assertTrue(b.upgradeTitleDeed(p1, s1));
		assertEquals(p1.getBalance(), 600);
		assertEquals(s1.getNumberOfHouses(), 4);

	}

	@Test
	public void upgradeTitleDeedSuccessfulHotelBuilt() {
		Board b = new Board();
		Player p1 = new Player("Zahit", 1000, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 4, 100, 0, 200, 0, 300, "RED",
				false);

		// upgrade attempt
		assertTrue(b.upgradeTitleDeed(p1, s1));
		// check player balance
		assertEquals(p1.getBalance(), 800);
		// check number of hotels
		assertEquals(s1.getNumberOfHotels(), 1);

	}

	@Test
	public void upgradeTitleDeedSuccessfulSkyscraperBuilt() {
		Board b = new Board();
		Player p1 = new Player("Zahit", 1000, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 0, 100, 1, 200, 0, 300, "RED",
				false);

		// upgrade attempt
		assertTrue(b.upgradeTitleDeed(p1, s1));
		// check player balance
		assertEquals(p1.getBalance(), 700);
		// check number of skyscrapers
		assertEquals(s1.getNumberOfSkyScrapers(), 1);

	}

	@Test
	public void upgradeTitleDeedAlreadyFullyUpgraded() {
		Board b = new Board();
		Player p1 = new Player("Zahit", 1000, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 0, 100, 0, 200, 1, 300, "RED",
				false);

		// upgrade attempt
		assertFalse(b.upgradeTitleDeed(p1, s1));
		// check player balance
		assertEquals(p1.getBalance(), 1000);
		// check number of skyscrapers
		assertEquals(s1.getNumberOfSkyScrapers(), 1);

	}

	@Test
	public void upgradeTitleDeedCorruptedNumberOfBuildings() {
		Board b = new Board();
		Player p1 = new Player("Zahit", 1000, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 7, 100, 3, 200, 5, 300, "RED",
				false);

		// upgrade attempt
		assertFalse(b.upgradeTitleDeed(p1, s1));
		// check player balance
		assertEquals(p1.getBalance(), 1000);

	}

	@Test
	public void majorityOwnerSuccessfulCaseOne() {
		// case where player owns 2 squares among 3 squares with same color.
		Board b = new Board();
		Player p1 = new Player("Zahit", 1000, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 7, 100, 3, 200, 5, 300, "RED",
				false);
		TitleDeedSquare s2 = new TitleDeedSquare("Izmir Square", p1, 200, null, 7, 100, 3, 200, 5, 300, "RED", false);
		TitleDeedSquare s3 = new TitleDeedSquare("Adana Square", new Player(), 200, null, 7, 100, 3, 200, 5, 300, "RED",
				false);
		b.addSquare(s1);
		b.addSquare(s2);
		b.addSquare(s3);

		assertTrue(b.majorityOwner(p1, "RED"));

	}

	@Test
	public void majorityOwnerUnsuccessfulCaseOne() {
		// case where player owns 1 square among 3 squares with same color.
		Board b = new Board();
		Player p1 = new Player("Zahit", 1000, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 7, 100, 3, 200, 5, 300, "RED",
				false);
		TitleDeedSquare s2 = new TitleDeedSquare("Izmir Square", new Player(), 200, null, 7, 100, 3, 200, 5, 300, "RED",
				false);
		TitleDeedSquare s3 = new TitleDeedSquare("Adana Square", new Player(), 200, null, 7, 100, 3, 200, 5, 300, "RED",
				false);
		b.addSquare(s1);
		b.addSquare(s2);
		b.addSquare(s3);

		assertFalse(b.majorityOwner(p1, "RED"));

	}

	@Test
	public void majorityOwnerSuccessfulCaseTwo() {
		// case where player owns 1 square among 2 squares with same color.
		Board b = new Board();
		Player p1 = new Player("Zahit", 1000, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", p1, 200, null, 7, 100, 3, 200, 5, 300, "RED",
				false);
		TitleDeedSquare s2 = new TitleDeedSquare("Izmir Square", new Player(), 200, null, 7, 100, 3, 200, 5, 300, "RED",
				false);

		b.addSquare(s1);
		b.addSquare(s2);

		assertTrue(b.majorityOwner(p1, "RED"));

	}

	@Test
	public void majorityOwnerUnsuccessfulCaseTwo() {
		// case where player owns 0 squares among 2 squares with same color.
		Board b = new Board();
		Player p1 = new Player("Zahit", 1000, false, null, null, 1, 3, false);
		TitleDeedSquare s1 = new TitleDeedSquare("Istanbul Square", new Player(), 200, null, 7, 100, 3, 200, 5, 300,
				"RED", false);
		TitleDeedSquare s2 = new TitleDeedSquare("Izmir Square", new Player(), 200, null, 7, 100, 3, 200, 5, 300, "RED",
				false);

		b.addSquare(s1);
		b.addSquare(s2);

		assertFalse(b.majorityOwner(p1, "RED"));

	}

	@Test
	public void repOkTrueTest() {
		Board b = new Board();
		b.addSquare(new GoSquare("Go Square"));
		assertTrue(b.repOk());
	}

	@Test
	public void repOkFalseTest() {
		Board b = new Board();
		assertFalse(b.repOk());

	}
}
