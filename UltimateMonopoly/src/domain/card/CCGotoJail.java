package domain.card;

import domain.MonopolyGameController;

public class CCGotoJail extends Card {

	public CCGotoJail() {
		super("Go to Jail!", "CommunityChest", true, "goToJail");
	}

	@Override
	public void useCard(MonopolyGameController controller) {
		controller.teleport(14);
	}
}