package coding.Observer;

import coding.ENUMS.Mapper;
import coding.User;

public class Content_Observer implements NotificationObserver {
    private final User user;

    public Content_Observer(User user) {
        this.user = user;
    }

    @Override
    public void update(Object user, Enum CODE) {
        if (user instanceof User) {
            System.out.println(this.user.getUserName() + " :" + ((User) user).getUserName() + Mapper.getMessage(CODE));
            this.user.getObserver().update(user, CODE);
        }
    }
}
