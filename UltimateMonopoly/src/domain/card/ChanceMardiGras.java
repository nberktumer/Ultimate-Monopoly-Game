package domain.card;

import domain.MonopolyGameController;

public class ChanceMardiGras extends Card {

	public ChanceMardiGras() {
		super("MARDI GRAS!", "Chance", true, "mardiGras");
	}

	@Override
	public void useCard(MonopolyGameController controller) {
		int playerSize = controller.getPlayers().size();

		for (int i = 0; i < playerSize; i++) {
			if (i != controller.getCurrentPlayerIndex())
				controller.teleportPlayer(37, i);
		}
		controller.teleport(37);
	}

}
