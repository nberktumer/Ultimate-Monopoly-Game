package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import domain.MonopolyGameController;

public class LeftGameFlowPanel extends JPanel implements ActionListener {

	GamePanel parent;
	MonopolyGameController controller;

	private List<Image> diceImages = new ArrayList<>();

	private final String cheatCode = "null";
	private String typedCode = "";
	private String gamename;

	private boolean cheatMode = false;

	private JTextField teleportTextField;
	private JButton teleportButton = new JButton("Teleport");
	private JButton removeCheatUIButton = new JButton("X");

	public LeftGameFlowPanel(int width, int height, GamePanel parent) {
		setLayout(null);
		setSize(width, height);
		this.parent = parent;
		teleportTextField = new JTextField();
		initButtons();
		initDiceImages();
		addKeyBindings();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Font f = new Font("Tahoma", Font.BOLD, 30);
		FontMetrics metrics = g.getFontMetrics(f);
		g.setFont(f);
		String text = gamename;
		g.drawString(text, (getWidth() - metrics.stringWidth(text)) / 2, this.getHeight() / 20);

		g.setColor(new Color(59, 89, 182));
		g.setFont(new Font("Tahoma", Font.PLAIN, 20));
		g.drawString("Players: ", (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));

		ArrayList<String> playerInformation = controller.getPlayerInformation(true);

		for (int i = 0; i < playerInformation.size(); i++) {
			String[] info = playerInformation.get(i).split(" ");
			if (info[0].equals("true")) {
				g.setColor(Color.red);
			} else {
				g.setColor(new Color(59, 89, 182));
			}

			if (info[3].equals("false")) {
				g.drawString(info[1] + " $" + info[2], (int) (this.getWidth() * 0.1),
						(int) (this.getHeight() * (0.1 + (0.03 * (i + 1)))));
			} else {
				g.drawString(info[1] + " " + "(R.I.P.)", (int) (this.getWidth() * 0.1),
						(int) (this.getHeight() * (0.1 + (0.03 * (i + 1)))));

			}
		}

		drawDiceResults(g);
	}

	private void drawDiceResults(Graphics g) {

		ArrayList<String> diceResults = controller.getDiceResultsAsString();

		for (int i = 0; i < 3; i++) {
			int diceDisplaySize = 30;
			int margin = 5;
			int firstmargin = (int) (getWidth() / 2 - 50);
			int xposition = firstmargin + ((diceDisplaySize + margin) * i);
			int yposition = (int) (getHeight() * 0.5) + 30 * 6;
			switch (diceResults.get(i)) {
			case "ONE":
				g.drawImage(diceImages.get(0), xposition, yposition, diceDisplaySize, diceDisplaySize, null);
				break;
			case "TWO":
				g.drawImage(diceImages.get(1), xposition, yposition, diceDisplaySize, diceDisplaySize, null);
				break;
			case "THREE":
				g.drawImage(diceImages.get(2), xposition, yposition, diceDisplaySize, diceDisplaySize, null);
				break;
			case "FOUR":
				g.drawImage(diceImages.get(3), xposition, yposition, diceDisplaySize, diceDisplaySize, null);
				break;
			case "FIVE":
				g.drawImage(diceImages.get(4), xposition, yposition, diceDisplaySize, diceDisplaySize, null);
				break;
			case "SIX":
				g.drawImage(diceImages.get(5), xposition, yposition, diceDisplaySize, diceDisplaySize, null);
				break;
			case "MRMONOPOLY":
				g.drawImage(diceImages.get(6), xposition, yposition, diceDisplaySize, diceDisplaySize, null);
				break;
			case "BUS":
				g.drawImage(diceImages.get(7), xposition, yposition, diceDisplaySize, diceDisplaySize, null);
				break;
			default:
				g.drawImage(diceImages.get(8), xposition, yposition, diceDisplaySize, diceDisplaySize, null);
				break;
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "endTurn":
			controller.nextTurn();
			break;
		case "rollDice":
			controller.rollDiceAndMove();
			break;
		case "teleport":
			int teleportIndex = getTeleportIndex();
			if (teleportIndex != -1)
				controller.teleport(teleportIndex);
			break;
		case "purchase":
			controller.purchaseSquare();
			break;
		case "auction":
			controller.auctionSquare();
			break;
		case "mainMenu":
			parent.parent.switchToMainMenuPanel();
			controller.reset();
			break;
		case "saveGame":
			controller.saveGame();
			break;
		case "viewProperties":
			parent.switchLeftPanel("propertiespanel");
			break;
		case "removeCheatUI":
			removeCheatUI();
			break;
		}

		repaint();
		parent.repaint();

	}

	private int getTeleportIndex() {
		String input = teleportTextField.getText();
		int index = -1;

		try {
			index = Integer.parseInt(input);
		} catch (NumberFormatException nfe) {
			return index;
		}

		if (index <= 119 && index >= 0)
			return index;

		return -1;

	}

	public void initButtons() {
		int positionX = (int) (getWidth() * 0.4);
		int startPositionY = (int) (getHeight() * 0.5);

		int buttonHeight = 30;
		int buttonWidth = 200;

		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.setBounds(getWidth() / 2 - buttonWidth / 2, startPositionY, buttonWidth, buttonHeight);
		endTurnButton.setActionCommand("endTurn");
		endTurnButton.addActionListener(this);
		endTurnButton.setEnabled(false);
		endTurnButton.addMouseListener(parent);
		endTurnButton.addMouseMotionListener(parent);
		endTurnButton.setBackground(new Color(59, 89, 182));
		endTurnButton.setForeground(Color.WHITE);
		endTurnButton.setFocusPainted(false);
		endTurnButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("endTurn", endTurnButton);
		endTurnButton.setOpaque(true);
		endTurnButton.setBorderPainted(false);
		add(endTurnButton);

		JButton rollDiceButton = new JButton("Roll Dice and Move");
		rollDiceButton.setBounds(getWidth() / 2 - buttonWidth / 2, startPositionY + buttonHeight + 5, buttonWidth,
				buttonHeight);
		rollDiceButton.setActionCommand("rollDice");
		rollDiceButton.addActionListener(this);
		rollDiceButton.setEnabled(false);
		rollDiceButton.addMouseListener(parent);
		rollDiceButton.addMouseMotionListener(parent);
		rollDiceButton.setBackground(new Color(59, 89, 182));
		rollDiceButton.setForeground(Color.WHITE);
		rollDiceButton.setFocusPainted(false);
		rollDiceButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("rollDice", rollDiceButton);
		rollDiceButton.setOpaque(true);
		rollDiceButton.setBorderPainted(false);
		add(rollDiceButton);

		JButton purchaseButton = new JButton("Buy!");
		purchaseButton.setBounds(getWidth() / 2 - buttonWidth / 2, startPositionY + (buttonHeight * 2) + 10,
				(buttonWidth / 2) - 3, buttonHeight);
		purchaseButton.setActionCommand("purchase");
		purchaseButton.addActionListener(this);
		purchaseButton.setEnabled(false);
		purchaseButton.addMouseListener(parent);
		purchaseButton.addMouseMotionListener(parent);
		purchaseButton.setBackground(new Color(59, 89, 182));
		purchaseButton.setForeground(Color.WHITE);
		purchaseButton.setFocusPainted(false);
		purchaseButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("purchase", purchaseButton);
		purchaseButton.setOpaque(true);
		purchaseButton.setBorderPainted(false);
		add(purchaseButton);

		JButton auctionButton = new JButton("Auction!");
		auctionButton.setBounds((getWidth() / 2 - buttonWidth / 2) + (buttonWidth / 2) + 3,
				startPositionY + (buttonHeight * 2) + 10, (buttonWidth / 2) - 3, buttonHeight);
		auctionButton.setActionCommand("auction");
		auctionButton.addActionListener(this);
		auctionButton.setEnabled(false);
		auctionButton.addMouseListener(parent);
		auctionButton.addMouseMotionListener(parent);
		auctionButton.setBackground(new Color(59, 89, 182));
		auctionButton.setForeground(Color.WHITE);
		auctionButton.setFocusPainted(false);
		auctionButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("auction", auctionButton);
		auctionButton.setOpaque(true);
		auctionButton.setBorderPainted(false);
		add(auctionButton);

		JButton propertiesButton = new JButton("View Properties");
		propertiesButton.setBounds(getWidth() / 2 - buttonWidth / 2, startPositionY + (buttonHeight * 3) + 15,
				buttonWidth, buttonHeight);
		propertiesButton.setActionCommand("viewProperties");
		propertiesButton.addActionListener(this);
		propertiesButton.addMouseListener(parent);
		propertiesButton.addMouseMotionListener(parent);
		propertiesButton.setBackground(new Color(59, 89, 182));
		propertiesButton.setForeground(Color.WHITE);
		propertiesButton.setFocusPainted(false);
		propertiesButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("viewProperties", propertiesButton);
		propertiesButton.setOpaque(true);
		propertiesButton.setBorderPainted(false);
		add(propertiesButton);

		int yposition = (int) (getHeight() * 0.5) + 30 * 6 + 70;

		teleportTextField.setBounds(0, yposition, getWidth(), buttonHeight);
		teleportTextField.setBackground(new Color(59, 89, 182));
		teleportTextField.setForeground(Color.WHITE);
		teleportTextField.setFont(new Font("Tahoma", Font.BOLD, 12));

		teleportButton.setBounds(getWidth() / 2 - buttonWidth / 2, yposition + 40, buttonWidth, buttonHeight);
		teleportButton.setActionCommand("teleport");
		teleportButton.addActionListener(this);
		teleportButton.addMouseListener(parent);
		teleportButton.addMouseMotionListener(parent);
		teleportButton.setBackground(new Color(59, 89, 182));
		teleportButton.setForeground(Color.WHITE);
		teleportButton.setFocusPainted(false);
		teleportButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("teleport", teleportButton);
		teleportButton.setOpaque(true);
		teleportButton.setBorderPainted(false);

		removeCheatUIButton.setBounds(getWidth() - 30, yposition - 40, 20, 20);
		removeCheatUIButton.setActionCommand("removeCheatUI");
		removeCheatUIButton.addActionListener(this);
		removeCheatUIButton.addMouseListener(parent);
		removeCheatUIButton.addMouseMotionListener(parent);
		removeCheatUIButton.setBackground(Color.RED);
		removeCheatUIButton.setForeground(Color.WHITE);
		removeCheatUIButton.setFocusPainted(false);
		removeCheatUIButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("removeCheatUI", removeCheatUIButton);
		removeCheatUIButton.setOpaque(true);
		removeCheatUIButton.setBorderPainted(false);
		removeCheatUIButton.setBorder(null);

	}

	private void initDiceImages() {
		try {
			diceImages.add(ImageIO.read(new File("assets/dice/diceone.png")));
			diceImages.add(ImageIO.read(new File("assets/dice/dicetwo.png")));
			diceImages.add(ImageIO.read(new File("assets/dice/dicethree.png")));
			diceImages.add(ImageIO.read(new File("assets/dice/dicefour.png")));
			diceImages.add(ImageIO.read(new File("assets/dice/dicefive.png")));
			diceImages.add(ImageIO.read(new File("assets/dice/dicesix.png")));
			diceImages.add(ImageIO.read(new File("assets/dice/dicemrmonopoly.png")));
			diceImages.add(ImageIO.read(new File("assets/dice/dicebus.png")));
			diceImages.add(ImageIO.read(new File("assets/dice/diceunknown.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	private void addKeyBindings() {
		for (char c = 'a'; c <= 'z'; c++) {
			char t = c;
			getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(t), "keyEnter:" + t);
			getActionMap().put("keyEnter:" + t, new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent e) {
					typedCode += t;
					if (!cheatMode && cheatCode.startsWith(typedCode)) {
						if (cheatCode.equals(typedCode)) {
							cheatMode = true;
							addCheatUI();
						}
					} else {
						typedCode = "";
					}

				}
			});
		}
	}

	private void addCheatUI() {
		add(teleportTextField);
		add(teleportButton);
		add(removeCheatUIButton);
		repaint();
	}

	private void removeCheatUI() {
		remove(teleportTextField);
		remove(teleportButton);
		remove(removeCheatUIButton);
		typedCode = "";
		cheatMode = false;

	}

}
