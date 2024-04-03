package classes;

import classes.entities.Entity;

import java.awt.*;

public class Cell {
    Point _cords;
    Entity _entity;
    Field _field;

    public Cell() {
    }

    public Cell(Point cords, Field field) {
        _field = field;
        _cords = cords;
    }

    public void setEntity(Entity entity) {
        _entity = entity;
    }

    public Entity getEntity() {
        return _entity;
    }

    public Point getCoords() {
        return _cords;
    }

    public Field getField() {
        return _field;
    }

    public void SetField(Field field, Point cords) {
        _field = field;
        _cords = cords;
    }


    public Cell getNeighbor(Direction direction) {
        Point neighborCoords = new Point(_cords);
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