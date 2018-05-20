package domain;

import java.util.Observable;

import ui.SoundManager;

public class MonopolyBot implements Observer, Runnable {
	private int count = 0;
	private boolean gameStarted = false;
	private String state = "";

	public MonopolyBot() {
		PlayerObservable.getInstance().addObserver(this);
	}

	// Counter runs in the MonopolyBot thread.
	@Override
	public void run() {
		while (true) {
			try {
				if (gameStarted) {
					count++;
					if (count >= 15) {
						if(count == 15)
							SoundManager.getInstance().playSound("idle");
						PlayerObservable.getInstance().notifyObservers("MonopolyBotView", "animate", "idle");
						state = "idle";
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(String key, String action, Object obj) {
		if (key == "MonopolyBot") {
			switch (action) {
			case "transaction":
				PlayerObservable.getInstance().notifyObservers("MonopolyBotView", "animate", "money");
				SoundManager.getInstance().playSound("transaction");
				break;
			case "jail":
				PlayerObservable.getInstance().notifyObservers("MonopolyBotView", "animate", "jail");
				SoundManager.getInstance().playSound("jail");
				break;
			case "bankrupt":
				PlayerObservable.getInstance().notifyObservers("MonopolyBotView", "animate", "bankrupt");
				break;
			case "win":
				PlayerObservable.getInstance().notifyObservers("MonopolyBotView", "animate", "win");
				SoundManager.getInstance().playSound("win");
				break;
			case "resetIdle":
				synchronized (this) {
					count = 0;
				}

				if (state.equals("idle")) {
					PlayerObservable.getInstance().notifyObservers("MonopolyBotView", "stopIdleAnimation", "");
				}

				break;
			case "gameStarted":
				synchronized (this) {
					gameStarted = true;
					count = 0;
				}
				break;
			case "gameEnded":
				synchronized (this) {
					gameStarted = false;
				}
				break;
			}
			state = action;
		}
	}

}
