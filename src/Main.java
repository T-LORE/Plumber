import classes.*;
import classes.entities.Entity;
import classes.events.*;

import java.io.IOException;

public class Main {
    Field _field;
    static Game _game;
    public static void main(String[] args) throws IOException {
        ConsoleUserInput eventSimulator = new ConsoleUserInput();
        UserInputObserver observer = new UserInputObserver();
        _game = new Game(eventSimulator);
        eventSimulator.addListener(observer);
        _game.addListener(new GameObserver());
        while (true) {
            eventSimulator.readEvent();
        }

    }

    private static void updateField() {
        if (_game.getField() != null)
            System.out.println(_game.getField().toString());
    }

    private static class UserInputObserver implements UserInputListener {

        @Override
        public void rotateClockwise(UserInputEvent event) {
            updateField();
        }

        @Override
        public void startFlow(UserInputEvent event) {

        }

        @Override
        public void loadLevel(UserInputEvent event) throws IOException {
            updateField();
        }
    }

    private static class WaterObserver implements WaterActionListener {

        @Override
        public void waterEndFlow(WaterActionEvent event) {

        }

        @Override
        public void stepEnd(WaterActionEvent event) {
            updateField();
        }
    }

    private static class GameObserver implements GameActionListener {

        @Override
        public void winEvent(GameActionEvent event) {
            System.out.println("WIN! :>");
        }

        @Override
        public void loseEvent(GameActionEvent event) {
            System.out.println("LOSE! D:");
        }

        @Override
        public void waterTick(GameActionEvent event) {
            updateField();
        }
    }
}