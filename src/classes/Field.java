package classes;

import java.awt.*;
import java.util.ArrayList;

public class Field {
    private int _height;
    private int _width;

    private ArrayList<ArrayList<Cell>> _cells = new ArrayList<ArrayList<Cell>>();

    public Field(int _height, int _width) {
        this._height = _height;
        this._width = _width;

        for (int i = 0; i < _height; i++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j < _width; j++) {
                row.add(new Cell(new Point(i, j), this));
            }
            _cells.add(row);
        }
    }
    
    public void setCell(Cell cell)
    {
        Point cords = cell.getCoords();
        if (cords.x > _width)
        {
            throw new IllegalArgumentException("x is out of bounds " + cords.x);
        }

        if (cords.y > _height)
        {
            throw new IllegalArgumentException("y is out of bounds " + cords.y);
        }

        ArrayList<Cell> a = _cells.get(cords.y);
        a.add(cords.x, cell);
        
    }
    
    public int getHeight() {
        return _height;
    }

    public int getWidth() {
        return _width;
    }

    public Cell getCell(Point coords) {
        return _cells.get(coords.x).get(coords.y);
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                result += _cells.get(i).get(j).toString();
            }
            result += "\n";
        }
        result += "\n";

        return result;
    }
}
