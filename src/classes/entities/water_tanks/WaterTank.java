package classes.entities.water_tanks;

import classes.Cell;
import classes.Direction;
import classes.Water;
import classes.entities.Entity;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class WaterTank extends Entity {
    private ArrayList<Direction> _possibleDirections;
    private Water _water;

    public WaterTank() {
        _possibleDirections = new ArrayList<Direction>();
    }

    public void addPossibleDirection(Direction direction) {
        if (!_possibleDirections.contains(direction))
        {
           _possibleDirections.add(direction);
        }
    }

    public void addPossibleDirections(ArrayList<Direction> directions) {
        for (Direction direction : directions) {
            addPossibleDirection(direction);
        }
    }

    public void removePossibleDirection(Direction direction) {
        _possibleDirections.remove(direction);
    }

    public void removePossibleDirections(ArrayList<Direction> directions) {
        for (Direction direction : directions) {
            removePossibleDirection(direction);
        }
    }

    public void clearPossibleDirections() {
        _possibleDirections.clear();
    }

    public ArrayList<Direction> getPossibleDirections() {
        return new ArrayList<Direction>(_possibleDirections);
    }

    public boolean fillFromDirection(Direction direction, Water water) {
        if (_possibleDirections.contains(direction)) {
            return fill(water);
        }
        return false;
    }

    protected boolean fill(Water water){
        if (_water == null)
        {
            _water = water;
            return true;
        }
        return false;
    }

    public boolean reachableFrom(Direction direction) {
        return _possibleDirections.contains(direction);
    }

    public HashMap<Direction, WaterTank> getReachableWaterTanks() {
        HashMap<Direction, WaterTank> result = new HashMap<Direction, WaterTank>();
        for (Direction direction : _possibleDirections) {
            Cell cell = getCell().getNeighbor(direction);
            if (cell == null)
            {
                continue;
            }
            Entity entity = cell.getEntity();
            if (entity instanceof WaterTank waterTank) {
                if (waterTank.reachableFrom(direction.turnAround()))
                {
                    result.put(direction.turnAround(), waterTank);
                }
            }
        }
        return result;
    }

    private HashMap<String, String> _dict = null;
    @Override
    public String toString() {
        if (_dict == null)
        {
            _dict = new HashMap<String, String>();
            _dict.put("00000", "○");
            _dict.put("00010", "╸");
            _dict.put("00100", "╸");
            _dict.put("00110", "─");
            _dict.put("01000", "╵");
            _dict.put("01010", "╔");
            _dict.put("01100", "╗");
            _dict.put("01110", "╦");
            _dict.put("10000", "╵");
            _dict.put("10010", "╚");
            _dict.put("10100", "╝");
            _dict.put("10110", "╩");
            _dict.put("11000", "║");
            _dict.put("11010", "╠");
            _dict.put("11100", "╣");
            _dict.put("11110", "╬");

            _dict.put("00001", "●");
            _dict.put("00011", "╸");
            _dict.put("00101", "╺");
            _dict.put("00111", "━");
            _dict.put("01001", "╻");
            _dict.put("01011", "┏");
            _dict.put("01101", "┓");
            _dict.put("01111", "┳");
            _dict.put("10001", "╹");
            _dict.put("10011", "┛");
            _dict.put("10101", "┗");
            _dict.put("10111", "┻");
            _dict.put("11001", "┃");
            _dict.put("11011", "┣");
            _dict.put("11101", "┫");
            _dict.put("11111", "╋");
        }
         
        String key = "";
        key += _possibleDirections.contains(Direction.UP) ? "1" : "0";
        key += _possibleDirections.contains(Direction.DOWN) ? "1" : "0";
        key += _possibleDirections.contains(Direction.LEFT) ? "1" : "0";
        key += _possibleDirections.contains(Direction.RIGHT) ? "1" : "0";
        key += _water == null ? "0" : "1";

        return _dict.get(key);
    }

 
}
