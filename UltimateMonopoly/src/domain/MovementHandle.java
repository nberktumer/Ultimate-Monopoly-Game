package domain;

public class MovementHandle {

	private static MovementHandle instance;

	public int handleRailRoad(int pos, int dir) {
		int result = pos;
		if (pos == 7)
			result = 61;
		else if (pos == 61)
			result = 7;
		else if (pos == 35)
			result = 81;
		else if (pos == 81)
			result = 35;
		else if (pos == 71)
			result = 105;
		else if (pos == 105)
			result = 71;
		else if (pos == 91)
			result = 117;
		else if (pos == 117)
			result = 91;

		return result + dir;
	}

	public int handleNextSquare(int pos, int dir) {
		int result = pos;

		if (pos == 55 && dir == 1)
			result = 0;
		else if (pos == 0 && dir == -1)
			result = 55;
		else if (pos == 95 && dir == 1)
			result = 56;
		else if (pos == 56 && dir == -1)
			result = 95;
		else if (pos == 119 && dir == 1)
			result = 96;
		else if (pos == 96 && dir == -1)
			result = 119;
		else
			result += dir;

		return result;
	}

	public static MovementHandle getInstance() {
		if (instance == null)
			instance = new MovementHandle();
		return instance;
	}

}