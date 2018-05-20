package domain;

import java.util.ArrayList;
import java.util.List;

public class PlayerObservable implements Observable {

	private static PlayerObservable instance;
	public List<Observer> observers = new ArrayList<>();

	public PlayerObservable() {
	}

	public synchronized static PlayerObservable getInstance() {
		if (instance == null)
			instance = new PlayerObservable();
		return instance;
	}

	@Override
	public void notifyObservers(String key, String action, Object obj) {
		for (Observer o : observers) {
			o.update(key, action, obj);
		}
	}

	@Override
	public void addObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.remove(o);
	}

}
