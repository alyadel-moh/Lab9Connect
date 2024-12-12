package coding.Observer;

import coding.CustomPanel;
import coding.ENUMS.Mapper;
import coding.User;
<<<<<<< Updated upstream

public class Group_Observer implements NotificationObserver {
    private final User user;

    public Group_Observer(User user) {
        this.user = user;
    }

    @Override
    public void update(User user, Enum CODE) {
        System.out.println(this.user.getUserName() + " :" + user.getUserName() + Mapper.getMessage(CODE));
        this.user.getObserver().update(user, CODE);
    }
}
=======
//
//public class Group_Observer implements NotificationObserver {
//    private final User user;
//
//    public Group_Observer(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public void update(User user, String text) {
//        CustomPanel
//    }
//}
>>>>>>> Stashed changes
