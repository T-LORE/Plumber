package classes;

import classes.entities.Entity;
import classes.entities.water_tanks.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Field {

    private final static int MAX_WIDTH = 15;
    private final static int MAX_HEIGHT = 7;

    private int _height;
    private int _width;

    private ArrayList<ArrayList<Cell>> _cells = new ArrayList<ArrayList<Cell>>();
    private ArrayList<Pipe> _pipes = new ArrayList<Pipe>();
    private ArrayList<Fitting> _fittings = new ArrayList<Fitting>();
    private Source _source;
    private Drain _drain;
    
    public Field(int _height, int _width) {
        this._height = _height;
        this._width = _width;

        for (int i = 0; i < _height; i++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j < _width; j++) {
                row.add(new Cell(new Point(j, i), this));
            }
            _cells.add(row);
        }
    }
    
    public void setCell(Cell cell) {
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
        return _source;
    }

    public Drain getDrain() {
        return _drain;
    }

    public AbstractRotatableWaterTanks getRotatableTankOnCords(Point cords) {
        Entity entity = getCell(cords).getEntity();
        if (entity instanceof AbstractRotatableWaterTanks) {
            return (AbstractRotatableWaterTanks) entity;
        } else {
            return null;
        }
    }
   
    public ArrayList<Pipe> getPipes() {
        return _pipes;
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

    public static Field loadFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String[] size = reader.readLine().split(" ");
        int width = Integer.parseInt(size[0]);
        int height = Integer.parseInt(size[1]);
        if (height < 2 || width < 2 || height > MAX_HEIGHT || width > MAX_WIDTH) {
            reader.close();
            throw new IllegalArgumentException("Invalid field dimensions (min: 2x2, max: 15x7): " + height + " " + width);
        }

        //TODO: to separate method configureField
        Field field = new Field(height, width);
        for (int i = 0; i < height ; i++) {
            String[] cells = reader.readLine().split("");
            for (int j = 0; j < width; j++) {
                configurateCell(cells[j], field, new Point(j, i));
            }
        }
        
        if (field.getSource() == null) {
            reader.close();
            throw new IllegalArgumentException("No source in the field");
        }
        if (field.getDrain() == null) {
            reader.close();
            throw new IllegalArgumentException("No drain in the field");
        }

        //TODO: to separate method configureElements
        MaterialNode root = MaterialNode.configure();
        field.getSource().configureTankWithOneMaterial(reader.readLine(), root);
        field.getDrain().configureTankWithOneMaterial(reader.readLine(), root);
        for (Pipe pipe : field.getPipes()) {
            pipe.configureTankWithOneMaterial(reader.readLine(), root);
        }
        for (Fitting fitting : field.getFittings()) {
            fitting.configureTankEndsToMaterial("UNIVERSAL;10;"+reader.readLine(), root);
        }
        reader.close();

        return field;
    }

    public ArrayList<Fitting> getFittings() {
        return _fittings;
    }

    private static void configurateCell(String cell, Field field, Point coords) {
        switch (cell) {
            case "□":
                break;
            case "s":
                field.addSimpleSource(coords);
                break;
            case "d":
                field.addSimpleDrain(coords);
                break;
            case "p":
                field.addSimplePipe(coords);
                break;
                case "f":
                field.addSimpleFitting(coords);
                break;
            default:
                throw new IllegalArgumentException("Invalid cell type: " + cell);
        }
    }

    private boolean addCellItem(Point coords, Entity entity) {
        Cell cell = getCell(coords);
        if (cell.getEntity() != null) {
            return false;
        }
        cell.setEntity(entity);
        return true;
    }

    public void addSimpleFitting(Point coords) {
        Fitting fitting = new Fitting();
        if (addCellItem(coords, fitting)) {
            _fittings.add(fitting);
        }
    }

    public void addSimplePipe(Point coords) {
        Pipe pipe = new Pipe();
        if (addCellItem(coords, pipe)) {
            _pipes.add(pipe);
        }
    }

    public void addSimpleSource(Point coords) {
        if (getSource() != null) {
            throw new IllegalArgumentException("Multiple sources in the field");
        }

        Source source = new Source();
        if (addCellItem(coords, source)) {
            _source = source;
        }
    }

    public void addSimpleDrain(Point coords) {
        if (getDrain() != null) {
            throw new IllegalArgumentException("Multiple drains in the field");
        }

        Drain drain = new Drain();
        if (addCellItem(coords, drain)) {
            _drain = drain;
        }
    }
}
