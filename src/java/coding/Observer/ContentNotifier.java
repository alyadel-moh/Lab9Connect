package coding.Observer;

import coding.User;

import java.util.ArrayList;

public class ContentNotifier {
    private final ArrayList<ContentObserver> observers = new ArrayList<>();

    // Add an observer
    public void addObserver(ContentObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    // Remove an observer
    public void removeObserver(ContentObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers
    public void notifyObservers(User user, String text, User target) {
        for (ContentObserver observer : observers) {
            if (target == null || observer.equals(target)) {
                observer.update(user, text);
            }
        }
    }

}
