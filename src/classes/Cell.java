package classes;

import classes.entities.Entity;

import java.awt.*;

public class Cell {
    private Point _cords;
    private Entity _entity;
    private Field _field;

    public Cell() {
    }

    public Cell(Point cords, Field field) {
        _field = field;
        _cords = cords;
    }
    
    public void setField(Field field, Point cords) {
        _cords = cords;
        _field = field;
        _field.setCell(this);
    }

    public Field getField() {
        return _field;
    }

    public void setEntity(Entity entity) {
        _entity = entity;
        if (_entity.getCell() == null) {
            _entity.setCell(this);
        }
    }

    public Entity getEntity() {
        return _entity;
    }

    public Point getCoords() {
        return _cords;
    }


    public Cell getNeighbor(Direction direction) {
        if (_field == null)
            return null;
        Point neighborCoords = new Point(_cords);
        switch (direction) {
            case UP:
                neighborCoords.translate(0, -1);
                break;
            case DOWN:
                neighborCoords.translate(0, 1);
                break;
            case LEFT:
                neighborCoords.translate(-1, 0);
                break;
            case RIGHT:
                neighborCoords.translate(1, 0);
                break;
        }
        if (neighborCoords.x < 0 || neighborCoords.x >= _field.getHeight() || neighborCoords.y < 0 || neighborCoords.y >= _field.getWidth()) {
            return null;
        }
        return _field.getCell(neighborCoords);
    }

    public void setCords(Point cords) {
        _cords = cords;
    }

    @Override
    public String toString() {
        String result = "\u25A1";
        if (_entity != null) {
            result = _entity.toString();
        }
        return  result;
    }
}