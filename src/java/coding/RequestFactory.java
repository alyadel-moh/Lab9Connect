package coding;

import coding.ENUMS.REQUEST;

public class RequestFactory {
    public static Request createRequest(REQUEST type, User sender, String receiverKey){
        return switch(type) {
            case FRIENDREQUEST -> new FriendRequest(sender, Database.findUserById(receiverKey));
            case GROUPREQUEST -> new Group_Request(sender, get_Group_by_key(receiverKey));
            default -> throw new IllegalArgumentException("Unknown Type: " + type);
        };
    }

    private static Group get_Group_by_key(String key) {
        return Group_Manager.getAllgroups().getOrDefault(key, null);
    }
}
