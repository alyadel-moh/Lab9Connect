package coding.Observer;

import coding.User;

import java.util.ArrayList;

public class Notifier {
    private final ArrayList<NotificationObserver> friendObservers = new ArrayList<>();
    private final ArrayList<NotificationObserver> groupObservers = new ArrayList<>();
    private final ArrayList<NotificationObserver> generalObserver = new ArrayList<>();


    ////////////////// GETTERS /////////////////////////////
    public ArrayList<NotificationObserver> getGroupObservers() {
        return groupObservers;
    }

    public ArrayList<NotificationObserver> getGeneralObserver() {
        return generalObserver;
    }

    public ArrayList<NotificationObserver> getFriendObservers() {
        return friendObservers;
    }

    ///////////////// ADDERS /////////////////////////////////
    // Add an observer
    public void addObserver(NotificationObserver observer) {
        if (observer != null && !friendObservers.contains(observer)) {
            friendObservers.add(observer);
        }
    }

    public void addGroupObserver(NotificationObserver observer) {
        if (observer != null && !groupObservers.contains(observer)) {
            groupObservers.add(observer);
        }
    }

    public void addGeneralObserver(NotificationObserver observer) {
        if (observer != null && !generalObserver.contains(observer)) {
            generalObserver.add(observer);
        }
    }

    ////////////////// REMOVERS /////////////////////////////////
    // Remove an observer
    public void removeObserver(NotificationObserver observer) {
        if(observer != null)
            friendObservers.remove(observer);
    }

    public void removeGeneralObserver(NotificationObserver observer) {
        if(observer != null)
            generalObserver.remove(observer);
    }

    public void removeGroupObserver(NotificationObserver observer) {
        if(observer != null)
            groupObservers.remove(observer);
    }

    ////////////////////////////////////////////////////////////////////////////

    // Notify all observers
    public void notifyObservers(User user, Enum code, NotificationObserver target) {
        for (NotificationObserver observer : friendObservers) {
            if (target == null || observer.equals(target)) {
                observer.update(user, code);
            }
        }
    }

    // Notify all observers
    public void notifyGroupObservers(Object user, Enum code, NotificationObserver target) {
        for (NotificationObserver observer : groupObservers) {
            if (target == null || observer.equals(target)) {
                observer.update(user, code);
            }
        }
    }

    // Notify all observers
    public void notifyGeneralObservers(User user, Enum code, NotificationObserver target) {
        for (NotificationObserver observer : generalObserver) {
            if (target == null || observer.equals(target)) {
                observer.update(user, code);
            }
        }
    }


}
