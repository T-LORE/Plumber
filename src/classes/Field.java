package classes;

import java.awt.*;
import java.util.ArrayList;

public class Field {
    private int height;
    private int width;

    private ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();

    public Field(int height, int width) {
        this.height = height;
        this.width = width;

        for (int i = 0; i < height; i++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(new Point(i, j), this));
            }
            cells.add(row);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCell(Point coords) {
        return cells.get(coords.x).get(coords.y);
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result += cells.get(i).get(j).toString();
            }
            result += "\n";
        }
        result += "\n";

        return result;
    }
}
