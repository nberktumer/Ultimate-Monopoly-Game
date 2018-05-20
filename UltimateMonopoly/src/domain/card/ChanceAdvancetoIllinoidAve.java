package domain.card;

import domain.MonopolyGameController;

public class ChanceAdvancetoIllinoidAve extends Card {

	public ChanceAdvancetoIllinoidAve() {
		super("Advance to Illinois Ave.", "Chance", true, "advanceToIllinoisAve");
	}

	@Override
	public void useCard(MonopolyGameController controller) {
		controller.teleport(60);
	}

}