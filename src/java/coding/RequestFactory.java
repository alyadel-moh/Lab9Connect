package coding;

public class RequestFactory {
    public static Request createRequest(String type, User sender, String receiverKey){
        return switch(type.toLowerCase()) {
            case "friend request" -> new FriendRequest(sender, Database.findUserById(receiverKey));
            case "group request" -> new Group_Request(sender, get_Group_by_key(receiverKey));
            default -> throw new IllegalArgumentException("Unknown Type: " + type);
        };
    }

    private static Group get_Group_by_key(String key) {
        return Group_Manager.getAllgroups().getOrDefault(key, null);
    }
}
