package domain.card;

import java.util.ArrayList;

import domain.MonopolyGameController;
import domain.square.TitleDeedSquare;

public class ChanceHurricaneMakesLandfall extends Card {

	public ChanceHurricaneMakesLandfall() {
		super("Hurricane makes landfall!", "Chance", true, "hurricaneMakesLandfall");
	}

	@Override
	public void useCard(MonopolyGameController controller) {
		ArrayList<Integer> indices = new ArrayList<Integer>();

		for (int i = 0; i < controller.getBoard().getSquares().size(); i++) {
			if (controller.getBoard().getSquares().get(i) instanceof TitleDeedSquare) {
				indices.add(i);
			}
		}

		controller.notifyObservers("game", "highlightSquaresColor", indices);
		controller.notifyObservers("game", "endTurn", "false");
	}

}