package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class PlayerListPanel extends JPanel {
	
	List<String> playerInformation;
	
	public PlayerListPanel() {
		setLayout(null);
	}
	
	public void setPlayerInformation(List<String> playerInformation) {
		this.playerInformation = playerInformation;
	}

	@Override
	public void paintComponent(Graphics g) {
	//	super.paintComponent(g);
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawString("Players: ", (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));
		for (int i = 0; i < playerInformation.size(); i++) {
			String[] info = playerInformation.get(i).split(" ");
			if (info[0].equals("true")) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.black);
			}

			g.drawString(info[1] + " " + info[2], (int) (this.getWidth() * 0.1),
					(int) (this.getHeight() * (0.1 + (0.02 * (i + 1)))));
		}
	}
}
