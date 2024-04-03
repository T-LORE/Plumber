package classes.entities.water_tanks;

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
            WaterTank waterTank = (WaterTank) getCell().getNeighbor(direction).getEntity();
            if (waterTank != null) {
                result.put(direction, waterTank);
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
            _dict.put("00000", "\u25CB");
            _dict.put("00010", "\u2578");
            _dict.put("00100", "\u2578");
            _dict.put("00110", "\u2500");
            _dict.put("01000", "\u2575");
            _dict.put("01010", "\u2554");
            _dict.put("01100", "\u2557");
            _dict.put("01110", "\u2566");
            _dict.put("10000", "\u2575");
            _dict.put("10010", "\u255A");
            _dict.put("10100", "\u255D");
            _dict.put("10110", "\u2569");
            _dict.put("11000", "\u2551");
            _dict.put("11010", "\u2560");
            _dict.put("11100", "\u2563");
            _dict.put("11110", "\u256C");

            _dict.put("00001", "\u25CF");
            _dict.put("00011", "\u2578");
            _dict.put("00101", "\u257A");
            _dict.put("00111", "\u2501");
            _dict.put("01001", "\u257B");
            _dict.put("01011", "\u255F");
            _dict.put("01101", "\u2563");
            _dict.put("01111", "\u256B");
            _dict.put("10001", "\u257B");
            _dict.put("10011", "\u255F");
            _dict.put("10101", "\u2563");
            _dict.put("10111", "\u256B");
            _dict.put("11001", "\u257A");
            _dict.put("11011", "\u255F");
            _dict.put("11101", "\u2563");
            _dict.put("11111", "\u256B");
        }
         
        String key = "";
        key += _possibleDirections.contains(Direction.UP) ? "1" : "0";
        key += _possibleDirections.contains(Direction.RIGHT) ? "1" : "0";
        key += _possibleDirections.contains(Direction.LEFT) ? "1" : "0";
        key += _possibleDirections.contains(Direction.RIGHT) ? "1" : "0";
        key += _water == null ? "0" : "1";

        return _dict.get(key);
    }

 
}
