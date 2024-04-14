package classes;

import classes.events.UserInputListener;

import java.util.ArrayList;

public class UserInputHandler {
    protected ArrayList<UserInputListener> _listeners = new ArrayList<>();

    public void addListener(UserInputListener listener) {
        _listeners.add(listener);
    }
}
