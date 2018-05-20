package domain.card;

import domain.MonopolyGameController;

public class ChanceAdvancetothePayCorner extends Card {

	public ChanceAdvancetothePayCorner() {
		super("Advance to the Pay Corner", "Chance", true, "advanceToThePayCorner");
	}

	@Override
	public void useCard(MonopolyGameController controller) {
		int index = controller.getBoard().getSquares().indexOf(controller.getCurrentPlayer().getPiece().getLocation());
		if (index >= 0 && index <= 55) {
			controller.teleport(0);
		} else if (index >= 56 && index <= 95) {
			controller.teleport(76);
		} else {
			controller.teleport(114);
		}
	}

}
