package domain;

public interface Observer {
	public void update(String key, String action, Object obj);
}
