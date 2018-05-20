package domain;

public class BankruptcyProcess {
	private Player playerThatOwes;
	private Player playerThatIsOwedTo;
	private int owedAmount;

	public BankruptcyProcess(Player playerThatOwes, Player playerThatIsOwedTo, int owedAmount) {
		this.playerThatOwes = playerThatOwes;
		this.playerThatIsOwedTo = playerThatIsOwedTo;
		this.owedAmount = owedAmount;

	}

	public boolean canDebtBePaid() {
		return playerThatOwes.getBalance() >= owedAmount;
	}

	public void terminateBankruptcyProcess() {
		playerThatOwes.updateBalanceDelta(-owedAmount);
		playerThatIsOwedTo.updateBalanceDelta(owedAmount);
		Logger.getInstance().log(playerThatOwes.getName() + " has paid his $" + owedAmount + " debt to "
				+ playerThatIsOwedTo.getName() + ".");

	}

}
