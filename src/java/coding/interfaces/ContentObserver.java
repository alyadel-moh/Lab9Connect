package coding.interfaces;

import coding.Posts;
import coding.Stories;

import java.util.ArrayList;

public interface ContentObserver {
    void updateStories(ArrayList<Stories> stories);

    void updatePosts(ArrayList<Posts> posts);
}
