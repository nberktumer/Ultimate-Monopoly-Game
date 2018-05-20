package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import domain.card.Card;
import domain.square.CabCompanySquare;
import domain.square.CompanySquare;
import domain.square.RailRoadSquare;
import domain.square.Square;
import domain.square.TitleDeedSquare;

public class SaveLoadManager {
	public static void saveGame(MonopolyGameController controller) {
		try {
			String filepath = "GameStates/" + controller.gamename + ".null";

			PrintWriter wr = new PrintWriter(new FileWriter(filepath));
			for (int lineNumber = 1; lineNumber <= 138; lineNumber++) {

				if (lineNumber == 1) {
					wr.println("// Player's Info");
				}
				if (lineNumber == 2) {
					wr.println("// Player Name-Balance-Player In Jail-Player Cards");
				}
				if (lineNumber == 15) {
					wr.println("// Current Player Name:");
				}
				if (lineNumber == 16) {
					wr.println(controller.currentPlayer.getName());
				}
				if (lineNumber == 18) {
					wr.println("//");
				}
				if (lineNumber == 17) {
					wr.println("// Board's Info");
				}

				// player's info
				if (lineNumber >= 3 && lineNumber <= 14) {

					if (controller.players.size() > lineNumber - 3) {
						Player playerToBeSaved = controller.players.get(lineNumber - 3);
						String playerName = playerToBeSaved.getName();
						int playerBalance = playerToBeSaved.getBalance();
						boolean playerInJail = playerToBeSaved.isInJail();
						boolean playerIsBankrupt = playerToBeSaved.isBankrupt();
						List<Card> playerCards = playerToBeSaved.getCards();
						int playerPieceLoc = controller.board.getSquares()
								.indexOf(playerToBeSaved.getPiece().getLocation());
						int direction = playerToBeSaved.getDirection() + 1;
						int jailTurnLeft = playerToBeSaved.getJailTurnLeft();

						String joinedString = String.join("-", playerName, playerBalance + "", playerInJail + "",
								playerCards.toString(), playerPieceLoc + "", direction + "", jailTurnLeft + "",
								playerIsBankrupt + "");
						wr.println(joinedString);
					} else {
						wr.println("player01-3200-false-null");
					}
				}

				// board's info
				int[] titleDeedsIndexes = { 1, 3, 4, 5, 9, 10, 12, 13, 15, 16, 17, 19, 24, 25, 27, 29, 31, 32, 36, 37,
						40, 41, 44, 45, 47, 48, 51, 53, 54, 55, 57, 59, 60, 62, 63, 65, 67, 68, 70, 73, 75, 77, 79, 82,
						84, 85, 87, 89, 90, 92, 94, 95, 97, 101, 103, 104, 107, 109, 110, 113, 115, 116, 118, 119 };
				int[] chanceSquareIndexes = { 2, 26, 38, 49, 58, 72, 83, 100 };
				int[] cabCompanySquareIndexes = { 6, 22, 34, 50 };
				int[] companySquareIndexes = { 11, 21, 39, 46, 64, 88, 99, 111 };
				int[] railRoadSquareIndexes = { 7, 35, 61, 71, 81, 91, 105, 117 };
				int[] communityChestsSquareIndexes = { 8, 18, 30, 52, 69, 78, 93, 112 };
				int[] goToJailSquareIndexes = { 14 };
				int[] busTicketSquareIndexes = { 20, 33 };
				int[] birthdayGiftSquareIndexes = { 23 };
				int[] subwaySquareIndexes = { 28 };
				int[] hollandTunnelSquareIndexes = { 42, 102 };
				int[] freeParkingSquareIndexes = { 56 };
				int[] rollThreeSquareIndexes = { 66 };
				int[] luxuryTaxSquareIndexes = { 74 };
				int[] incomeTaxSquareIndexes = { 80 };
				int[] jailSquareIndexes = { 86 };
				int[] stockExchangeSquareIndexes = { 96 };
				int[] taxRefundSquareIndexes = { 98 };
				int[] reverseDirectionSquareIndexes = { 106 };
				int[] squeezePlaySquareIndexes = { 108 };
				int[] bonusSquareIndexes = { 114 };
				int[] payDaySquareIndexes = { 0 };
				int[] auctionSquareIndexes = { 43 };
				int[] goSquareIndexes = { 76 };

				if (lineNumber >= 19 && contains(titleDeedsIndexes, (lineNumber - 19))) {

					Square titDeed = controller.board.getSquares().get(lineNumber - 19);
					if (titDeed instanceof TitleDeedSquare) {
						TitleDeedSquare squareToBeSaved = (TitleDeedSquare) titDeed;
						String squareName = squareToBeSaved.getName();
						String ownerName = "";
						if (squareToBeSaved.getOwner() != null) {
							ownerName = squareToBeSaved.getOwner().getName();
						} else {
							ownerName = "null";
						}
						int price = squareToBeSaved.getPrice();
						ArrayList<Integer> rentList = squareToBeSaved.getRentList();
						int numOfHouses = squareToBeSaved.getNumberOfHouses();
						int housePrice = squareToBeSaved.getHousePrice();
						int numOfHotel = squareToBeSaved.getNumberOfHotels();
						int hotelPrice = squareToBeSaved.getHotelPrice();
						int numOfSkyscraper = squareToBeSaved.getNumberOfSkyScrapers();
						int skyskcraperPrice = squareToBeSaved.getSkyScraperPrice();
						String color = squareToBeSaved.getColor();
						boolean isStreet = squareToBeSaved.isStreet();

						System.out.println("Saving line number " + lineNumber);

						String joinedString = String.join("-", (lineNumber - 19) + "", squareName, ownerName,
								price + "", rentList.get(0) + "", rentList.get(1) + "", rentList.get(2) + "",
								rentList.get(3) + "", rentList.get(4) + "", rentList.get(5) + "", rentList.get(6) + "",
								numOfHouses + "", housePrice + "", numOfHotel + "", hotelPrice + "",
								numOfSkyscraper + "", skyskcraperPrice + "", color, isStreet + "");
						System.out.println("Line added");
						wr.println(joinedString);
					}

				}

				if (lineNumber >= 19 && contains(chanceSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Chance");
				}

				if (lineNumber >= 19 && contains(cabCompanySquareIndexes, (lineNumber - 19))) {
					Square cabComp = controller.board.getSquares().get(lineNumber - 19);
					if (cabComp instanceof CabCompanySquare) {
						CabCompanySquare squareToBeSaved = (CabCompanySquare) cabComp;
						String ownerName = "";
						if (squareToBeSaved.getOwner() != null) {
							ownerName = squareToBeSaved.getOwner().getName();
						} else {
							ownerName = "null";
						}
						wr.println((lineNumber - 19) + "-" + squareToBeSaved.getName() + "-" + ownerName);
					}

				}

				if (lineNumber >= 19 && contains(companySquareIndexes, (lineNumber - 19))) {
					Square comp = controller.board.getSquares().get(lineNumber - 19);
					if (comp instanceof CompanySquare) {
						CompanySquare squareToBeSaved = (CompanySquare) comp;

						String ownerName = "";
						if (squareToBeSaved.getOwner() != null) {
							ownerName = squareToBeSaved.getOwner().getName();
						} else {
							ownerName = "null";
						}
						wr.println((lineNumber - 19) + "-" + squareToBeSaved.getName() + "-" + ownerName);
					}

				}

				// deal with later
				if (lineNumber >= 19 && contains(railRoadSquareIndexes, (lineNumber - 19))) {
					Square rail = controller.board.getSquares().get(lineNumber - 19);
					if (rail instanceof RailRoadSquare) {
						RailRoadSquare squareToBeSaved = (RailRoadSquare) rail;
						String ownerName = "";
						if (squareToBeSaved.getOwner() != null) {
							ownerName = squareToBeSaved.getOwner().getName();
						} else {
							ownerName = "null";
						}
						wr.println((lineNumber - 19) + "-" + squareToBeSaved.getName() + "-" + ownerName);
					}

				}

				if (lineNumber >= 19 && contains(communityChestsSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Community Chest");
				}

				if (lineNumber >= 19 && contains(goToJailSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Go To Jail");
				}

				if (lineNumber >= 19 && contains(busTicketSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Bus Ticket");
				}

				if (lineNumber >= 19 && contains(birthdayGiftSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Birthday Gift");
				}

				if (lineNumber >= 19 && contains(subwaySquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Subway");
				}

				if (lineNumber >= 19 && contains(hollandTunnelSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Holland Tunnel");
				}

				if (lineNumber >= 19 && contains(freeParkingSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Free Parking");
				}

				if (lineNumber >= 19 && contains(rollThreeSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Roll Three");
				}

				if (lineNumber >= 19 && contains(luxuryTaxSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Luxury Tax");
				}

				if (lineNumber >= 19 && contains(incomeTaxSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Income Tax");
				}

				if (lineNumber >= 19 && contains(jailSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Jail");
				}

				if (lineNumber >= 19 && contains(stockExchangeSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Stock Exchange");
				}

				if (lineNumber >= 19 && contains(taxRefundSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Tax Refund");
				}

				if (lineNumber >= 19 && contains(reverseDirectionSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Reverse Direction");
				}

				if (lineNumber >= 19 && contains(squeezePlaySquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Squueze Play");
				}

				if (lineNumber >= 19 && contains(bonusSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Bonus");
				}

				if (lineNumber >= 19 && contains(payDaySquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Pay Day");
				}

				if (lineNumber >= 19 && contains(auctionSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Auction");
				}

				if (lineNumber >= 19 && contains(goSquareIndexes, (lineNumber - 19))) {
					wr.println((lineNumber - 19) + "-Go");
				}

			}
			wr.close();

		} catch (IOException e) {
			System.out.println("Can't create the output file.");
		}
	}

	public static void loadGame(String fileName, MonopolyGameController controller) {
		BufferedReader rd;
		int lineNumber = 1;
		ArrayList<Integer> piecesLocs = new ArrayList<Integer>();
		try {
			rd = new BufferedReader(new FileReader("GameStates/" + fileName));
			while (true) {
				String line = rd.readLine();
				if (lineNumber == 17) {
					lineNumber++;
					break;
				}

				// player's info
				if (lineNumber >= 3 && lineNumber <= 14) {
					String[] arr = line.split("-");

					if (!arr[0].equals("player01")) {
						controller.players.add(new Player(arr[0], Integer.parseInt(arr[1]),
								Boolean.parseBoolean(arr[2]), new ArrayList<Card>(), new Piece(null, null),
								Integer.parseInt(arr[5]) - 1, Integer.parseInt(arr[6]), Boolean.parseBoolean(arr[7])));
						piecesLocs.add(Integer.parseInt(arr[4]));
					}
				}

				if (lineNumber == 16) {
					if (!fileName.equals("InitialGameState.null")) {
						controller.currentPlayer = controller.getPlayerByName(line);
					}
				}

				// board's info
				lineNumber++;
			}
			controller.board.initSquares(rd, lineNumber);
			controller.board.initCards();
			rd.close();

			BufferedReader rd2 = new BufferedReader(new FileReader("GameStates/" + fileName));
			int lineNumber2 = 1;

			while (true) {
				String line = rd2.readLine();
				if (line == null) {
					rd2.close();
					break;
				}

				int[] titleDeedsIndexes = { 1, 3, 4, 5, 9, 10, 12, 13, 15, 16, 17, 19, 24, 25, 27, 29, 31, 32, 36, 37,
						40, 41, 44, 45, 47, 48, 51, 53, 54, 55, 57, 59, 60, 62, 63, 65, 67, 68, 70, 73, 75, 77, 79, 82,
						84, 85, 87, 89, 90, 92, 94, 95, 97, 101, 103, 104, 107, 109, 110, 113, 115, 116, 118, 119 };
				// adding owner
				if (lineNumber2 >= 19 && contains(titleDeedsIndexes, lineNumber2 - 19)) {
					String[] arr = line.split("-");

					System.out.println("Setting owner " + lineNumber2);

					((TitleDeedSquare) controller.board.getSquareByName(arr[1]))
							.setOwner(controller.getPlayerByName(arr[2]));
				}

				lineNumber2++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!fileName.equals("InitialGameState.null")) {
			for (int i = 0; i < controller.players.size(); i++) {
				controller.players.get(i).getPiece().setLocation(controller.board.getSquares().get(piecesLocs.get(i)));
				try {
					controller.players.get(i).getPiece()
							.setImage(ImageIO.read(new File("assets/pieces/piece-0" + (i + 1) + ".png")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			controller.gamename = fileName.substring(0, fileName.lastIndexOf("."));
		}
	}

	private static boolean contains(final int[] array, final int key) {
		return Arrays.stream(array).anyMatch(n -> n == key);
	}

	public static boolean isDuplicateGameName(String gameName) {
		File folder = new File("GameStates/");
		File[] files = folder.listFiles();

		for (File f : files) {
			if (f.getName().equals(gameName + ".null")) {
				return false;
			}
		}

		return true;
	}

	public static void initNewGamePlayer(ArrayList<String> usernames, String newGameName,
			MonopolyGameController controller) {
		controller.players.clear();

		for (int i = 0; i < usernames.size(); i++) {
			try {
				controller.players
						.add(new Player(usernames.get(i), 3200, false, new ArrayList<Card>(),
								new Piece(controller.board.getSquares().get(76),
										ImageIO.read(new File("assets/pieces/piece-0" + (i + 1) + ".png"))),
								1, 3, false));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		controller.currentPlayer = controller.players.get(0);
		controller.gamename = newGameName;
	}

}
