package classes;

import classes.entities.water_tanks.Pipe;
import classes.events.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Game {
    public Field getGameField() {
        return _field;
    }

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

    public Game() {
        _player = new Player();
        _player.addListener(new PlayerObserver());
        setGameStatus(GameStatus.END_GAME_PHASE);
    }

    private void setGameStatus(GameStatus gameStatus) {
        _gameStatus = gameStatus;
    }

    public void prepareLevel(String levelPath) throws IOException {
        if (_water != null)
        {
            _water.stop();
        }
        _field = Field.loadFromFile(levelPath);
        _field.getDrain().addListener(new DrainObserver());
        _player.setActive(true);
        setGameStatus(GameStatus.CONSTRUCTION_PHASE);
    }

    private void rotateClockwise(Point cords){
        if (_gameStatus == GameStatus.CONSTRUCTION_PHASE) {
            Pipe pipe = _field.getPipeOnCords(cords);
            if (pipe != null) {
                pipe.rotateClockwise();
            }
        }
    }

    public void startWaterFlow() {
        if (_gameStatus == GameStatus.CONSTRUCTION_PHASE) {
            _water = _field.getSource().createWater();
            _water.flow();
            _water.addListener(new WaterObserver());
            setGameStatus(GameStatus.FLOW_PHASE);
        }
    }

    private void onDrainFilled() {
        if (_gameStatus == GameStatus.FLOW_PHASE) {
            fireWinEvent();
            _water.stop();
            setGameStatus(GameStatus.END_GAME_PHASE);
        }
    }

    private void onWaterEndFlow() {
        if (_gameStatus == GameStatus.FLOW_PHASE) {
            fireLoseEvent();
            _water.stop();
            setGameStatus(GameStatus.END_GAME_PHASE);
        }
    }

    public Field getField() {
        return _field;
    }

    public Player getPlayer() {
        return _player;
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
        public void stepEnd(WaterActionEvent event) {
            fireWaterTickEvent();
        }
    }

    private class DrainObserver implements DrainActionListener {

        @Override
        public void filled(DrainActionEvent event) {
            onDrainFilled();
        }
    }

    private class PlayerObserver implements PlayerActionListener {


        @Override
        public void rotateClockwise(PlayerActionEvent event) {
            Game.this.rotateClockwise(event.cords);
        }

        @Override
        public void startFlow(PlayerActionEvent event) {
            startWaterFlow();
        }

        @Override
        public void startLevel(PlayerActionEvent event) {
            try {
                prepareLevel(event.levelPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
