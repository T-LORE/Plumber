package classes.entities;

import classes.Cell;

public abstract class Entity {
    private Cell _cell;

    public void setCell(Cell cell) {
        _cell = cell;
    }

    protected Cell getCell() {
        return _cell;
    }
}