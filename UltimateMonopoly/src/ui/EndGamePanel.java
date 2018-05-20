package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import domain.MonopolyGameController;

public class EndGamePanel extends JPanel implements ActionListener {
	private String winnerName;
	protected MonopolyGameController controller;
	protected MonopolyFrame parent;

	private String gameName;
	private List<Animatable> animations = new ArrayList<>();
	private BufferedImage cupImage;
	private Timer animationTimer;

	private int frameHeight;
	private int frameWidth;

	public EndGamePanel(int w, int h) {
		setLayout(null);
		frameHeight = h;
		frameWidth = w;

		setSize(frameWidth, frameHeight);
		setVisible(true);
		setBackground(Color.decode("#AFE7CC"));

		try {
			cupImage = ImageIO.read(new File("assets/emojis/trophy.png"));
			cupImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);

		} catch (IOException e) {
			e.printStackTrace();
		}
		WinTrophyAnimationGenerator trophyAnim = new WinTrophyAnimationGenerator();
		trophyAnim.initAnimation(w / 2 + 250, h / 2 - 120, 120, 120, w, h, "trophy");
		animations.add(trophyAnim);

		trophyAnim = new WinTrophyAnimationGenerator();
		trophyAnim.initAnimation(w / 2 - 250, h / 2 - 120, 120, 120, w, h, "trophy");
		animations.add(trophyAnim);

		trophyAnim = new WinTrophyAnimationGenerator();
		trophyAnim.initAnimation(w / 2 - 200, h / 2 - 120, 120, 120, w, h, "label");
		animations.add(trophyAnim);

		animationTimer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}

		});
		animationTimer.start();

		initButtons();
	}

	public void animate(Graphics g) {
		for (int i = animations.size() - 1; i >= 0; i--) {
			WinTrophyAnimationGenerator a = (WinTrophyAnimationGenerator) animations.get(i);
			if (a.framesAvailable()) {
				if (a.getEmojiName() == "trophy")
					g.drawImage(cupImage, a.getX(), a.getY(), a.getWidth(), a.getHeight(), this);
				else
					drawCenteredString(g, winnerName,
							new Rectangle(frameWidth / 2 - 230, frameHeight / 2 - 180, 460, 120),
							new Font("Tahoma", Font.BOLD, a.getLabelSize()));
				a.nextFrame();
			} else {
				animations.remove(i);
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		animate(g);

		drawCenteredString(g, winnerName + " has won the game \"" + gameName + "\"!",
				new Rectangle(0, frameHeight / 2 - 15, frameWidth, 30), new Font("Tahoma", Font.PLAIN, 20));
	}

	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);

		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

		g.setFont(font);
		g.drawString(text, x, y);
	}

	public void initAncestor() {
		parent = (MonopolyFrame) SwingUtilities.getWindowAncestor(this);
		controller = parent.controller;
	}

	public void initButtons() {
		int buttonHeight = 30;
		int buttonWidth = 130;

		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds((int) (getWidth() - buttonWidth), 0, buttonWidth, buttonHeight);
		mainMenuButton.setActionCommand("mainMenu");
		mainMenuButton.addActionListener(this);
		mainMenuButton.setBackground(Color.red);
		mainMenuButton.setForeground(Color.WHITE);
		mainMenuButton.setFocusPainted(false);
		mainMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		mainMenuButton.setOpaque(true);
		mainMenuButton.setBorderPainted(false);
		add(mainMenuButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "mainMenu":
			parent.switchToMainMenuPanel();
			controller.reset();
			break;
		}

		repaint();
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGamename(String gameName) {
		this.gameName = gameName;
	}

}