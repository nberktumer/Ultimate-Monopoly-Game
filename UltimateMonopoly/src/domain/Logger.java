package domain;

import java.util.ArrayList;

public class Logger implements Observable {
	private static Logger instance;
	private ArrayList<String> log;
	private ArrayList<Observer> observers;

	private Logger() {
		log = new ArrayList<String>();
		observers = new ArrayList<Observer>();
	}

	public static Logger getInstance() {
		if (instance == null)
			instance = new Logger();

		return instance;
	}

	public void log(String input) {
		log.add(input);
		notifyObservers("game", "updateLog", input);
	}

	public ArrayList<String> getLog() {
		return log;
	}

	public void flushLog() {
		log.clear();
	}

	@Override
	public void notifyObservers(String key, String action, Object obj) {
		for (Observer o : observers) {
			o.update(key, action, obj);
		}

	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

}
