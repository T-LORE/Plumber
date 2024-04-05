package classes;

import classes.events.DrainActionEvent;
import classes.events.DrainActionListener;
import classes.events.WaterActionEvent;
import classes.events.WaterActionListener;

import java.io.IOException;

public class Game {
    public enum GameStatus {
        END_GAME_PHASE,
        CONSTRUCTION_PHASE,
        FLOW_PHASE
    }

    private GameStatus _gameStatus;

    private Field _field;

    private Player _player;

    private void setGameStatus(GameStatus gameStatus) {
        _gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return _gameStatus;
    }

    private void prepareLeve(String levelPath) throws IOException {
        _gameStatus = GameStatus.CONSTRUCTION_PHASE;
        _field = Field.loadFromFile(levelPath);
    }

    private void startWaterFlow() {
        if (_gameStatus != GameStatus.CONSTRUCTION_PHASE)
            return;
        Water water = _field.getSource().createWater();
        WaterObserver observer = new WaterObserver();
        water.addListener(observer);
        _gameStatus = GameStatus.FLOW_PHASE;
    }

    private void onDrainFilled() {
        System.out.println("win!");
        // TODO: win event
        _gameStatus = GameStatus.END_GAME_PHASE;
    }

    private void onWaterEndFlow() {
        System.out.println("lost end flow");
        // TODO: lost event
        _gameStatus = GameStatus.END_GAME_PHASE;

    }

    private void onWaterPouredOut() {
        System.out.println("lost poured");
        // TODO: lost event
        _gameStatus = GameStatus.END_GAME_PHASE;
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
    }

    private class DrainObserver implements DrainActionListener {

        @Override
        public void filled(DrainActionEvent event) {
            onDrainFilled();
        }
    }
}
