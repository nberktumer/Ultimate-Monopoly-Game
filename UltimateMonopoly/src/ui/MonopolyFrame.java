package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import domain.MonopolyGameController;
import domain.Observer;
import domain.PlayerObservable;

public class MonopolyFrame extends JFrame implements ActionListener, Observer {

	MonopolyGameController controller;
	GamePanel gamePanel;
	EndGamePanel endGamePanel;

	private boolean animating = false;
	private Timer t;
	private boolean gameOver = false;

	public MonopolyFrame(MonopolyGameController controller) {
		setLayout(null);
		this.controller = controller;
		setResizable(false);

		String osname = System.getProperty("os.name").toLowerCase();
		boolean mac = osname.startsWith("mac os x");

		if (mac) {
			getContentPane().setPreferredSize(new Dimension(1280, 720));
		} else {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}

		pack();
		setVisible(true);
		getContentPane().setSize(new Dimension(getWidth(), getHeight()));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		MainMenuPanel mainMenuPanel = new MainMenuPanel(getContentPane().getWidth(), getContentPane().getHeight());
		getContentPane().add(mainMenuPanel);
		mainMenuPanel.initAncestor();
		mainMenuPanel.repaint();

		setBackground(Color.BLACK);
		PlayerObservable.getInstance().addObserver(this);
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	public void switchToMainMenuPanel() {
		getContentPane().removeAll();
		MainMenuPanel mainMenuPanel = new MainMenuPanel(getContentPane().getWidth(), getContentPane().getHeight());
		getContentPane().add(mainMenuPanel);

		mainMenuPanel.initAncestor();

		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "gameEnded", null);

		repaint();

	}

	public void switchToGamePanel(ArrayList<String> usernames, String newgamename) {
		gameOver = false;
		getContentPane().removeAll();
		gamePanel = new GamePanel(getContentPane().getWidth(), getContentPane().getHeight());
		getContentPane().add(gamePanel);

		if (usernames != null) {
			controller.initNewGamePlayers(usernames, newgamename);
		}
		gamePanel.initAncestor();
		changeButtonState("rollDice", true);

		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "gameStarted", null);

		repaint();
	}

	public void switchToEndGamePanel(String winnerName, String gameName) {
		getContentPane().removeAll();
		endGamePanel = new EndGamePanel(getContentPane().getWidth(), getContentPane().getHeight());
		endGamePanel.setGamename(gameName);
		endGamePanel.setWinnerName(winnerName);
		getContentPane().add(endGamePanel);

		endGamePanel.initAncestor();

		repaint();
	}

	public void loadGame(String filename) {
		controller.loadGame(filename);
	}

	public void changeButtonState(String buttonname, boolean enabled) {
		gamePanel.changeButtonState(buttonname, enabled);

	}

	public void printMessage(String title, String msg) {
		JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void update(String key, String action, Object obj) {
		if (key == "game") {
			switch (action) {
			case "outOfBalanceMessage":
				printMessage("Not Enough Money", "You don't have enough money to purchase this square.");
				break;
			case "invalidBidMessage":
				printMessage("Invalid Offer", "Please make a valid offer.");
				break;
			case "updateLog":
				gamePanel.updateLog(obj.toString());
				break;
			case "highlightSquare":
				gamePanel.highlightSquare((int) obj);
				break;
			case "highlightSquaresAuction":
				gamePanel.setInputType("AuctionSelect");
				gamePanel.highlightSquares((ArrayList<Integer>) obj);
				break;
			case "highlightSquaresTeleport":
				gamePanel.setInputType("TeleportSelect");
				gamePanel.highlightSquares((ArrayList<Integer>) obj);
				break;
			case "highlightSquaresColor":
				gamePanel.setInputType("ColorSelect");
				gamePanel.highlightSquares((ArrayList<Integer>) obj);
				break;
			case "highlightCommunityChestCards":
				gamePanel.setInputType("CommunityChestCardSelect");
				gamePanel.setHightlightCommunityChestCards(true);
				break;
			case "highlightChanceCards":
				gamePanel.setInputType("ChanceCardSelect");
				gamePanel.setHightlightChanceCards(true);
				break;
			case "initAuction":
				gamePanel.switchLeftPanel("auctionpanel");
				gamePanel.setAuctionInfo((HashMap<String, Object>) obj);
				break;
			case "inJail":
				gamePanel.add(gamePanel.jailRollDiceButton);
				break;
			case "outJail":
				gamePanel.remove(gamePanel.jailRollDiceButton);
				break;
			case "startBankruptcy":
				gamePanel.switchLeftPanel("bankruptcypanel");
				gamePanel.setBankruptcyInfo((int) obj);
				break;
			case "afterCardPlayed":
				gamePanel.afterCardPlayed();
				break;
			case "gameEnded":
				gameOver = true;
				if (t != null)
					t.stop();
				switchToEndGamePanel(obj.toString(), controller.getGamename());
				break;
			case "stateChange":
				gamePanel.changeButtonStates(obj.toString());
				break;
			default:
				changeButtonState(action, Boolean.valueOf(obj.toString()));
				break;
			}
		} else if (key == "MonopolyBotView" && obj.toString() == "idle" && !animating && !gameOver) {
			ShakeAnimationListener shakeAnimListener = new ShakeAnimationListener();
			t = new Timer(10, shakeAnimListener);
			t.start();
			animating = true;
		}
	}

	public class ShakeAnimationListener implements ActionListener {
		private int x = 0;
		private final int maxDist = 20;
		private final int maxAnimCount = 6;
		private int direction = 1;
		private int animCount = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			MonopolyFrame.this.repaint();
			if (animCount >= maxAnimCount) {
				t.stop();
				animating = false;
				MonopolyFrame.this.getContentPane().setLocation(0, 0);
				return;
			}
			if (Math.abs(x) >= maxDist) {
				direction *= -1;
				animCount++;
			}
			x = (direction > 0 ? x + 5 : x - 5);
			MonopolyFrame.this.getContentPane().setLocation(x, 0);
		}
	}
}
