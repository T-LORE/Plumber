package classes;

import classes.entities.Entity;

import java.awt.*;

public class Cell {
    Point _coords;
    Entity _entity;
    Field _field;

    public Cell(Point coords, Field field) {
        _coords = coords;
        _field = field;
    }

    public void setEntity(Entity entity) {
        _entity = entity;
    }

    public Entity getEntity() {
        return _entity;
    }

    public Point getCoords() {
        return _coords;
    }

    public Field getField() {
        return _field;
    }

    public Cell getNeighbor(Direction direction) {
        Point neighborCoords = new Point(_coords);
        switch (direction) {
            case UP:
                neighborCoords.translate(-1, 0);
                break;
            case DOWN:
                neighborCoords.translate(1, 0);
                break;
            case LEFT:
                neighborCoords.translate(0, -1);
                break;
            case RIGHT:
                neighborCoords.translate(0, 1);
                break;
        }
        if (neighborCoords.x < 0 || neighborCoords.x >= _field.getHeight() || neighborCoords.y < 0 || neighborCoords.y >= _field.getWidth()) {
            return null;
        }
        return _field.getCell(neighborCoords);
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