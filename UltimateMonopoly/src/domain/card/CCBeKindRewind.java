package domain.card;

import java.util.List;

import domain.Cup;
import domain.DieValue;
import domain.Logger;
import domain.MonopolyGameController;
import domain.square.Square;

public class CCBeKindRewind extends Card {

	public CCBeKindRewind() {
		super("Be Kind, Rewind", "CommunityChest", true, "beKindRewind");
	}

	@Override
	public void useCard(MonopolyGameController controller) {
		controller.getCurrentPlayer().setDirection(controller.getCurrentPlayer().getDirection() * -1);

		Cup c = controller.rollDice();
		List<DieValue> dice = c.getResults();

		int total = dice.get(0).getValue() + dice.get(1).getValue();
		Logger.getInstance().log("You rolled " + total + ".");
		controller.move(total);

		Square finalSquare = controller.getCurrentPlayer().getPiece().getLocation();
		finalSquare.landedOn(controller);

		controller.getCurrentPlayer().setDirection(controller.getCurrentPlayer().getDirection() * -1);
		controller.getCurrentPlayer().getPiece().getLocation().landedOn(controller);
	}
}
