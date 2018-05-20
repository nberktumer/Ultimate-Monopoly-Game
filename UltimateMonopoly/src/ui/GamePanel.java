package ui;

import java.awt.BasicStroke;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import domain.Logger;
import domain.MonopolyGameController;
import domain.PlayerObservable;

public class GamePanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	protected MonopolyGameController controller;
	protected MonopolyFrame parent;
	protected MonopolyBotView bot;

	private LeftGameFlowPanel leftGameFlowPanel;
	private LeftPropertyInfoPanel leftPropertiesPanel;
	private LeftAuctionPanel leftAuctionPanel;
	private LeftBankruptcyPanel leftBankruptcyPanel;

	private boolean hightlightCommunityChestCards = false;
	private boolean hightlightChanceCards = false;

	private String gamename;
	private String inputType = "";
	private ArrayList<String> logs;

	private int frameHeight;
	private int frameWidth;
	private int squareHeight;
	private int squareWidth;
	private int tokenSize;
	private int boardMarginLeft = 0;
	private int boardMarginTop = 0;
	private String displayCardName = "";

	private List<SquareHolder> boardComponents = new ArrayList<>();
	private List<Integer> highlightedSquareIndices = new ArrayList<>();
	private SquareHolder activatedHolder;
	private List<Image> tokenImages = new ArrayList<>();
	private List<Image> cardBackImages = new ArrayList<>();
	private Map<String, Image> cardImages = new HashMap<>();
	protected Map<String, JButton> buttons = new HashMap<>();

	protected JButton jailRollDiceButton;
	protected JButton playCardButton;
	private JTextArea logTextArea;
	protected Choice currentPlayerProperties;

	public GamePanel(int w, int h) {
		//setLayout(null);
		frameHeight = h;
		frameWidth = w;

		squareHeight = (int) (frameHeight / 8.5) + 1;
		squareWidth = squareHeight / 2;
		tokenSize = squareWidth / 3;
		boardMarginLeft = (frameWidth - squareWidth * 17) / 2;

		setSize(frameWidth, frameHeight);
		setVisible(true);
		setBackground(Color.decode("#AFE7CC"));

		leftGameFlowPanel = new LeftGameFlowPanel(boardMarginLeft, frameHeight, this);
		leftGameFlowPanel.setBounds(0, 0, boardMarginLeft, frameHeight);
		leftGameFlowPanel.setBackground(Color.decode("#AFE7CC"));

		leftPropertiesPanel = new LeftPropertyInfoPanel(boardMarginLeft, frameHeight, this);
		leftPropertiesPanel.setBounds(0, 0, boardMarginLeft, frameHeight);
		leftPropertiesPanel.setBackground(Color.decode("#AFE7CC"));

		leftAuctionPanel = new LeftAuctionPanel(boardMarginLeft, frameHeight, this);
		leftAuctionPanel.setBounds(0, 0, boardMarginLeft, frameHeight);
		leftAuctionPanel.setBackground(Color.decode("#AFE7CC"));

		leftBankruptcyPanel = new LeftBankruptcyPanel(boardMarginLeft, frameHeight, this);
		leftBankruptcyPanel.setBounds(0, 0, boardMarginLeft, frameHeight);
		leftBankruptcyPanel.setBackground(Color.decode("#AFE7CC"));

		add(leftGameFlowPanel);
		bot = new MonopolyBotView();
		bot.setBounds(frameWidth - boardMarginLeft + 2, 200, boardMarginLeft, 400);
		bot.setBackground(Color.decode("#AFE7CC"));
		bot.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		add(bot);

		initBoardComponents();
		initButtons();
		initCardImages();

		addMouseListener(this);
		addMouseMotionListener(this);

		logs = new ArrayList<String>();
		logTextArea = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(logTextArea);
		logTextArea.setEditable(false);
		logTextArea.setBackground(Color.decode("#AFE7CC"));
		scrollPane.setBounds(frameWidth - boardMarginLeft, frameHeight - 300, boardMarginLeft, 300);
		logTextArea.setFont(new Font("Calibri", Font.PLAIN, 16));
		logTextArea.addMouseListener(this);
		logTextArea.addMouseMotionListener(this);
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		logTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		logTextArea.setBackground(new Color(59, 89, 182));
		logTextArea.setForeground(Color.WHITE);
		logTextArea.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);

	}

	public void setHightlightCommunityChestCards(boolean hightlightCommunityChestCards) {
		this.hightlightCommunityChestCards = hightlightCommunityChestCards;
	}

	public void setHightlightChanceCards(boolean hightlightChanceCards) {
		this.hightlightChanceCards = hightlightChanceCards;
	}

	public MonopolyBotView getMonopolyBotView() {
		return bot;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBoard(g);
		drawPieces(g);
		drawCards(g);
	}

	private void drawBoard(Graphics g) {
		List<Integer> passList = new ArrayList<>(Arrays.asList(61, 81, 99, 105, 111, 117));
		for (int i = 0; i < boardComponents.size(); i++) {
			SquareHolder s = boardComponents.get(i);
			if (!passList.contains(i)) {
				g.drawImage(s.img, s.posX, s.posY, s.width, s.height, null);
			}
		}

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(activatedHolder.posX, activatedHolder.posY, activatedHolder.width, activatedHolder.height);
		g2.setColor(Color.black);
		g2.setStroke(oldStroke);

		for (Integer i : highlightedSquareIndices) {
			SquareHolder sh = boardComponents.get(i);
			g2.setColor(Color.yellow);
			oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(5));
			g2.drawRect(sh.posX, sh.posY, sh.width, sh.height);
			g2.setColor(Color.black);
			g2.setStroke(oldStroke);

		}

		int imageWidth = boardComponents.get(103).posX - 5
				- (boardComponents.get(119).posX + boardComponents.get(119).width + 5);

		if (hightlightCommunityChestCards) {
			g2.setColor(Color.RED);
			oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(5));
			g2.drawRect(boardComponents.get(119).posX + boardComponents.get(119).width + 5,
					boardComponents.get(119).posY + 5, imageWidth / 2, 85);
			g2.setColor(Color.black);
			g2.setStroke(oldStroke);
		} else if (hightlightChanceCards) {
			g2.setColor(Color.RED);
			oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(5));
			g2.drawRect(boardComponents.get(103).posX - imageWidth / 2, boardComponents.get(103).posY + 5,
					imageWidth / 2 - 5, 85);
			g2.setColor(Color.black);
			g2.setStroke(oldStroke);
		}

	}

	private void drawPieces(Graphics g) {
		for (int i = 0; i < tokenImages.size(); i++) {
			int pieceindex = controller.getIndices().get(i);

			SquareHolder sq = boardComponents.get(pieceindex);
			int x = (int) (sq.posX + sq.width * 0.5 * (i % 2));

			int y;
			if (i < 2) {
				y = (int) (sq.posY + sq.height * 0.1);
			} else {
				y = (int) (sq.posY + sq.height * 0.6);

			}
			g.drawImage(tokenImages.get(i), x, y, tokenSize, tokenSize, null);
		}
	}

	private void drawCards(Graphics g) {
		if (displayCardName == "") {
			int imageWidth = boardComponents.get(103).posX - 5
					- (boardComponents.get(119).posX + boardComponents.get(119).width + 5);
			g.drawImage(cardBackImages.get(0), boardComponents.get(119).posX + boardComponents.get(119).width + 5,
					boardComponents.get(119).posY + 5, imageWidth / 2, 85, null);
			g.drawImage(cardBackImages.get(1), boardComponents.get(103).posX - imageWidth / 2,
					boardComponents.get(103).posY + 5, imageWidth / 2 - 5, 85, null);
		} else {
			int imageWidth = boardComponents.get(103).posX - 5
					- (boardComponents.get(119).posX + boardComponents.get(119).width + 5);
			g.drawImage(cardImages.get(displayCardName),
					boardComponents.get(119).posX + boardComponents.get(119).width + 5,
					boardComponents.get(119).posY + 5, imageWidth, 150, null);

		}
	}

	public void initAncestor() {
		parent = (MonopolyFrame) SwingUtilities.getWindowAncestor(this);
		controller = parent.controller;
		scalePieces();
		leftGameFlowPanel.controller = controller;
		leftPropertiesPanel.controller = controller;
		leftAuctionPanel.controller = controller;
		leftBankruptcyPanel.controller = controller;
		gamename = controller.getGamename();
		leftGameFlowPanel.setGamename(gamename);
		initTradeChoice();
		logTextArea.append("Welcome to Monopoly! \n");
	}

	public void scalePieces() {
		for (int i = 0; i < controller.getPieces().size(); i++) {
			tokenImages.add(controller.getPieces().get(i).getScaledInstance(tokenSize, tokenSize, Image.SCALE_SMOOTH));
		}
	}

	private void initCardImages() {

		try {
			cardBackImages.add(ImageIO.read(new File("assets/cards/CommunityChest.png")));
			cardBackImages.add(ImageIO.read(new File("assets/cards/Chance.png")));
			cardImages.put("bankErrorInYourFavor", ImageIO.read(new File("assets/cards/bankErrorInYourFavor.png")));
			cardImages.put("beKindRewind", ImageIO.read(new File("assets/cards/beKindRewind.png")));
			cardImages.put("goToJail", ImageIO.read(new File("assets/cards/goToJail.png")));
			cardImages.put("advanceToIllinoisAve", ImageIO.read(new File("assets/cards/advanceToIllinoisAve.png")));
			cardImages.put("advanceToThePayCorner", ImageIO.read(new File("assets/cards/advanceToThePayCorner.png")));
			cardImages.put("hurricaneMakesLandfall", ImageIO.read(new File("assets/cards/hurricaneMakesLandfall.png")));
			cardImages.put("mardiGras", ImageIO.read(new File("assets/cards/mardiGras.png")));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initButtons() {
		int buttonHeight = 30;
		int buttonWidth = 130;

		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds((int) (getWidth() - buttonWidth), 0, buttonWidth, buttonHeight);
		mainMenuButton.setActionCommand("mainMenu");
		mainMenuButton.addActionListener(this);
		mainMenuButton.setBackground(Color.red);
		mainMenuButton.addMouseListener(this);
		mainMenuButton.addMouseMotionListener(this);
		mainMenuButton.setForeground(Color.WHITE);
		mainMenuButton.setFocusPainted(false);
		mainMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttons.put("mainMenu", mainMenuButton);
		mainMenuButton.setOpaque(true);
		mainMenuButton.setBorderPainted(false);
		add(mainMenuButton);

		JButton saveGameButton = new JButton("Save Game");
		saveGameButton.setBounds((int) (getWidth() - boardMarginLeft), 0, buttonWidth, buttonHeight);
		saveGameButton.setActionCommand("saveGame");
		saveGameButton.addActionListener(this);
		saveGameButton.addMouseListener(this);
		saveGameButton.addMouseMotionListener(this);
		saveGameButton.setBackground(new Color(59, 89, 182));
		saveGameButton.setForeground(Color.WHITE);
		saveGameButton.setFocusPainted(false);
		saveGameButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttons.put("saveGame", saveGameButton);
		saveGameButton.setOpaque(true);
		saveGameButton.setBorderPainted(false);
		add(saveGameButton);

		jailRollDiceButton = new JButton("Roll Dice");
		jailRollDiceButton.setBounds((int) ((getWidth() - buttonWidth) / 2),
				boardComponents.get(111).posY - buttonHeight - 50, buttonWidth, buttonHeight);
		jailRollDiceButton.setActionCommand("jailRollDice");
		jailRollDiceButton.addActionListener(this);
		jailRollDiceButton.addMouseListener(this);
		jailRollDiceButton.addMouseMotionListener(this);
		jailRollDiceButton.setBackground(new Color(59, 89, 182));
		jailRollDiceButton.setForeground(Color.WHITE);
		jailRollDiceButton.setFocusPainted(false);
		jailRollDiceButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		jailRollDiceButton.setOpaque(true);
		jailRollDiceButton.setBorderPainted(false);

		playCardButton = new JButton("Play Card!");
		playCardButton.setBounds((int) ((getWidth() - buttonWidth) / 2),
				boardComponents.get(111).posY - buttonHeight - 50, buttonWidth, buttonHeight);
		playCardButton.setActionCommand("playCard");
		playCardButton.addActionListener(this);
		playCardButton.addMouseListener(this);
		playCardButton.addMouseMotionListener(this);
		playCardButton.setBackground(new Color(59, 89, 182));
		playCardButton.setForeground(Color.WHITE);
		playCardButton.setFocusPainted(false);
		playCardButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		playCardButton.setOpaque(true);
		playCardButton.setBorderPainted(false);

		currentPlayerProperties = new Choice();
		currentPlayerProperties.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftPropertiesPanel.add(currentPlayerProperties);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "mainMenu":
			parent.switchToMainMenuPanel();
			controller.reset();
			break;
		case "saveGame":
			controller.saveGame();
			break;
		case "jailRollDice":
			controller.rollDiceAndMove();
			break;
		case "playCard":
			controller.playCard(displayCardName);
			break;
		}

		repaint();
	}

	protected void switchLeftPanel(String panelname) {

		remove(getComponentAt(0, 0));
		switch (panelname) {
		case "gameflowpanel":
			add(leftGameFlowPanel);
			break;
		case "propertiespanel":
			setComboBoxPropertyList("propertiespanel");
			leftBankruptcyPanel.remove(currentPlayerProperties);
			leftPropertiesPanel.add(currentPlayerProperties);
			add(leftPropertiesPanel);
			break;
		case "bankruptcypanel":
			setComboBoxPropertyList("bankruptcypanel");
			leftPropertiesPanel.remove(currentPlayerProperties);
			leftBankruptcyPanel.add(currentPlayerProperties);
			add(leftBankruptcyPanel);
			break;
		case "auctionpanel":
			add(leftAuctionPanel);
			break;
		}
	}

	public void setComboBoxPropertyList(String panelname, String selector) {
		setComboBoxPropertyList(panelname);
		currentPlayerProperties.select(selector);
		leftBankruptcyPanel.setSelectedItem(selector);
		repaint();
	}

	public void setComboBoxPropertyList(String panelname) {

		currentPlayerProperties.removeAll();
		ArrayList<PropertyInformation> propertyInfo = controller.getCurrentPlayerPropertiesInformation();
		ArrayList<String> propertyNames = new ArrayList<String>();
		for (PropertyInformation pi : propertyInfo) {
			propertyNames.add(pi.getName());
		}
		Collections.sort(propertyNames);

		for (String name : propertyNames) {
			currentPlayerProperties.addItem(name);
		}

		if (currentPlayerProperties.getSelectedItem() != null) {
			leftPropertiesPanel.setSelectedItem(currentPlayerProperties.getSelectedItem().toString());
			leftBankruptcyPanel.setSelectedItem(currentPlayerProperties.getSelectedItem().toString());
		}

		if (panelname.equals("propertiespanel")) {
			currentPlayerProperties.setBounds(0, 130, leftPropertiesPanel.getWidth(), 30);
		} else if (panelname.equals("bankruptcypanel")) {
			currentPlayerProperties.setBounds(0, 300, leftPropertiesPanel.getWidth(), 30);

		}
		currentPlayerProperties.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (currentPlayerProperties.getSelectedItem() != null) {
					leftPropertiesPanel.setSelectedItem(currentPlayerProperties.getSelectedItem().toString());
					leftBankruptcyPanel.setSelectedItem(currentPlayerProperties.getSelectedItem().toString());
				}
				leftPropertiesPanel.repaint();
				leftBankruptcyPanel.repaint();
			}

		});

		if (currentPlayerProperties.getItemCount() == 0) {
			leftPropertiesPanel.setSelectedItem(null);
			leftBankruptcyPanel.setSelectedItem(null);
			leftPropertiesPanel.repaint();
			leftBankruptcyPanel.repaint();
		}

		currentPlayerProperties.addMouseListener(this);
		currentPlayerProperties.addMouseMotionListener(this);

	}

	private void initBoardComponents() {
		try {
			int i = 0;
			int j = 0;
			int pos = 0; // 0 - TOP / 1 - RIGHT / 2 - BOTTOM / 3 - LEFT
			int type = 1; // 1 - CORNER / 0 - EDGE
			int limit = 14;
			int startPos = 0;
			String imgName = "";

			for (int index = 0; index < 120; index++) {
				if (index < 56) {
					limit = 14;
					startPos = 0;
				} else if (index >= 56 && index < 96) {
					limit = 10;
					startPos = squareHeight;
				} else {
					limit = 6;
					startPos = 2 * squareHeight;
				}

				if (i == 0 && j == 0) {
					pos = 0;
					type = 1;
				} else if (i == limit && j == 0) {
					pos = 1;
					type = 1;
				} else if (i == limit && j == limit) {
					pos = 2;
					type = 1;
				} else if (i == 0 && j == limit) {
					pos = 3;
					type = 1;
				} else {
					type = 0;
				}

				int posX = (i == 0 || i % 14 == 1 ? i * squareHeight
						: i * squareWidth + Math.abs(squareHeight - squareWidth)) + boardMarginLeft + startPos;
				int posY = (j == 0 || j % 14 == 1 ? j * squareHeight
						: j * squareWidth + Math.abs(squareHeight - squareWidth)) + boardMarginTop + startPos;

				int width = calculateWidth(pos, type);
				int height = calculateHeight(pos, type);

				switch (index) {
				case 7:
					imgName = "7-61-99";
					width = squareWidth;
					height = 3 * squareHeight;
					break;
				case 35:
					imgName = "35-81-111";
					width = squareWidth;
					height = 3 * squareHeight;
					posY -= 2 * squareHeight;
					break;
				case 71:
					imgName = "71-105";
					width = 2 * squareHeight;
					height = squareWidth + 1;
					posX -= squareHeight;
					posY -= 1;
					break;
				case 91:
					imgName = "91-117";
					width = 2 * squareHeight;
					height = squareWidth;
					break;
				case 61:
				case 99:
				case 81:
				case 111:
				case 105:
				case 117:
					imgName = "7-61-99";
					break;
				default:
					imgName = "" + index;
					break;
				}
				System.out.println("Initializing image " + imgName);
				Image img = ImageIO.read(new File("assets/board/" + imgName + ".png"));
				img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				boardComponents.add(new SquareHolder(posX, posY, width, height, img));

				switch (pos) {
				case 0:
					i++;
					break;
				case 1:
					j++;
					break;
				case 2:
					i--;
					break;
				case 3:
					j--;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		activatedHolder = boardComponents.get(76);

	}

	private int calculateWidth(int pos, int type) {
		return (type == 1 || pos == 3 || pos == 1 ? squareHeight : squareWidth);
	}

	private int calculateHeight(int pos, int type) {
		return (type == 1 || pos == 0 || pos == 2 ? squareHeight : squareWidth);
	}

	public int getTokenSize() {
		return tokenSize;
	}

	public void changeButtonState(String buttonname, boolean enabled) {
		buttons.get(buttonname).setEnabled(enabled);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "resetIdle", null);
		int xpos = arg0.getX();
		int ypos = arg0.getY();

		if (highlightedSquareIndices.size() > 0) {

			for (int i = 0; i < highlightedSquareIndices.size(); i++) {
				SquareHolder s = boardComponents.get(highlightedSquareIndices.get(i));
				if (xpos > s.posX && xpos < s.posX + s.width && ypos > s.posY && ypos < s.posY + s.height) {

					if (inputType.equals("AuctionSelect")) {
						controller.startAuction(highlightedSquareIndices.get(i));
					} else if (inputType.equals("TeleportSelect")) {
						highlightedSquareIndices.clear();
						controller.teleport(i);
						repaint();
					} else if (inputType.equals("ColorSelect")) {
						controller.downgradeAllPropertiesOfColor(highlightedSquareIndices.get(i));
						highlightedSquareIndices.clear();
						repaint();
					}

				}
			}

		}

		int imageWidth = boardComponents.get(103).posX - 5
				- (boardComponents.get(119).posX + boardComponents.get(119).width + 5);

		if (inputType.equals("CommunityChestCardSelect")) {
			int squareXPos = boardComponents.get(119).posX + boardComponents.get(119).width + 5;
			int squareYPos = boardComponents.get(119).posY + 5;
			int squareWidth = imageWidth / 2;
			int squareHeight = 85;

			if (xpos > squareXPos && xpos < squareXPos + squareWidth && ypos > squareYPos
					&& ypos < squareYPos + squareHeight) {
				inputType = "";
				hightlightCommunityChestCards = false;
				displayCardName = controller.getRandomCard("CommunityChest");
				add(playCardButton);
				repaint();
			}
		} else if (inputType.equals("ChanceCardSelect")) {
			int squareXPos = boardComponents.get(103).posX - imageWidth / 2;
			int squareYPos = boardComponents.get(103).posY + 5;
			int squareWidth = imageWidth / 2 - 5;
			int squareHeight = 85;

			if (xpos > squareXPos && xpos < squareXPos + squareWidth && ypos > squareYPos
					&& ypos < squareYPos + squareHeight) {
				inputType = "";
				hightlightChanceCards = false;
				displayCardName = controller.getRandomCard("Chance");
				add(playCardButton);
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "resetIdle", null);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "resetIdle", null);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "resetIdle", null);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "resetIdle", null);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "resetIdle", null);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		PlayerObservable.getInstance().notifyObservers("MonopolyBot", "resetIdle", null);
	}

	public void updateLog(String line) {
		logs = Logger.getInstance().getLog();
		logTextArea.append(line + "\n");
		repaint();
	}

	public void highlightSquare(int index) {
		activatedHolder = boardComponents.get(index);
	}

	public void highlightSquares(ArrayList<Integer> availableIndices) {
		this.highlightedSquareIndices = availableIndices;
	}

	public void setAuctionInfo(HashMap<String, Object> obj) {
		highlightedSquareIndices.clear();
		leftAuctionPanel.setAuctionInfo(obj);

	}

	public void setBankruptcyInfo(int amt) {
		leftBankruptcyPanel.setBankruptcyInfo(amt);
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public void afterCardPlayed() {
		displayCardName = "";
		remove(playCardButton);
		repaint();

	}

	public void changeButtonStates(String stateName) {
		ButtonStateHandler.modifyButtonStates(buttons, stateName);
	}

	public void initTradeChoice() {

		ArrayList<String> list = controller.getPlayerInformation(true);
		ArrayList<String> nameList = new ArrayList<String>();
		for (String info : list) {
			nameList.add(info.split(" ")[1]);

		}
		leftPropertiesPanel.initPlayerList(nameList);
	}

}
