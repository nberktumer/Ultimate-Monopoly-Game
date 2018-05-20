package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RegularDie extends Die {
	ArrayList<DieValue> dieValues = new ArrayList<DieValue>(
			Arrays.asList(DieValue.FOUR, DieValue.FIVE, DieValue.SIX, DieValue.ONE, DieValue.TWO, DieValue.THREE));

	public DieValue roll() {
		Random r = new Random();
		return dieValues.get(r.nextInt(6));

	}

}
