package domain.card;

import domain.MonopolyGameController;

public class CCBankErrorinYourFavor extends Card {

	public CCBankErrorinYourFavor() {
		super("Bank Error in Your Favor!", "CommunityChest", true, "bankErrorInYourFavor");
	}

	@Override
	public void useCard(MonopolyGameController controller) {
		controller.getCurrentPlayer().updateBalanceDelta(200);
	}
}