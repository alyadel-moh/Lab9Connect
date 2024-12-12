package coding.Observer;

import coding.CustomPanel;
import coding.ENUMS.Mapper;
import coding.Notifications_Panel;
import coding.User;

public class Request_Observer implements NotificationObserver {
    private final User user;

    public Request_Observer(User user) {
        this.user = user;
    }

    @Override
    public void update(User user, Enum CODE) {
        Notifications_Panel customPanel =  new Notifications_Panel(user, CODE ,"Accept" ,"Request");
        System.out.println(this.user.getUserName() + " :" +user.getUserName() + Mapper.getMessage(CODE));
        this.user.getObserver().update(user, CODE);
    }
}
