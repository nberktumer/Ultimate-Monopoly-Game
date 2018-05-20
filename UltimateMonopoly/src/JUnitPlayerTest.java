import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import domain.Cup;
import domain.DieValue;
import domain.Piece;
import domain.Player;
import domain.card.Card;
import domain.square.ChanceSquare;
import domain.square.Square;

public class JUnitPlayerTest {

	@Test
    public void updateBalancePositiveOrZeroBalance() {
		Player p = new Player("Player", 300, false, new ArrayList<>(), new Piece(new ChanceSquare("TestSquare"), null), 1, 3, false);
		p.updateBalanceDelta(100);
		assertTrue(p.getBalance() == 400);
	}

	@Test
    public void updateBalanceNegativeBalance() {
		Player p = new Player("Player", 300, false, new ArrayList<>(), new Piece(new ChanceSquare("TestSquare"), null), 1, 3, false);
		p.updateBalanceDelta(-100);
		assertTrue(p.getBalance() == 200);
	}

	@Test
    public void repOkTrueTest() {
		Player p = new Player("Player", 300, false, new ArrayList<>(), new Piece(new ChanceSquare("TestSquare"), null), 1, 3, false);
		assertTrue(p.repOk());
	}

	@Test
    public void repOkFalseTest() {
		Player p = new Player("Player", 300, false, null, new Piece(new ChanceSquare("TestSquare"), null), 1, 3, false);
		assertFalse(p.repOk());
	}

}
