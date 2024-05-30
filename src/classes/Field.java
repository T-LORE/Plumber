package classes;

import classes.entities.Entity;
import classes.entities.water_tanks.Drain;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.Source;
import classes.entities.water_tanks.WaterTank;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.entities.water_tanks.water_tanks_ends.DefaultWaterTankEnd;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Field {

    private final static int MAX_WIDTH = 15;
    private final static int MAX_HEIGHT = 7;

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
        if (cords.x > _width || cords.y > _height || cords.x < 0 || cords.y < 0) {
            throw new IllegalArgumentException("Invalid cell coordinates: " + cords.x + " " + cords.y);
        }
        
        ArrayList<Cell> row = _cells.get(cords.y);
        row.add(cords.x, cell);
        if (cell.getField() == null) {
            cell.setField(this, cords);
        }
        
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

    private static HashMap<String, ArrayList<Direction>> _parseDictionary;

    private static void cacheParseDictionary() {
        ArrayList<WaterTank> watertanks = new ArrayList<WaterTank>();
        // 16 possible pipes type
        for (int i = 0; i < 16; i++)
        {
            WaterTank watertank = new WaterTank();
            ArrayList<Direction> possibleDirections = new ArrayList<Direction>();

            // bit mask
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

            watertank.addEnds(createDefaultEndsFromDirections(possibleDirections));
            watertanks.add(watertank);
        }
        _parseDictionary = new HashMap<String, ArrayList<Direction>>();
        for (WaterTank tank : watertanks)
        {
            _parseDictionary.put(tank.toString(), getPossibleDirectionsFromTank(tank));
        }
    }

    private static ArrayList<AbstractWaterTankEnd> createDefaultEndsFromDirections(ArrayList<Direction> directions) {
        ArrayList<AbstractWaterTankEnd> ends = new ArrayList<AbstractWaterTankEnd>();
        for (Direction direction : directions) {
            ends.add(new DefaultWaterTankEnd(direction));
        }
        return ends;
    }

    private static ArrayList<Direction> getPossibleDirectionsFromTank(WaterTank tank) {
        ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
        for (AbstractWaterTankEnd end : tank.getEnds()) {
            possibleDirections.add(end.getDirection());
        }
        return possibleDirections;
    }

    public static Field loadFromFile(String filename) throws IOException {

        if (_parseDictionary == null)
        {
            cacheParseDictionary();
        }

        Source source = new Source();
        Drain drain = new Drain();
        Cell empty = new Cell();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String[] dimensions = reader.readLine().split(" ");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        if (height < 2 || width < 2) {
            throw new IllegalArgumentException("Invalid field dimensions: " + height + " " + width);
        }

        if (height > MAX_HEIGHT || width > MAX_WIDTH) {
            throw new IllegalArgumentException("Field size too big (max 15x7): " + height + " " + width);
        }

        Field field = new Field(height, width);
        Source newSource = null;
        Drain newDrain = null;
        for (int i = 0; i < height ; i++) {
            String[] cells = reader.readLine().split("");
            for (int j = 0; j < width; j++) {
                String cell = cells[j];
                Cell newCell = new Cell(new Point(j, i), field);
                if (cell.equals(empty.toString())) {
                    field.setCell(newCell);
                } else if (cell.equals(source.toString())) {
                    if (newSource != null) {
                        throw new IllegalArgumentException("Multiple sources in the field");
                    }
                    newSource = new Source();

                    //TODO: add the ability to add directions for source
                    newSource.addEnd(new DefaultWaterTankEnd(Direction.UP));
                    newSource.addEnd(new DefaultWaterTankEnd(Direction.DOWN));
                    newSource.addEnd(new DefaultWaterTankEnd(Direction.LEFT));
                    newSource.addEnd(new DefaultWaterTankEnd(Direction.RIGHT));

                    newSource.setCell(newCell);
                    newCell.setEntity(newSource);
                    field.setCell(newCell);
                } else if (cell.equals(drain.toString())) {
                    if (newDrain != null) {
                        throw new IllegalArgumentException("Multiple drains in the field");
                    }
                    newDrain = new Drain();

                    //TODO: add the ability to add directions for drain
                    newDrain.addEnd(new DefaultWaterTankEnd(Direction.UP));
                    newDrain.addEnd(new DefaultWaterTankEnd(Direction.DOWN));
                    newDrain.addEnd(new DefaultWaterTankEnd(Direction.LEFT));
                    newDrain.addEnd(new DefaultWaterTankEnd(Direction.RIGHT));

                    newDrain.setCell(newCell);
                    newCell.setEntity(newDrain);
                    field.setCell(newCell);
                } else  if (_parseDictionary.containsKey(cell)){
                    ArrayList<Direction> possibleDirections = _parseDictionary.get(cell);
                    Pipe pipe = new Pipe();
                    pipe.addEnds(createDefaultEndsFromDirections(possibleDirections));

                    pipe.setCell(newCell);
                    newCell.setEntity(pipe);
                    field.setCell(newCell);
                } else {
                    throw new IllegalArgumentException("Invalid cell type: " + cell);
                }
            }
        }

        reader.close();
        if (field.getSource() == null) {
            throw new IllegalArgumentException("No source in the field");
        }
        if (field.getDrain() == null) {
            throw new IllegalArgumentException("No drain in the field");
        }
        return field;
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
