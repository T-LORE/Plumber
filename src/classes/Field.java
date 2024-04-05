package classes;

import classes.entities.Entity;
import classes.entities.water_tanks.Drain;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.Source;
import classes.entities.water_tanks.WaterTank;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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
        return _cells.get(coords.y).get(coords.x);
    }

    public Source getSource() {
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                if (_cells.get(i).get(j).getEntity() instanceof Source) {
                    return (Source) _cells.get(i).get(j).getEntity();
                }
            }
        }
        return null;
    }

    public Drain getDrain() {
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                if (_cells.get(i).get(j).getEntity() instanceof Drain) {
                    return (Drain) _cells.get(i).get(j).getEntity();
                }
            }
        }
        return null;
    }

    public Pipe getPipeOnCords(Point cords) {
        Entity entity = getCell(cords).getEntity();
        if (entity instanceof Pipe) {
            return (Pipe) entity;
        } else {
            return null;
        }
    }

    private static HashMap<String, ArrayList<Direction>> _parseDict;

    public static Field loadFromFile(String filename) throws IOException {

        //cache _parseDict;
        if (_parseDict == null)
        {
            // create all watertanks with all possible directions
            ArrayList<Direction> directions = new ArrayList<Direction>();
            directions.add(Direction.UP);
            directions.add(Direction.DOWN);
            directions.add(Direction.LEFT);
            directions.add(Direction.RIGHT);
            ArrayList<WaterTank> watertanks = new ArrayList<WaterTank>();
            for (int i = 0; i < 16; i++)
            {
                if (i == 12)
                {
                    int a = 2;
                }
                WaterTank watertank = new WaterTank();
                ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
                if ((i & 1) == 1)
                {
                    possibleDirections.add(Direction.UP);
                }
                if ((i & 2) == 2)
                {
                    possibleDirections.add(Direction.DOWN);
                }
                if ((i & 4) == 4)
                {
                    possibleDirections.add(Direction.LEFT);
                }
                if ((i & 8) == 8)
                {
                    possibleDirections.add(Direction.RIGHT);
                }
                watertank.addPossibleDirections(possibleDirections);
                watertanks.add(watertank);
            }
            _parseDict = new HashMap<String, ArrayList<Direction>>();
            for (WaterTank tank : watertanks)
            {
                _parseDict.put(tank.toString(), tank.getPossibleDirections());
            }
        }
        Source source = new Source();
        Drain drain = new Drain();
        Cell empty = new Cell();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String[] dimensions = reader.readLine().split(" ");
        int height = Integer.parseInt(dimensions[0]);
        int width = Integer.parseInt(dimensions[1]);
        System.out.println("height: " + height + " width: " + width);

        Field field = new Field(height, width);

        for (int i = 0; i < height ; i++) {
            String[] cells = reader.readLine().split("");
            for (int j = 0; j < width; j++) {
                String cell = cells[j];
                Cell newCell = new Cell(new Point(j, i), field);
                if (cell.equals(empty.toString())) {
                    field.setCell(newCell);
                } else if (cell.equals(source.toString())) {
                    Source newSource = new Source();
                    newSource.addPossibleDirection(Direction.UP);
                    newSource.addPossibleDirection(Direction.DOWN);
                    newSource.addPossibleDirection(Direction.LEFT);
                    newSource.addPossibleDirection(Direction.RIGHT);

                    newSource.setCell(newCell);
                    newCell.setEntity(newSource);
                    field.setCell(newCell);
                } else if (cell.equals(drain.toString())) {
                    Drain newDrain = new Drain();
                    newDrain.addPossibleDirection(Direction.UP);
                    newDrain.addPossibleDirection(Direction.DOWN);
                    newDrain.addPossibleDirection(Direction.LEFT);
                    newDrain.addPossibleDirection(Direction.RIGHT);

                    newDrain.setCell(newCell);
                    newCell.setEntity(newDrain);
                    field.setCell(newCell);
                } else  if (_parseDict.containsKey(cell)){
                    ArrayList<Direction> possibleDirections = _parseDict.get(cell);
                    Pipe pipe = new Pipe();
                    pipe.addPossibleDirections(possibleDirections);

                    pipe.setCell(newCell);
                    newCell.setEntity(pipe);
                    field.setCell(newCell);
                } else {
                    System.out.println("Invalid cell type: <" + cell + "> at position " + i + " " + j);
                }
            }
        }

        reader.close();
        return field;
    }

    public void saveToFile(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(this.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save field to file", e);
        }
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                result += _cells.get(i).get(j).toString();
            }
            result += "\n";
        }

        return result;
    }
}
