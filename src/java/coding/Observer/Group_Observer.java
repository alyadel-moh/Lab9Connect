package coding.Observer;

import coding.CustomPanel;
import coding.ENUMS.Mapper;
import coding.Group;
import coding.User;

public class Group_Observer implements NotificationObserver {
    private final User user;

    public Group_Observer(User user) {
        this.user = user;
    }

    @Override
    public void update(Object user, Enum CODE) {
        if (user instanceof User) {
            System.out.println(this.user.getUserName() + " :" + ((User) user).getUserName() + Mapper.getMessage(CODE));
            this.user.getObserver().update(user, CODE);

        }else if (user instanceof Group){
            System.out.println(this.user.getUserName() + " :" + ((Group) user).getName() + Mapper.getMessage(CODE));
            this.user.getObserver().update(user, CODE);
        }
    }
}
