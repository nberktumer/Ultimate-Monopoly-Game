import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import domain.Cup;
import domain.Player;
import domain.square.Square;
import domain.square.TitleDeedSquare;

public class JUnitTitleDeedSquareTest {
	/*
	 * ArrayList<Integer> rents; Player p1, p2; int balance1, balance2;
	 * 
	 * @Before public void setUp() {
	 * 
	 * rents = new ArrayList<Integer>(); rents.add(30); rents.add(50);
	 * rents.add(60); rents.add(70); rents.add(80); rents.add(90);
	 * rents.add(100); p1 = new Player("Ibrahim", 300, false, null, null, 1, 3,
	 * false); p2 = new Player("Durul", 300, false, null, null, 1, 3, false);
	 * balance1 = p1.getBalance(); balance2 = p2.getBalance(); }
	 * 
	 * @Test public void landedOnDifferentOwnerEnoughMoney() {
	 * 
	 * TitleDeedSquare sq = new TitleDeedSquare("Anon District", p1, 200, rents,
	 * 0, 0, 0, 0, 0, 0, "GREEN", false);
	 * 
	 * sq.landedOn(p2);
	 * 
	 * assertEquals(p2.getBalance(), balance2 - sq.getRent());
	 * assertEquals(p1.getBalance(), balance1 + sq.getRent());
	 * 
	 * }
	 * 
	 * @Test public void landedOnDifferentOwnerNotEnoughMoney() {
	 * 
	 * TitleDeedSquare sq = new TitleDeedSquare("Anon District", p1, 200, rents,
	 * 0, 0, 0, 0, 0, 0, "GREEN", false);
	 * p2.updateBalanceDelta(-p2.getBalance()); sq.landedOn(p2);
	 * 
	 * assertEquals(0, p2.getBalance()); assertNotEquals(p1.getBalance(),
	 * balance1 + sq.getRent());
	 * 
	 * }
	 * 
	 * @Test public void landedOnSelfOwnedSquare() { TitleDeedSquare sq = new
	 * TitleDeedSquare("Anon District", p1, 200, rents, 0, 0, 0, 0, 0, 0,
	 * "GREEN", false); sq.landedOn(p1);
	 * 
	 * assertNotEquals(p1.getBalance(), balance1 + sq.getRent());
	 * 
	 * }
	 * 
	 * @Test public void landedOnUnOwnedSquare() {
	 * 
	 * TitleDeedSquare sq = new TitleDeedSquare("Anon District", null, 200,
	 * rents, 0, 0, 0, 0, 0, 0, "GREEN", false); sq.landedOn(p1);
	 * assertEquals(p1.getBalance(), balance1);
	 * 
	 * // TODO: if p1 devides to buy
	 * 
	 * }
	 * 
	 * @Test public void trueTitleDeedSquareRepOk() { TitleDeedSquare sq = new
	 * TitleDeedSquare("Anon District", new Player(), 200, rents, 0, 100, 0,
	 * 200, 0, 300, "GREEN", false); assertTrue(sq.repOk()); }
	 * 
	 * @Test public void falseTitleDeedSquareRepOk() { TitleDeedSquare sq = new
	 * TitleDeedSquare("Anon District", null, -200, rents, 0, 0, 0, 0, 0, 0,
	 * "GREEN", false); assertFalse(sq.repOk());
	 * 
	 * }
	 */
}
