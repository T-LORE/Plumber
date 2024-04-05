package classes;

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
        _field.getSource().createWater().flow();
        // TODO: water listener
        _gameStatus = GameStatus.FLOW_PHASE;
    }

    private void onDrainFilled() {
        // TODO: win event
        _gameStatus = GameStatus.END_GAME_PHASE;
    }

    private void onWaterEndFlow() {
        // TODO: lost event
        _gameStatus = GameStatus.END_GAME_PHASE;
    }

    private void onWaterPouredOut() {
        // TODO: lost event
        _gameStatus = GameStatus.END_GAME_PHASE;
    }

}
