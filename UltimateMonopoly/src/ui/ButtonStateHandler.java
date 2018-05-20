package ui;

import java.util.Map;

import javax.swing.JButton;

public class ButtonStateHandler {

	private static boolean mrMonopolyUpcoming = false;
	private static boolean busIconUpcoming = false;
	private static boolean doubleUpcoming = false;

	public static void modifyButtonStates(Map<String, JButton> buttons, String stateName) {
		switch (stateName) {
		case "doubleState":
			doubleUpcoming = true;
			break;
		case "busState":
			busIconUpcoming = true;
			break;
		case "mrMonopolyState":
			mrMonopolyUpcoming = true;
			break;
		case "startOfTurn":
			buttons.get("endTurn").setEnabled(false);
			buttons.get("rollDice").setEnabled(true);
			break;
		case "endOfTurn":
			buttons.get("endTurn").setEnabled(true);
			buttons.get("rollDice").setEnabled(false);

			if (doubleUpcoming) {
				doubleUpcoming = false;
				mrMonopolyUpcoming = false;
				busIconUpcoming = false;
				buttons.get("endTurn").setEnabled(false);
				buttons.get("rollDice").setEnabled(true);
			} else if (busIconUpcoming) {
				busIconUpcoming = false;
				buttons.get("endTurn").setEnabled(true);
				buttons.get("rollDice").setEnabled(false);
			} else if (mrMonopolyUpcoming) {
				mrMonopolyUpcoming = false;
				buttons.get("endTurn").setEnabled(true);
				buttons.get("rollDice").setEnabled(false);
			}

			break;
		case "waitForActionState":
			buttons.get("endTurn").setEnabled(false);
			buttons.get("rollDice").setEnabled(false);
			break;
		case "enableTransaction":
			buttons.get("purchase").setEnabled(true);
			buttons.get("auction").setEnabled(true);
			break;
		case "disableTransaction":
			buttons.get("purchase").setEnabled(false);
			buttons.get("auction").setEnabled(false);
			break;
		case "outOfJailState":
			buttons.get("rollDice").setEnabled(true);
			buttons.get("endTurn").setEnabled(false);
			buttons.get("purchase").setEnabled(false);
			buttons.get("auction").setEnabled(false);
			break;
		case "disableAll":
			buttons.get("rollDice").setEnabled(false);
			buttons.get("endTurn").setEnabled(false);
			buttons.get("purchase").setEnabled(false);
			buttons.get("auction").setEnabled(false);
			break;

		}

	}

}
