package coding.interfaces;

import coding.Content;

import java.util.ArrayList;

public interface ContentObserver {
    void update(ArrayList<Content> contents);
}
