package domain;

import java.util.ArrayList;

/**
 * @overview A container class for dice. This is responsible of all die actions
 *           such as roll dice and flush.
 * @author Null
 *
 */
public class Cup {

	private ArrayList<DieValue> results;
	private static int numberOfDuplicates = 0;

	public Cup() {
		this.results = new ArrayList<>();
		Cup.numberOfDuplicates = 0;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns results.
	 */
	public ArrayList<DieValue> getResults() {
		return results;
	}

	/**
	 * 
	 * @requires -
	 * @modifies results
	 * @effects Sets results to given results.
	 */
	public void setResults(ArrayList<DieValue> results) {
		this.results = results;
	}

	/**
	 * 
	 * @requires -
	 * @modifies -
	 * @effects Returns numberOfDuplicates.
	 */
	public int getNumberOfDuplicates() {
		return numberOfDuplicates;
	}

	/**
	 * 
	 * @requires Given numberOfDuplicates must be between 0 and 3
	 * @modifies numberOfDuplicates
	 * @effects Sets numberOfDuplicates to given numberOfDuplicates.
	 */
	public void setNumberOfDuplicates(int numberOfDuplicates) {
		Cup.numberOfDuplicates = numberOfDuplicates;
	}

	/**
	 * 
	 * @requires results is not null.
	 * @modifies results
	 * @effects Creates a regular die and a speed die, then rolls the regular
	 *          die twice and rolls the speed die once. Adds the results of dice
	 *          to results.
	 */
	public void roll() {
		RegularDie regularDie = new RegularDie();
		SpeedDie speedDie = new SpeedDie();

		results.add(regularDie.roll());
		results.add(regularDie.roll());
		results.add(speedDie.roll());
	}

	/**
	 * 
	 * @requires results is not null.
	 * @modifies -
	 * @effects Returns true, if results contains BUS or MRMONOPOLY. Otherwise,
	 *          returns false.
	 */
	public boolean isSpecial() {
		return (results.contains(DieValue.BUS) || results.contains(DieValue.MRMONOPOLY));
	}

	/**
	 * 
	 * @requires results is not null.
	 * @modifies results, numberOfDuplicates
	 * @effects Clears results and sets numberOfDuplicates to 0.
	 */
	public void flush() {
		results.clear();
	}

	public void incrementNumberOfDuplicates() {
		Cup.numberOfDuplicates++;
	}

	public boolean repOk() {
		return (results != null && numberOfDuplicates >= 0 && numberOfDuplicates <= 3);
	}

	@Override
	public String toString() {
		return "Cup [results=" + results + ", numberOfDuplicates=" + numberOfDuplicates + "]";
	}
}
