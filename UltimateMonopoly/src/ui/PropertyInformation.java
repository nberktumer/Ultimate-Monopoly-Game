package ui;

public class PropertyInformation {

	private String type;
	private String name;
	private int upgradeCost;
	private int house;
	private int hotel;
	private int skyscraper;
	private int currentRentPrice;
	private int sellamount;
	private int downgradeamount;

	public PropertyInformation(String type, String name, int upgradeCost) {
		super();
		this.type = type;
		this.name = name;
		this.upgradeCost = upgradeCost;
	}

	public int getHouse() {
		return house;
	}

	public int getHotel() {
		return hotel;
	}

	public int getSkyscraper() {
		return skyscraper;
	}

	public void setHouse(int house) {
		this.house = house;
	}

	public void setHotel(int hotel) {
		this.hotel = hotel;
	}

	public void setSkyscraper(int skyscraper) {
		this.skyscraper = skyscraper;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getUpgradeCost() {
		return upgradeCost;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUpgradeCost(int upgradeCost) {
		this.upgradeCost = upgradeCost;
	}

	public int getCurrentRentPrice() {
		return currentRentPrice;
	}

	public void setCurrentRentPrice(int currentRentPrice) {
		this.currentRentPrice = currentRentPrice;
	}

	public int getSellamount() {
		return sellamount;
	}

	public void setSellamount(int sellamount) {
		this.sellamount = sellamount;
	}

	public int getDowngradeamount() {
		return downgradeamount;
	}

	public void setDowngradeamount(int downgradeamount) {
		this.downgradeamount = downgradeamount;
	}

}
