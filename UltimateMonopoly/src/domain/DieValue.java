package domain;

public enum DieValue {
	BUS(-1), MRMONOPOLY(-1), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);
	
	 private final int val;
	 DieValue(int val) { this.val = val; }
	 public int getValue() { return val; }
}
