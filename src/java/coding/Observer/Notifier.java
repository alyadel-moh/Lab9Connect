package coding.Observer;

import coding.User;

import java.util.ArrayList;

public class Notifier {
    private final ArrayList<NotificationObserver> friendObservers = new ArrayList<>();
    private final ArrayList<NotificationObserver> groupObservers = new ArrayList<>();
    private final ArrayList<NotificationObserver> generalObserver = new ArrayList<>();

    public ArrayList<NotificationObserver> getGroupObservers() {
        return groupObservers;
    }

    public ArrayList<NotificationObserver> getGeneralObserver() {
        return generalObserver;
    }

    public ArrayList<NotificationObserver> getFriendObservers() {
        return friendObservers;
    }

    // Add an observer
    public void addObserver(NotificationObserver observer) {
        if (observer != null && !friendObservers.contains(observer)) {
            friendObservers.add(observer);
        }
    }

    // Remove an observer
    public void removeObserver(NotificationObserver observer) {
        if(observer != null)
            friendObservers.remove(observer);
    }

    // Notify all observers
    public void notifyObservers(Object user, Enum code, NotificationObserver target) {
        for (NotificationObserver observer : friendObservers) {
            if (target == null || observer.equals(target)) {
                observer.update(user, code);
            }
        }
    }

    // Notify all observers
    public void notifyGroupObservers(Object user, Enum code, NotificationObserver target) {
        for (NotificationObserver observer : friendObservers) {
            if (target == null || observer.equals(target)) {
                observer.update(user, code);
            }
        }
    }

    // Notify all observers
    public void notifyFriendObservers(User user, Enum code, NotificationObserver target) {
        for (NotificationObserver observer : friendObservers) {
            if (target == null || observer.equals(target)) {
                observer.update(user, code);
            }
        }
    }


}
