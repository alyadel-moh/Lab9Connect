package coding.Observer;

import coding.CustomPanel;
import coding.User;

import java.util.ArrayList;

public class Notifier {
    private final ArrayList<NotificationObserver> observers = new ArrayList<>();

    public ArrayList<NotificationObserver> getObservers() {
        return observers;
    }

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
    public void notifyObservers(User user, Enum code, NotificationObserver target) {
        //updateNotifications(user);
        for (NotificationObserver observer : observers) {
            if (target == null || observer.equals(target)) {
                observer.update(user, code);
            }
        }
    }

    public void updateNotifications(User user){
        for(User friend : user.getManager().getFriends()){
            friend.getHandler().addNotification(new CustomPanel<>(user, "Notification", "Ignore"));
        }
    }

}
