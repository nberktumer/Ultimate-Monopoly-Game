package ui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.MonopolyGameController;

public class LeftPropertyInfoPanel extends JPanel implements ActionListener {

	GamePanel parent;
	MonopolyGameController controller;
	PropertyInformation selectedItem;
	JButton goBackButton = new JButton("Go Back");
	JButton upgradeButton = new JButton("Upgrade");
	JButton downgradeButton = new JButton("Downgrade");
	JButton sellButton = new JButton("Sell");
	JButton tradeButton = new JButton("Trade");
	JButton removeTradeButton = new JButton("X");
	JButton finishTradeButton = new JButton("Finish!");
	Choice playerToTradeWith = new Choice();
	JTextField tradeAmountField;
	boolean tradeFieldIsOpen = false;
	private ArrayList<String> playerNames = new ArrayList<String>();

	public LeftPropertyInfoPanel(int width, int height, GamePanel parent) {
		setLayout(null);
		setSize(width, height);
		this.parent = parent;
		initButtons();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		paintWithInfo(g);
	}

	public void setSelectedItem(String pInfo) {
		selectedItem = null;
		for (PropertyInformation p : controller.getCurrentPlayerPropertiesInformation()) {
			if (p.getName().equals(pInfo))
				selectedItem = p;
		}
	}

	public void paintWithInfo(Graphics g) {
		Font f = new Font("Tahoma", Font.PLAIN, 20);
		FontMetrics metrics = g.getFontMetrics(f);
		g.setFont(f);
		g.setColor(new Color(59, 89, 182));
		String text = controller.getCurrentPlayer().getName() + " ($" + controller.getCurrentPlayer().getBalance()
				+ ")";
		g.drawString(text, (getWidth() - metrics.stringWidth(text)) / 2, 100);
		if (selectedItem != null) {
			g.drawString("Current Rent Price: $" + selectedItem.getCurrentRentPrice(), (int) (this.getWidth() * 0.1),
					200);
			g.drawString("House Count: " + selectedItem.getHouse(), (int) (this.getWidth() * 0.1), 230);
			g.drawString("Hotel Count: " + selectedItem.getHotel(), (int) (this.getWidth() * 0.1), 260);
			g.drawString("Skyscraper Count: " + selectedItem.getSkyscraper(), (int) (this.getWidth() * 0.1), 290);
			g.drawString(
					"Upgrade Cost: "
							+ (selectedItem.getUpgradeCost() == -1 ? "N/A" : "$" + selectedItem.getUpgradeCost()),
					(int) (this.getWidth() * 0.1), 320);
			String downgradeAmount = selectedItem.getDowngradeamount() == -1 ? "N/A"
					: "$" + selectedItem.getDowngradeamount();
			g.drawString("Downgrade Price: " + downgradeAmount, (int) (this.getWidth() * 0.1), 350);
			g.drawString("Sell Price: $" + selectedItem.getSellamount(), (int) (this.getWidth() * 0.1), 380);

		}

		if (tradeFieldIsOpen) {
			g.setFont(f);
			String tradeText = "Trade Price";
			g.drawString(tradeText, (getWidth() - metrics.stringWidth(tradeText)) / 2, 650);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "upgrade":
			if (parent.currentPlayerProperties.getSelectedItem() == null) {
				parent.parent.printMessage("Error", "Upgrade failed!");

			} else {
				if (!controller.upgradeTitleDeed(parent.currentPlayerProperties.getSelectedItem().toString())) {
					parent.parent.printMessage("Error", "Upgrade failed!");
				} else {
					setSelectedItem(selectedItem.getName());
				}
			}
			break;

		case "goBack":
			removeTradeUI();
			parent.switchLeftPanel("gameflowpanel");
			break;

		case "sellproperties":
			if (parent.currentPlayerProperties.getSelectedItem() == null) {
				parent.parent.printMessage("Error", "Sell failed!");
			} else {

				if (parent.currentPlayerProperties.getSelectedItem() != null) {
					controller.sellTitleDeed(parent.currentPlayerProperties.getSelectedItem().toString());
				} else {
					parent.parent.printMessage("Error", "Sell failed!");
				}

				parent.setComboBoxPropertyList("propertiespanel");

			}
			break;
		case "downgradeproperties":
			if (parent.currentPlayerProperties.getSelectedItem() == null) {
				parent.parent.printMessage("Error", "Downgrade failed!");
			} else {

				if (parent.currentPlayerProperties.getSelectedItem() != null) {
					if (!controller.downgradeTitleDeed(parent.currentPlayerProperties.getSelectedItem().toString()))
						parent.parent.printMessage("Error", "Downgrade failed!");

				} else {
					parent.parent.printMessage("Error", "Downgrade failed!");
				}
				setSelectedItem(selectedItem.getName());
			}
			break;
		case "addTrade":
			addTradeUI();
			break;
		case "removeTrade":
			removeTradeUI();
			break;
		case "finishTrade":
			finishTrade();
		}

		repaint();
		parent.repaint();

	}

	public void initButtons() {
		int buttonHeight = 30;
		int buttonWidth = 150;

		goBackButton.setBounds(0, 0, buttonWidth, buttonHeight);
		goBackButton.setActionCommand("goBack");
		goBackButton.addActionListener(this);
		goBackButton.addMouseListener(parent);
		goBackButton.addMouseMotionListener(parent);
		goBackButton.setBackground(new Color(59, 89, 182));
		goBackButton.setForeground(Color.WHITE);
		goBackButton.setFocusPainted(false);
		goBackButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("goBack", goBackButton);
		goBackButton.setOpaque(true);
		goBackButton.setBorderPainted(false);
		add(goBackButton);

		upgradeButton.setBounds(getWidth() / 2 - buttonWidth / 2, 420, buttonWidth, buttonHeight);
		upgradeButton.setActionCommand("upgrade");
		upgradeButton.addActionListener(this);
		upgradeButton.addMouseListener(parent);
		upgradeButton.addMouseMotionListener(parent);
		upgradeButton.setBackground(new Color(59, 89, 182));
		upgradeButton.setForeground(Color.WHITE);
		upgradeButton.setFocusPainted(false);
		upgradeButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("upgrade", upgradeButton);
		upgradeButton.setOpaque(true);
		upgradeButton.setBorderPainted(false);
		add(upgradeButton);

		downgradeButton.setBounds(getWidth() / 2 - buttonWidth / 2, 460, buttonWidth, buttonHeight);
		downgradeButton.setActionCommand("downgradeproperties");
		downgradeButton.addActionListener(this);
		downgradeButton.addMouseListener(parent);
		downgradeButton.addMouseMotionListener(parent);
		downgradeButton.setBackground(new Color(59, 89, 182));
		downgradeButton.setForeground(Color.WHITE);
		downgradeButton.setFocusPainted(false);
		downgradeButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("downgradeproperties", downgradeButton);
		downgradeButton.setOpaque(true);
		downgradeButton.setBorderPainted(false);
		add(downgradeButton);

		sellButton.setBounds(getWidth() / 2 - buttonWidth / 2, 500, buttonWidth, buttonHeight);
		sellButton.setActionCommand("sellproperties");
		sellButton.addActionListener(this);
		sellButton.addMouseListener(parent);
		sellButton.addMouseMotionListener(parent);
		sellButton.setBackground(new Color(59, 89, 182));
		sellButton.setForeground(Color.WHITE);
		sellButton.setFocusPainted(false);
		sellButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("sellproperties", sellButton);
		sellButton.setOpaque(true);
		sellButton.setBorderPainted(false);
		add(sellButton);

		tradeButton.setBounds(getWidth() / 2 - buttonWidth / 2, 540, buttonWidth, buttonHeight);
		tradeButton.setActionCommand("addTrade");
		tradeButton.addActionListener(this);
		tradeButton.addMouseListener(parent);
		tradeButton.addMouseMotionListener(parent);
		tradeButton.setBackground(new Color(59, 89, 182));
		tradeButton.setForeground(Color.WHITE);
		tradeButton.setFocusPainted(false);
		tradeButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("addTrade", tradeButton);
		tradeButton.setOpaque(true);
		tradeButton.setBorderPainted(false);
		add(tradeButton);

		removeTradeButton.setBounds(getWidth() - 30, 570, 20, 20);
		removeTradeButton.setActionCommand("removeTrade");
		removeTradeButton.addActionListener(this);
		removeTradeButton.addMouseListener(parent);
		removeTradeButton.addMouseMotionListener(parent);
		removeTradeButton.setBackground(Color.RED);
		removeTradeButton.setForeground(Color.WHITE);
		removeTradeButton.setFocusPainted(false);
		removeTradeButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("removeTrade", removeTradeButton);
		removeTradeButton.setOpaque(true);
		removeTradeButton.setBorderPainted(false);
		removeTradeButton.setBorder(null);

		playerToTradeWith = new Choice();
		playerToTradeWith.setBounds(0, 600, getWidth(), buttonHeight);
		playerToTradeWith.setFont(new Font("Tahoma", Font.BOLD, 12));

		tradeAmountField = new JTextField();
		tradeAmountField.setBounds(0, 660, getWidth(), buttonHeight);
		tradeAmountField.setBackground(new Color(59, 89, 182));
		tradeAmountField.setForeground(Color.WHITE);
		tradeAmountField.setFont(new Font("Tahoma", Font.BOLD, 12));

		finishTradeButton.setBounds(getWidth() / 2 - buttonWidth / 2, 700, buttonWidth, buttonHeight);
		finishTradeButton.setActionCommand("finishTrade");
		finishTradeButton.addActionListener(this);
		finishTradeButton.addMouseListener(parent);
		finishTradeButton.addMouseMotionListener(parent);
		finishTradeButton.setBackground(new Color(59, 89, 182));
		finishTradeButton.setForeground(Color.WHITE);
		finishTradeButton.setFocusPainted(false);
		finishTradeButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("finishTrade", finishTradeButton);
		finishTradeButton.setOpaque(true);
		finishTradeButton.setBorderPainted(false);

	}

	public void initPlayerList(ArrayList<String> playerNames) {
		for (String name : playerNames) {
			playerToTradeWith.add(name);
		}

	}

	public void addTradeUI() {
		add(removeTradeButton);
		add(tradeAmountField);
		add(playerToTradeWith);
		add(finishTradeButton);
		tradeButton.setEnabled(false);
		tradeFieldIsOpen = true;

	}

	public void removeTradeUI() {
		remove(removeTradeButton);
		remove(tradeAmountField);
		remove(playerToTradeWith);
		remove(finishTradeButton);
		tradeButton.setEnabled(true);
		tradeFieldIsOpen = false;

	}

	private void finishTrade() {
		if (parent.currentPlayerProperties.getSelectedItem() == null) {
			parent.parent.printMessage("Error", "No title deed is selected!");
		} else {
			if (controller.tradeWithYourself(playerToTradeWith.getSelectedItem())) {
				parent.parent.printMessage("Error", "You can not trade with yourself!");
			} else {
				if (controller.playerIsBankrupt(playerToTradeWith.getSelectedItem())) {
					parent.parent.printMessage("Error", "Player is bankrupt! Trade failed!");
					return;
				}
				if (controller.attemptToTrade(parent.currentPlayerProperties.getSelectedItem(),
						playerToTradeWith.getSelectedItem(), tradeAmountField.getText())) {
					parent.setComboBoxPropertyList("propertiespanel");
					removeTradeUI();
				} else {
					parent.parent.printMessage("Error", "Invalid amount entered!");
				}

			}

		}

	}

}
