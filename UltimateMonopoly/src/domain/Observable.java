package domain;


public interface Observable {
	public void notifyObservers(String key, String action, Object obj);
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
}
