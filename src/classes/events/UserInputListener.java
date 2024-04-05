package classes.events;

import java.awt.*;
import java.io.IOException;
import java.util.EventListener;
public interface UserInputListener extends EventListener {
    void rotateClockwise(UserInputEvent event);

    void startFlow(UserInputEvent event);

    void loadLevel(UserInputEvent event) throws IOException;
}
