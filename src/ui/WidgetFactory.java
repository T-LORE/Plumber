package ui;

import classes.Cell;
import classes.Field;
import classes.Player;
import classes.entities.water_tanks.Drain;
import classes.entities.water_tanks.Fitting;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.Source;
import classes.entities.water_tanks.AbstractWaterTank;

import java.util.HashMap;
import java.util.Map;

public class WidgetFactory {

    private final Map<Cell, CellWidget> cells = new HashMap<>();

    private final Map<AbstractWaterTank, CellItemWidget> waterTanks = new HashMap<>();

    public FieldWidget createFieldWidget(Field field) {
        return new FieldWidget(field, this);
    }

    public CellWidget createCellWidget(Cell cell) {
        if (cells.containsKey(cell)) {
            throw new IllegalStateException("Cell" + cell.getCoords().getX() + " " + cell.getCoords().getY() + " already has a widget");
        }

        CellWidget cellWidget = new CellWidget(cell);

        AbstractWaterTank abstractWaterTank = (AbstractWaterTank) cell.getEntity();
        if (abstractWaterTank != null) {
            if (waterTanks.containsKey(abstractWaterTank)) {
                throw new IllegalStateException("Water tank already has a widget");
            }

            if (abstractWaterTank instanceof Pipe) {
                waterTanks.put(abstractWaterTank, new PipeWidget((Pipe) abstractWaterTank));
                cellWidget.addItem(waterTanks.get(abstractWaterTank));
            }

            if (abstractWaterTank instanceof Source) {
                waterTanks.put(abstractWaterTank, new SourceWidget((Source) abstractWaterTank));
                cellWidget.addItem(waterTanks.get(abstractWaterTank));
            }

            if (abstractWaterTank instanceof Drain) {
                waterTanks.put(abstractWaterTank, new DrainWidget((Drain) abstractWaterTank));
                cellWidget.addItem(waterTanks.get(abstractWaterTank));
            }

            if (abstractWaterTank instanceof Fitting) {
                waterTanks.put(abstractWaterTank, new FittingWidget((Fitting) abstractWaterTank));
                cellWidget.addItem(waterTanks.get(abstractWaterTank));
            }
        }

        cells.put(cell, cellWidget);
        return cellWidget;        
    }

    public PlayerActionWidget createPlayerActionWidget(GamePanel panel, Player player) {
        return new PlayerActionWidget(panel, this, player);
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
