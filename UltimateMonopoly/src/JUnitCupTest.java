import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import domain.Cup;
import domain.DieValue;
import domain.Piece;
import domain.Player;
import domain.card.Card;
import domain.square.GoToJailSquare;
import domain.square.IncomeTaxSquare;
import domain.square.ReverseDirectionSquare;

public class JUnitCupTest {
	@Test
	public void isSpecialWithSpecial() {
		Cup c = new Cup();
		c.setResults(new ArrayList<DieValue>(Arrays.asList(DieValue.MRMONOPOLY, DieValue.FIVE, DieValue.FOUR)));
		assertTrue(c.isSpecial());
	}

	@Test
	public void isSpecialWithoutSpecial() {
		Cup c = new Cup();
		c.setResults(new ArrayList<DieValue>(Arrays.asList(DieValue.ONE, DieValue.FIVE, DieValue.FOUR)));
		assertFalse(c.isSpecial());
	}

	@Test
	public void falseCupRepOk() {
		Cup c = new Cup();
		c.setNumberOfDuplicates(5);
		assertFalse(c.repOk());
		
	}

	@Test
	public void trueCupRepOk() {
		Cup c = new Cup();
		c.setNumberOfDuplicates(2);
		assertTrue(c.repOk());

	}

}
