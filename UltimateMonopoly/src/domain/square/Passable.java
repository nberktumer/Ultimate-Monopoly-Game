package domain.square;

import domain.MonopolyGameController;

public interface Passable {

	public void passedBy(MonopolyGameController monopolyGame, int roll);
}
