package classes;

import java.awt.*;
import java.util.ArrayList;

public class Field {
    private int height;
    private int width;

    private ArrayList<Cell> cells = new ArrayList<Cell>();

    public Field(int height, int width) {
        this.height = height;
        this.width = width;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells.add(new Cell(new Point(i, j), this));
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCell(Point coords) {
        return cells.get(coords.x * width + coords.y);
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result += cells.get(i * width + j).toString();
            }
            result += "\n";
        }
        result += "\n";

        return result;
    }
}
