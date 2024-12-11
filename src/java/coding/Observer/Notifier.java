package coding.Observer;

import coding.CustomPanel;
import coding.User;

import java.util.ArrayList;

public class Notifier<T> {
    private final ArrayList<NotificationObserver> observers = new ArrayList<>();

    // Add an observer
    public void addObserver(NotificationObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    // Remove an observer
    public void removeObserver(NotificationObserver observer) {
        if(observer != null)
            observers.remove(observer);
    }

    // Notify all observers
    public void notifyObservers(User user, String text, T target) {
        updateNotifications(user);

        for (NotificationObserver observer : observers) {
            if (target == null || observer.equals(target)) {
                observer.update(user, text);
            }
        }
    }

    public void updateNotifications(User user){
        for(User friend : user.getManager().getFriends()){
            friend.getHandler().addNotification(new CustomPanel<>(user, "Notification", "Ignore"));
        }
    }

}
