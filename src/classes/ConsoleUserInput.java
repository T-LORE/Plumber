package classes;

import classes.events.UserInputEvent;
import classes.events.UserInputListener;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUserInput extends UserInputHandler {

    public ConsoleUserInput() {

    }

    public void readEvent() throws IOException {
        //read console
        System.out.println("Enter command: ");
        System.out.println("1 - rotate clockwise");
        System.out.println("2 - start flow");
        System.out.println("3 - load level");

        //read input from console

        Scanner myInput = new Scanner( System.in );

        int command = 0;
        command = myInput.nextInt();


        switch (command) {
            case 1:
                System.out.println("Enter cords: ");
                int x = myInput.nextInt();
                int y = myInput.nextInt();
                fireRotateClockwiseEvent(new Point(x, y));
                
                break;
            case 2:
                fireStartFlowEvent();
                break;
            case 3:
                fireLoadLevelEvent("123.txt");
                break;
            default:
                System.out.println("Invalid command" + command);
        }
        
        
    }

    private void fireRotateClockwiseEvent(Point point) {
        System.out.println("Fire rotate clockwise event" + point.x + " " + point.y);
        UserInputEvent event = new UserInputEvent(this);
        event.point = point;

        for (UserInputListener listener : _listeners) {
            listener.rotateClockwise(event);
        }
    }

    private void fireStartFlowEvent() {
        System.out.println("Fire start flow event");
        UserInputEvent event = new UserInputEvent(this);

        for (UserInputListener listener : _listeners) {
            listener.startFlow(event);
        }
    }

    private void fireLoadLevelEvent(String levelPath) throws IOException {
        System.out.println("Fire load level event" + levelPath);
        UserInputEvent event = new UserInputEvent(this);
        event.levelPath = levelPath;

        for (UserInputListener listener : _listeners) {
            listener.loadLevel(event);
        }
    }
}
