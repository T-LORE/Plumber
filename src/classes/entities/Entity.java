package classes.entities;

import classes.Cell;

public abstract class Entity {
    private Cell _cell;

    public void setCell(Cell cell) {
        _cell = cell;
        if (cell.getEntity() == null) {
            cell.setEntity(this);
        }
    }

    public Cell getCell() {

        return _cell;
    }
}