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

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.Logger;
import domain.MonopolyGameController;

public class LeftBankruptcyPanel extends JPanel implements ActionListener {
	GamePanel parent;
	MonopolyGameController controller;

	Image bankruptLogo;

	PropertyInformation selectedItem;

	JButton goBackButton = new JButton("Go Back");
	JButton sellButton = new JButton("Sell");
	JButton downgradeButton = new JButton("Downgrade");

	private final String cantGoBackErrorMessage = "You can't go back until you "
			+ "have paid up your debt or you have no more properties to sell!";
	private String playername = "";
	private int moneyOwed = 0;

	public LeftBankruptcyPanel(int width, int height, GamePanel parent) {
		setLayout(null);
		setSize(width, height);
		this.parent = parent;
		try {
			bankruptLogo = ImageIO.read(new File("gui_images/bankrupt.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initButtons();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bankruptLogo, getWidth() / 2 - bankruptLogo.getWidth(this) / 2, 20, this);

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
		String text = playername + " owes $" + moneyOwed + "! (has $" + controller.bankruptedPlayerCurrentMoney() + ")";
		g.drawString(text, (getWidth() - metrics.stringWidth(text)) / 2, 290);

		if (selectedItem != null) {
			g.drawString("House Count: " + selectedItem.getHouse(), (int) (this.getWidth() * 0.1), 350);
			g.drawString("Hotel Count: " + selectedItem.getHotel(), (int) (this.getWidth() * 0.1), 380);
			g.drawString("Skyscraper Count: " + selectedItem.getSkyscraper(), (int) (this.getWidth() * 0.1), 410);
			g.drawString("Sell Price: $" + selectedItem.getSellamount(), (int) (this.getWidth() * 0.1), 440);
			String downgradeAmount = selectedItem.getDowngradeamount() == -1 ? "N/A"
					: "$" + selectedItem.getDowngradeamount();
			g.drawString("Downgrade Price: " + downgradeAmount, (int) (this.getWidth() * 0.1), 470);
		}
		String deptPayPossible = controller.bankruptcyCanDebtBePaid() ? "Debt can be paid!" : "Debt can't be paid!";
		g.drawString(deptPayPossible, (int) (this.getWidth() * 0.1), 500);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "goBack":
			if (allowGoBack()) {
				if (controller.bankruptcyCanDebtBePaid()) {
					controller.terminateBankruptcyProcess();
				} else {
					controller.bankruptPlayer(playername);
				}
				parent.switchLeftPanel("gameflowpanel");
			} else {
				parent.parent.printMessage("Error", cantGoBackErrorMessage);

			}
			break;

		case "sellbankruptcy":
			if (parent.currentPlayerProperties.getSelectedItem() == null) {
				parent.parent.printMessage("Error", "Sell failed!");
			} else {

				if (parent.currentPlayerProperties.getSelectedItem() != null) {
					controller.sellTitleDeed(parent.currentPlayerProperties.getSelectedItem().toString());
				} else {
					parent.parent.printMessage("Error", "Sell failed!");
				}

				parent.setComboBoxPropertyList("bankruptcypanel");

			}
			break;

		case "downgradebankruptcy":
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

		sellButton.setBounds(getWidth() / 2 - buttonWidth / 2, 530, buttonWidth, buttonHeight);
		sellButton.setActionCommand("sellbankruptcy");
		sellButton.addActionListener(this);
		sellButton.addMouseListener(parent);
		sellButton.addMouseMotionListener(parent);
		sellButton.setBackground(new Color(59, 89, 182));
		sellButton.setForeground(Color.WHITE);
		sellButton.setFocusPainted(false);
		sellButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("sellbankruptcy", sellButton);
		sellButton.setOpaque(true);
		sellButton.setBorderPainted(false);
		add(sellButton);

		downgradeButton.setBounds(getWidth() / 2 - buttonWidth / 2, 570, buttonWidth, buttonHeight);
		downgradeButton.setActionCommand("downgradebankruptcy");
		downgradeButton.addActionListener(this);
		downgradeButton.addMouseListener(parent);
		downgradeButton.addMouseMotionListener(parent);
		downgradeButton.setBackground(new Color(59, 89, 182));
		downgradeButton.setForeground(Color.WHITE);
		downgradeButton.setFocusPainted(false);
		downgradeButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("downgradebankruptcy", downgradeButton);
		downgradeButton.setOpaque(true);
		downgradeButton.setBorderPainted(false);
		add(downgradeButton);

	}

	public void setBankruptcyInfo(int moneyOwed) {
		this.playername = controller.getCurrentPlayer().getName();
		this.moneyOwed = moneyOwed;
	}

	public boolean allowGoBack() {
		return parent.currentPlayerProperties.getItemCount() == 0 || controller.bankruptcyCanDebtBePaid();

	}

}
