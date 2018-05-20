import domain.Logger;
import domain.MonopolyBot;
import domain.MonopolyGameController;
import domain.Player;
import domain.PlayerObservable;
import ui.MonopolyFrame;

public class MonopolyGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MonopolyGameController handler = new MonopolyGameController();
		MonopolyFrame frame = new MonopolyFrame(handler);
		handler.addObserver(frame);
		Logger.getInstance().addObserver(frame);
		new Thread(new MonopolyBot()).start();

	}

}
