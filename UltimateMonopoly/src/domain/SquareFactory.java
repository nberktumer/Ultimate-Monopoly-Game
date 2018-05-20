package domain;

import java.util.ArrayList;
import java.util.Arrays;

import domain.square.AuctionSquare;
import domain.square.BirthdayGiftSquare;
import domain.square.BonusSquare;
import domain.square.BusTicketSquare;
import domain.square.CabCompanySquare;
import domain.square.ChanceSquare;
import domain.square.CommunityChestSquare;
import domain.square.CompanySquare;
import domain.square.FreeParkingSquare;
import domain.square.GoSquare;
import domain.square.GoToJailSquare;
import domain.square.HollandTunnelSquare;
import domain.square.IncomeTaxSquare;
import domain.square.JailSquare;
import domain.square.LuxuryTaxSquare;
import domain.square.PayDaySquare;
import domain.square.RailRoadSquare;
import domain.square.ReverseDirectionSquare;
import domain.square.RollThreeSquare;
import domain.square.Square;
import domain.square.SqueezePlaySquare;
import domain.square.StockExchangeSquare;
import domain.square.SubwaySquare;
import domain.square.TaxRefundSquare;
import domain.square.TitleDeedSquare;

public class SquareFactory {

	private static SquareFactory instance;

	int[] titleDeedsIndexes = { 1, 3, 4, 5, 9, 10, 12, 13, 15, 16, 17, 19, 24, 25, 27, 29, 31, 32, 36, 37, 40, 41, 44,
			45, 47, 48, 51, 53, 54, 55, 57, 59, 60, 62, 63, 65, 67, 68, 70, 73, 75, 77, 79, 82, 84, 85, 87, 89, 90, 92,
			94, 95, 97, 101, 103, 104, 107, 109, 110, 113, 115, 116, 118, 119 };
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

	private SquareFactory() {

	}

	private boolean contains(final int[] array, final int key) {
		return Arrays.stream(array).anyMatch(n -> n == key);
	}

	public Square createSquare(String line, int lineNumber) {

		if (lineNumber >= 19 && contains(titleDeedsIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			ArrayList<Integer> rentList = new ArrayList<Integer>();
			rentList.add(Integer.parseInt(arr[4]));
			rentList.add(Integer.parseInt(arr[5]));
			rentList.add(Integer.parseInt(arr[6]));
			rentList.add(Integer.parseInt(arr[7]));
			rentList.add(Integer.parseInt(arr[8]));
			rentList.add(Integer.parseInt(arr[9]));
			rentList.add(Integer.parseInt(arr[10]));

			// title deed's owner is new unspecified player here

			System.out.println("Loading line number " + lineNumber);
			return new TitleDeedSquare(arr[1], null, Integer.parseInt(arr[3]), rentList, Integer.parseInt(arr[11]),
					Integer.parseInt(arr[12]), Integer.parseInt(arr[13]), Integer.parseInt(arr[14]),
					Integer.parseInt(arr[15]), Integer.parseInt(arr[16]), arr[17], Boolean.parseBoolean(arr[18]));
		}
		if (lineNumber >= 19 && contains(chanceSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new ChanceSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(cabCompanySquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new CabCompanySquare(arr[1], null));
		}

		if (lineNumber >= 19 && contains(companySquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new CompanySquare(arr[1], null));
		}

		// deal with later
		if (lineNumber >= 19 && contains(railRoadSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new RailRoadSquare(arr[1], null));
		}

		if (lineNumber >= 19 && contains(communityChestsSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new CommunityChestSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(goToJailSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new GoToJailSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(busTicketSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new BusTicketSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(birthdayGiftSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new BirthdayGiftSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(subwaySquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new SubwaySquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(hollandTunnelSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new HollandTunnelSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(freeParkingSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new FreeParkingSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(rollThreeSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new RollThreeSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(luxuryTaxSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new LuxuryTaxSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(incomeTaxSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new IncomeTaxSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(jailSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new JailSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(stockExchangeSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new StockExchangeSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(taxRefundSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new TaxRefundSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(reverseDirectionSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new ReverseDirectionSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(squeezePlaySquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new SqueezePlaySquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(bonusSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");
			return (new BonusSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(payDaySquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");

			return (new PayDaySquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(auctionSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");

			return (new AuctionSquare(arr[1]));
		}

		if (lineNumber >= 19 && contains(goSquareIndexes, (lineNumber - 19))) {
			String[] arr = line.split("-");

			return (new GoSquare(arr[1]));
		}

		return null;
	}

	public static SquareFactory getInstance() {
		if (instance == null)
			instance = new SquareFactory();
		return instance;
	}

}
