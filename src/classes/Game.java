package classes;

import classes.events.*;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    public enum GameStatus {
        END_GAME_PHASE,
        CONSTRUCTION_PHASE,
        FLOW_PHASE
    }

    private GameStatus _gameStatus;

    private Field _field;

    private Player _player;

    private Water _water;

    private ArrayList<GameActionListener> _listeners = new ArrayList<>();

    public Game(ConsoleUserInput eventSimulator) {
        UserInputObserver userInputObserver = new UserInputObserver();
        eventSimulator.addListener(userInputObserver);
        _player = new Player(eventSimulator);
    }

    private void setGameStatus(GameStatus gameStatus) {
        _gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return _gameStatus;
    }

    private void prepareLevel(String levelPath) throws IOException {
        _gameStatus = GameStatus.CONSTRUCTION_PHASE;
        DrainObserver observer = new DrainObserver();
        _field = Field.loadFromFile(levelPath);
        _player.setField(_field);
        _player.setActive(true);
        _field.getDrain().addListener(observer);
    }

    private void startWaterFlow() {
        if (_gameStatus != GameStatus.CONSTRUCTION_PHASE)
            return;
        _water = _field.getSource().createWater();
        _water.flow();
        WaterObserver observer = new WaterObserver();
        _water.addListener(observer);
        _gameStatus = GameStatus.FLOW_PHASE;
    }

    private void onDrainFilled() {
        fireWinEvent();
        _water.stop();
        _gameStatus = GameStatus.END_GAME_PHASE;
    }

    private void onWaterEndFlow() {
        fireLoseEvent();
        _water.stop();
        _gameStatus = GameStatus.END_GAME_PHASE;

    }

    private void onWaterPouredOut() {
        System.out.println("lost poured");
        fireLoseEvent();
        _water.stop();
        _gameStatus = GameStatus.END_GAME_PHASE;
    }

    public Field getField() {
        return _field;
    }

    public void addListener(GameActionListener listener) {
        _listeners.add(listener);
    }

    private void fireWinEvent() {
        for (GameActionListener listener : _listeners) {
            listener.winEvent(new GameActionEvent(this));
        }
    }

    private void fireLoseEvent() {
        for (GameActionListener listener : _listeners) {
            listener.loseEvent(new GameActionEvent(this));
        }
    }

    private void fireWaterTickEvent() {
        for (GameActionListener listener : _listeners) {
            listener.waterTick(new GameActionEvent(this));
        }
    }



    private class WaterObserver implements WaterActionListener {

        @Override
        public void waterEndFlow(WaterActionEvent event) {
            onWaterEndFlow();
        }

        @Override
        public void waterPouredOut(WaterActionEvent event) {
            onWaterPouredOut();
        }

        @Override
        public void tickEnd(WaterActionEvent event) {
            fireWaterTickEvent();
        }
    }

    private class DrainObserver implements DrainActionListener {

        @Override
        public void filled(DrainActionEvent event) {
            onDrainFilled();
        }
    }

    private class UserInputObserver implements UserInputListener {

        @Override
        public void rotateClockwise(UserInputEvent event) {

        }

        @Override
        public void startFlow(UserInputEvent event) {
            startWaterFlow();
        }

        @Override
        public void loadLevel(UserInputEvent event) throws IOException {
            prepareLevel(event.levelPath);
        }
    }
}
