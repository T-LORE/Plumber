package ui;

import classes.Cell;
import classes.Field;
import classes.Game;
import classes.Player;
import classes.entities.water_tanks.Drain;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.Source;
import classes.entities.water_tanks.WaterTank;

import java.util.HashMap;
import java.util.Map;

public class WidgetFactory {

    private final Map<Cell, CellWidget> cells = new HashMap<>();

    private final Map<WaterTank, CellItemWidget> waterTanks = new HashMap<>();

    public FieldWidget createFieldWidget(Field field) {
        return new FieldWidget(field, this);
    }

    public CellWidget createCellWidget(Cell cell) {
        if (cells.containsKey(cell)) {
            throw new IllegalStateException("Cell" + cell.getCoords().getX() + " " + cell.getCoords().getY() + " already has a widget");
        }

        CellWidget cellWidget = new CellWidget(cell);

        WaterTank waterTank = (WaterTank) cell.getEntity();
        if (waterTank != null) {
            if (waterTanks.containsKey(waterTank)) {
                throw new IllegalStateException("Water tank already has a widget");
            }

            if (waterTank instanceof Pipe) {
                waterTanks.put(waterTank, new PipeWidget((Pipe) waterTank));
                cellWidget.addItem(waterTanks.get(waterTank));
            }

            if (waterTank instanceof Source) {
                waterTanks.put(waterTank, new SourceWidget());
                cellWidget.addItem(waterTanks.get(waterTank));
            }

            if (waterTank instanceof Drain) {
                waterTanks.put(waterTank, new DrainWidget());
                cellWidget.addItem(waterTanks.get(waterTank));
            }
        }

        cells.put(cell, cellWidget);
        return cellWidget;        
    }

    public PlayerActionWidget createPlayerActionWidget(GamePanel panel, Game game, FieldWidget field, Player player) {
        return new PlayerActionWidget(panel, game, this, player);
    }

    public TexturedButton createStartWaterflowButton() {
        return new TexturedButton("button_startwaterflow.png", 250, 80);
    }

    public TexturedButton createRestartButton() {
        return new TexturedButton("button_restart.png", 250, 80);
    }

    public TexturedButton createLoadLevelButton() {
        return new TexturedButton("button_load.png", 250, 80);
    }

}
