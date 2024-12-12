package coding.ENUMS;

import coding.ENUMS.NOTIFICATIONS.CONTENT;
import coding.ENUMS.NOTIFICATIONS.GROUP;
import coding.ENUMS.NOTIFICATIONS.REQUEST;

import java.util.HashMap;

public class Mapper {
    private static final HashMap<Enum<?>, String> mapper = new HashMap<>();

    // Static initializer block to populate the map
    static {
        mapper.put(CONTENT.POST, " added a new post!");
        mapper.put(CONTENT.STORY, " posted a new story!");

        mapper.put(GROUP.ADDED, "added to group!");
        mapper.put(GROUP.POST, " new post in group");
        mapper.put(GROUP.CHANGE_STATUS, " status change in group");

        mapper.put(REQUEST.RECEIVE, " sent you a friend request!");
        mapper.put(REQUEST.SEND, " friend request sent!");
        mapper.put(REQUEST.SENDGROUP, " requested to join group ");
    }

    // Static method to retrieve the message
    public static String getMessage(Enum<?> code) {
        return mapper.getOrDefault(code, "Unknown code");
    }
}
