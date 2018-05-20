package ui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import domain.MonopolyGameController;

public class MainMenuPanel extends JPanel implements ActionListener {

	private final String[] numberOfPlayersArr = { "2", "3", "4" };
	private MonopolyGameController controller;
	private MonopolyFrame parent;

	private ArrayList<String> usernames = new ArrayList<String>();
	private Image rulesImage;
	private Image logoImage;
	private JButton newGameButton;
	private JButton loadGameButton;
	private JButton quitGameButton;
	private JButton continueButton;
	private JButton mainMenuButton;
	private Choice numberOfPlayersList;
	private JTextField inputUsernameTextField = new JTextField();

	private int panelWidth;
	private int panelHeight;
	private int numberOfPlayers = -1;
	private int currentPlayerInputIndex = 1;

	private boolean settingsPanel = false;
	private boolean invalidFileName = false;
	private boolean numberOfPlayersSet = false;
	private boolean finalized = false;

	public MainMenuPanel(int panelWidth, int panelHeight) {
		setLayout(null);
		setSize(panelWidth, panelHeight);
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		setBackground(Color.ORANGE);
		setVisible(true);
		initButtons();
		try {
			rulesImage = ImageIO.read(new File("gui_images/rules.png"));
			logoImage = ImageIO.read(new File("gui_images/logo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logoImage, getWidth() / 2 - logoImage.getWidth(this) / 2, getHeight() / 10, this);
		if (settingsPanel) {

			g.drawImage(rulesImage, getWidth() - rulesImage.getWidth(this), getHeight() - rulesImage.getHeight(this),
					this);

			g.setColor(new Color(59, 89, 182));
			g.setFont(new Font("Tahoma", Font.PLAIN, 20));

			if (!numberOfPlayersSet) {
				g.drawString("Number Of Players: ", panelWidth / 2 - 75, panelHeight / 2 - 75);
			} else {
				g.drawString("Number Of Players Set To " + numberOfPlayers + "!", panelWidth / 2 + 300,
						panelHeight / 2 - 200);
				for (int i = 0; i < numberOfPlayers; i++) {
					g.drawString(usernames.get(i), panelWidth / 2 + 300, panelHeight / 2 - 200 + (1 + i) * 30);
				}

				if (!finalized) {
					g.drawString("Please enter name for Player " + currentPlayerInputIndex + ":", panelWidth / 2 - 75,
							panelHeight / 2 - 45);
				} else {
					g.drawString("Please enter name for Game:", panelWidth / 2 - 75, panelHeight / 2 - 45);
					if (invalidFileName) {
						g.setColor(Color.red);
						g.drawString("Invalid File Name!", panelWidth / 2 - 75, panelHeight / 2 + 80);
						g.setColor(new Color(59, 89, 182));
					}

				}
			}
		} else {

		}

	}

	public void initAncestor() {
		parent = (MonopolyFrame) SwingUtilities.getWindowAncestor(this);
		controller = parent.controller;
	}

	public void initButtons() {
		System.out.println(panelWidth);
		System.out.println(panelHeight);

		int buttonWidth = panelWidth / 6;
		int buttonHeight = panelHeight / 18;

		newGameButton = new JButton("New Game");

		newGameButton.setBounds(panelWidth / 2 - buttonWidth / 2, panelHeight / 2 - buttonHeight / 2 - 75, buttonWidth,
				buttonHeight);
		newGameButton.setActionCommand("newGame");
		newGameButton.addActionListener(this);
		newGameButton.setBackground(new Color(59, 89, 182));
		newGameButton.setForeground(Color.WHITE);
		newGameButton.setFocusPainted(false);
		newGameButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		newGameButton.setOpaque(true);
		newGameButton.setBorderPainted(false);
		loadGameButton = new JButton("Load Game");
		loadGameButton.setBounds(panelWidth / 2 - buttonWidth / 2, panelHeight / 2 - buttonHeight / 2 + 25, buttonWidth,
				buttonHeight);
		loadGameButton.setActionCommand("loadGame");
		loadGameButton.addActionListener(this);
		loadGameButton.setBackground(new Color(59, 89, 182));
		loadGameButton.setForeground(Color.WHITE);
		loadGameButton.setFocusPainted(false);
		loadGameButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		loadGameButton.setOpaque(true);
		loadGameButton.setBorderPainted(false);
		quitGameButton = new JButton("Quit Game");
		quitGameButton.setBounds(panelWidth / 2 - buttonWidth / 2, panelHeight / 2 - buttonHeight / 2 + 125,
				buttonWidth, buttonHeight);
		quitGameButton.setActionCommand("quitGame");
		quitGameButton.addActionListener(this);
		quitGameButton.setBackground(new Color(59, 89, 182));
		quitGameButton.setForeground(Color.WHITE);
		quitGameButton.setFocusPainted(false);
		quitGameButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		quitGameButton.setOpaque(true);
		quitGameButton.setBorderPainted(false);
		add(newGameButton);
		add(loadGameButton);
		add(quitGameButton);

	}

	public void changeButtons() {
		removeAll();
		int buttonWidth = 150;
		int buttonHeight = 30;

		continueButton = new JButton("Continue!");
		continueButton.setBounds(panelWidth / 2 - buttonWidth / 2, panelHeight / 2 - buttonHeight / 2, buttonWidth,
				buttonHeight);
		continueButton.setActionCommand("setNumberOfPlayers");
		continueButton.addActionListener(this);
		continueButton.setBackground(new Color(59, 89, 182));
		continueButton.setForeground(Color.WHITE);
		continueButton.setFocusPainted(false);
		continueButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		continueButton.setOpaque(true);
		continueButton.setBorderPainted(false);
		mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds((int) (getWidth() - buttonWidth), 0, buttonWidth, buttonHeight);
		mainMenuButton.setActionCommand("mainMenu");
		mainMenuButton.addActionListener(this);
		mainMenuButton.setBackground(Color.red);
		mainMenuButton.setForeground(Color.WHITE);
		mainMenuButton.setFocusPainted(false);
		mainMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		mainMenuButton.setOpaque(true);
		mainMenuButton.setBorderPainted(false);
		numberOfPlayersList = new Choice();
		for (String n : numberOfPlayersArr) {
			numberOfPlayersList.add(n);
		}
		numberOfPlayersList.setBounds(panelWidth / 2 - buttonWidth / 2, panelHeight / 2 - buttonHeight / 2 - 50,
				buttonWidth, buttonHeight);
		numberOfPlayersList.setFont(new Font("Tahoma", Font.BOLD, 12));

		add(numberOfPlayersList);
		add(continueButton);
		add(mainMenuButton);

		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "newGame":
			settingsPanel = true;
			changeButtons();
			break;
		case "loadGame":
			loadGameSequence();
			break;
		case "quitGame":
			controller.quitGame();
			break;
		case "setNumberOfPlayers":
			remove(continueButton);
			remove(numberOfPlayersList);
			numberOfPlayersSet = true;
			numberOfPlayers = (Integer.parseInt((String) numberOfPlayersList.getSelectedItem()));
			initUsernameInput();
			repaint();
			break;
		case "continueNameInput":
			if (nameCheck(inputUsernameTextField.getText())) {
				usernames.set(currentPlayerInputIndex - 1, inputUsernameTextField.getText());
				inputUsernameTextField.setText("");
				currentPlayerInputIndex++;
				if (currentPlayerInputIndex == numberOfPlayers + 1) {
					finalized = true;
					remove(continueButton);
					JButton startButton = new JButton("Start!");
					int buttonWidth = 150;
					int buttonHeight = 30;
					startButton.setBounds(panelWidth / 2 - buttonWidth / 2,
							panelHeight / 2 - buttonHeight / 2 + buttonHeight, buttonWidth, buttonHeight);
					startButton.setActionCommand("start");
					startButton.addActionListener(this);
					startButton.setBackground(new Color(59, 89, 182));
					startButton.setForeground(Color.WHITE);
					startButton.setFocusPainted(false);
					startButton.setFont(new Font("Tahoma", Font.BOLD, 12));
					startButton.setOpaque(true);
					startButton.setBorderPainted(false);
					add(startButton);
				}
			}
			inputUsernameTextField.requestFocus();
			repaint();
			break;
		case "start":
			if (tryToStartGame()) {
				parent.loadGame("InitialGameState.null");
				parent.switchToGamePanel(usernames, inputUsernameTextField.getText());
			} else {
				invalidFileName = true;
				repaint();
			}
			break;
		case "mainMenu":
			parent.switchToMainMenuPanel();
			controller.reset();
			break;
		}

	}

	public void initUsernames() {
		for (int i = 0; i < numberOfPlayers; i++) {
			usernames.add("waiting for input..");
		}
	}

	public void initUsernameInput() {
		int buttonWidth = 150;
		int buttonHeight = 30;
		initUsernames();
		inputUsernameTextField.setBounds(panelWidth / 2 - buttonWidth / 2, panelHeight / 2 - buttonHeight / 2 - 15,
				buttonWidth, buttonHeight);
		inputUsernameTextField.setBackground(new Color(59, 89, 182));
		inputUsernameTextField.setForeground(Color.WHITE);
		inputUsernameTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(inputUsernameTextField);

		continueButton = new JButton("Continue!");
		continueButton.setBounds(panelWidth / 2 - buttonWidth / 2, panelHeight / 2 - buttonHeight / 2 + buttonHeight,
				buttonWidth, buttonHeight);
		continueButton.setActionCommand("continueNameInput");
		continueButton.addActionListener(this);
		continueButton.setBackground(new Color(59, 89, 182));
		continueButton.setForeground(Color.WHITE);
		continueButton.setFocusPainted(false);
		continueButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		continueButton.setOpaque(true);
		continueButton.setBorderPainted(false);
		add(continueButton);

		inputUsernameTextField.requestFocus();

	}

	public boolean nameCheck(String name) {
		if (!usernames.contains(name) && name != null && !name.toLowerCase().equals("-") && !name.contains(" ")
				&& name.length() != 0 && name.length() <= 10)
			return true;

		return false;

	}

	public void loadGameSequence() {
		JFileChooser fileChooser = new JFileChooser("./GameStates");
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				if (f.isDirectory()) {
					return true;
				} else {
					if (f.getAbsolutePath().toLowerCase().endsWith("null")
							&& !f.getName().equals("InitialGameState.null"))
						return true;
				}

				return false;
			}
		});
		int choice = fileChooser.showOpenDialog(null);

		if (choice == fileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile.getAbsolutePath().toLowerCase().endsWith("null")) {
				parent.loadGame(selectedFile.getName());
				parent.switchToGamePanel(null,
						selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")));
				parent.update("game", "highlightSquare", parent.controller.currentPlayerIndex());
			} else {
				JOptionPane.showMessageDialog(parent, "Please do not try to hack us :(", "Not Cool Bro!",
						JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	public boolean tryToStartGame() {
		String gamename = inputUsernameTextField.getText();
		if (gamename == null) {
			return false;
		}

		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		boolean hasSpecialChar = p.matcher(gamename).find();

		if (hasSpecialChar || gamename.length() == 0 || gamename.length() > 10) {
			return false;
		}

		return parent.controller.isDuplicateGameName(gamename);
	}
}
