package domain;

import domain.card.CCBankErrorinYourFavor;
import domain.card.CCBeKindRewind;
import domain.card.CCGotoJail;
import domain.card.Card;
import domain.card.ChanceAdvancetoIllinoidAve;
import domain.card.ChanceAdvancetothePayCorner;
import domain.card.ChanceHurricaneMakesLandfall;
import domain.card.ChanceMardiGras;

public class CardFactory {

	private static CardFactory instance;
	
	public Card createCard(String name) {
		if(name.equals("Mardi Gras")) {
			return new ChanceMardiGras();
		} else if(name.equals("Advance to the Pay Corner")) {
			return new ChanceAdvancetothePayCorner();
		} else if(name.equals("Advance to Illinois Ave")) {
			return new ChanceAdvancetoIllinoidAve();
		} else if(name.equals("Hurricane Makes Landfall!")) {
			return new ChanceHurricaneMakesLandfall();
		} else if(name.equals("Be Kind, Rewind")) {
			return new CCBeKindRewind();
		} else if(name.equals("Bank Error in Your Favor!")) {
			return new CCBankErrorinYourFavor();
		} else if(name.equals("Go to Jail")) {
			return new CCGotoJail();
		}
		
		return null;
	}


	
	public static CardFactory getInstance() {
		if (instance == null)
			instance = new CardFactory();
		return instance;
	}
	
}
