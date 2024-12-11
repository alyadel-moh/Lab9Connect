package coding.Observer;

import coding.User;

public interface NotificationObserver {
    public void update(User user, String text);

}
