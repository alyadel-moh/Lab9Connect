package coding.Observer;

import coding.User;

public interface ContentObserver {
    public void update(User user, String text);
}
