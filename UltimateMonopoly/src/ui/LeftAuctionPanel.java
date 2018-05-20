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
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Logger;
import domain.MonopolyGameController;

public class LeftAuctionPanel extends JPanel implements ActionListener {

	GamePanel parent;
	MonopolyGameController controller;

	Image auctionLogo;

	private JTextField offerTextField = new JTextField();
	private ArrayList<String> playersInformation;
	private String titleDeedName;

	public LeftAuctionPanel(int width, int height, GamePanel parent) {
		setLayout(null);
		setSize(width, height);
		this.parent = parent;
		try {
			auctionLogo = ImageIO.read(new File("gui_images/auction.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initButtons();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(auctionLogo, getWidth() / 2 - auctionLogo.getWidth(this) / 2, 20, this);

		Font f = new Font("Tahoma", Font.BOLD, 24);
		FontMetrics metrics = g.getFontMetrics(f);
		g.setFont(f);
		String text = titleDeedName;
		g.drawString(text, (getWidth() - metrics.stringWidth(text)) / 2, auctionLogo.getHeight(this) + 100);

		g.setFont(new Font("Tahoma", Font.PLAIN, 20));
		g.drawString("Highest Bet: $" + controller.auctionGetHighestBid(), (int) (getWidth() * 0.1),
				auctionLogo.getHeight(this) + 140);
		g.drawString("By " + controller.auctionGetHighestBetName(), (int) (getWidth() * 0.1),
				auctionLogo.getHeight(this) + 170);

		for (int i = 0; i < playersInformation.size(); i++) {
			String[] info = playersInformation.get(i).split(" ");
			if (controller.auctionGetCurrentPlayerIndex() == i) {
				g.setColor(Color.red);
			} else {
				g.setColor(new Color(59, 89, 182));
			}

			g.drawString(info[1] + " " + info[2], (int) (this.getWidth() * 0.15),
					auctionLogo.getHeight(this) + 200 + i * 30);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case "pass":
			nextPlayer();
			break;
		case "offer":
			makeOffer();
			break;
		}

		repaint();
		parent.repaint();

	}

	public void initButtons() {
		int startPositionY = auctionLogo.getHeight(this) + 330;

		int buttonHeight = 30;
		int buttonWidth = 100;

		offerTextField.setBounds(getWidth() / 2 - buttonWidth - 5, startPositionY, buttonWidth * 2 + 10, buttonHeight);
		offerTextField.setBackground(new Color(59, 89, 182));
		offerTextField.setForeground(Color.WHITE);
		offerTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(offerTextField);

		JButton passButton = new JButton("Pass");
		passButton.setBounds(getWidth() / 2 + 5, startPositionY + 40, buttonWidth, buttonHeight);
		passButton.setActionCommand("pass");
		passButton.addActionListener(this);
		passButton.addMouseListener(parent);
		passButton.addMouseMotionListener(parent);
		passButton.setBackground(new Color(59, 89, 182));
		passButton.setForeground(Color.WHITE);
		passButton.setFocusPainted(false);
		passButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("pass", passButton);
		passButton.setOpaque(true);
		passButton.setBorderPainted(false);
		add(passButton);

		JButton offerButton = new JButton("Offer");
		offerButton.setBounds(getWidth() / 2 - buttonWidth - 5, startPositionY + 40, buttonWidth, buttonHeight);
		offerButton.setActionCommand("offer");
		offerButton.addActionListener(this);
		offerButton.addMouseListener(parent);
		offerButton.addMouseMotionListener(parent);
		offerButton.setBackground(new Color(59, 89, 182));
		offerButton.setForeground(Color.WHITE);
		offerButton.setFocusPainted(false);
		offerButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		parent.buttons.put("offer", offerButton);
		offerButton.setOpaque(true);
		offerButton.setBorderPainted(false);
		add(offerButton);

		offerTextField.requestFocus();

	}

	public void setAuctionInfo(HashMap<String, Object> param) {
		playersInformation = (ArrayList<String>) param.get("players");
		titleDeedName = (String) param.get("square");
	}

	private boolean validateInput() {
		String input = offerTextField.getText();
		int offer = -1;
		try {
			offer = Integer.parseInt(input);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return controller.auctionValidateBetOffer(offer);

	}

	private void makeOffer() {
		String input = offerTextField.getText();
		int offer = -1;

		try {
			offer = Integer.parseInt(input);
		} catch (NumberFormatException nfe) {
			return;
		}

		if (validateInput()) {
			controller.auctionMakeNewHighestOffer(offer);
			offerTextField.setText("");
			offerTextField.requestFocus();
			if (controller.auctionShouldTerminate())
				terminateAuction();
		}

	}

	private void nextPlayer() {
		offerTextField.setText("");
		offerTextField.requestFocus();
		controller.auctionNextPlayer();
		if (controller.auctionShouldTerminate())
			terminateAuction();

	}

	private void terminateAuction() {
		controller.terminateAuction(titleDeedName);
		parent.switchLeftPanel("gameflowpanel");

	}

}
