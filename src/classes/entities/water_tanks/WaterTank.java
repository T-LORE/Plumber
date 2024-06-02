package classes.entities.water_tanks;

import classes.Cell;
import classes.Direction;
import classes.MaterialNode;
import classes.MaterialNode.MaterialType;
import classes.Water;
import classes.entities.Entity;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.entities.water_tanks.water_tanks_ends.MaterialWaterTankEnd;
import classes.events.WaterTankActionEvent;
import classes.events.WaterTankActionListener;

import java.util.ArrayList;
import java.util.HashMap;

public class WaterTank extends Entity {
    private ArrayList<AbstractWaterTankEnd> _ends = new ArrayList<AbstractWaterTankEnd>();
    private Water _water;
    protected ArrayList<WaterTankActionListener> _listeners = new ArrayList<WaterTankActionListener>();
    protected MaterialNode _material;
    
    public WaterTank() {
        super();
    }

    public boolean addEnd(AbstractWaterTankEnd end) {
        if (_ends.contains(end)) {
            System.out.println("End already exists");
            return false;
        }

        if (end.setParentWaterTank(this)) {
            _ends.add(end);
            return true;
        }
        return false;      
    }

    public void addEnds(ArrayList<AbstractWaterTankEnd> ends) {
        for (AbstractWaterTankEnd end : ends) {
            addEnd(end);
        }
    }

    public void removeEnd(AbstractWaterTankEnd end) {
        _ends.remove(end);
    }

    public void removeEnds(ArrayList<AbstractWaterTankEnd> ends) {
        for (AbstractWaterTankEnd end : ends) {
            removeEnd(end);
        }
    }

    public void clearEnds() {
        _ends.clear();
    }

    public ArrayList<AbstractWaterTankEnd> getEnds() {
        return _ends;
    }

    public AbstractWaterTankEnd getEnd(Direction direction) {
        for (AbstractWaterTankEnd end : _ends) {
            if (end.getDirection() == direction) {
                return end;
            }
        }
        return null;
    }

    public boolean fill(Water water){
        if (_water == null)
        {
            _water = water;
            fireFilledEvent();
            return true;
        }
        return false;
    }

    public WaterTank getNeighbour(Direction direction) {
        Cell cell = getCell().getNeighbor(direction);
        if (cell == null)
        {
            return null;
        }
        Entity entity = cell.getEntity();
        if (entity instanceof WaterTank waterTank) {
            return waterTank;
        }
        return null;
    }

    public HashMap<Direction, WaterTank> getConnectedWaterTanks() {
        HashMap<Direction, WaterTank> connectedWaterTanks = new HashMap<>();
        for (AbstractWaterTankEnd end : _ends) {
            if (end.getConnectedNeighbour() != null) {
                connectedWaterTanks.put(end.getDirection(), end.getConnectedNeighbour());
            }
        }
        return connectedWaterTanks;
    }

    public Water getWater() {

        return _water;
    }

    public void addListener(WaterTankActionListener listener) {
        _listeners.add(listener);
    }

    private void fireFilledEvent() {
        for (WaterTankActionListener listener : _listeners) {
            listener.tankFilled(new WaterTankActionEvent(this));
        }
    }

    private HashMap<String, String> _dict = null;
    @Override
    public String toString() {
        if (_dict == null)
        {
            _dict = new HashMap<String, String>();
            _dict.put("00000", "○");
            _dict.put("00010", "╶");
            _dict.put("00100", "╴");
            _dict.put("00110", "═");
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
            _dict.put("10011", "┗");
            _dict.put("10101", "┛");
            _dict.put("10111", "┻");
            _dict.put("11001", "┃");
            _dict.put("11011", "┣");
            _dict.put("11101", "┫");
            _dict.put("11111", "╋");
        }
         
        String key = "";
        key += isHaveEnd(Direction.UP) ? "1" : "0";
        key += isHaveEnd(Direction.DOWN) ? "1" : "0";
        key += isHaveEnd(Direction.LEFT) ? "1" : "0";
        key += isHaveEnd(Direction.RIGHT) ? "1" : "0";
        key += _water == null ? "0" : "1";

        return _dict.get(key);
    }

    private boolean isHaveEnd(Direction direction) {
        for (AbstractWaterTankEnd end : _ends) {
            if (end.getDirection() == direction) {
                return true;
            }
        }
        return false;
    }

    public MaterialNode getMaterial() {
        return _material;    
    }

    public void setMaterial(MaterialNode material) {
        _material = material;
    }

    public void configureTankWithOneMaterial(String config, MaterialNode materialRoot) {
        // <<Материал трубы>>; <<Верхний выход (1 или 0)>>; <<Правый выход (1 или 0)>>; <<Нижний выход (1 или 0)>>; <<Левый выход (1 или 0)>>
        String[] configSplit = config.split(";");
        if (configSplit.length != 5) {
            throw new IllegalArgumentException("Invalid one material tank configuration: " + config);
        }

        String materialName = configSplit[0];
        String newConfig = materialName + ";";

        for (int i = 1; i < 5; i++) {
            newConfig += (configSplit[i].equals("1") ? materialName : "null") + ";";
        }

        configureTankEndsToMaterial(newConfig, materialRoot);
    }

    public void configureTankEndsToMaterial(String config, MaterialNode materialRoot) {
        // <<Материал танка>>; <<материал верхнего конца>>; <<материал правого конца>>; <<материал нижнего конца>>; <материал левого конца>>
        String[] configParts = config.split(";");
        if (configParts.length != 5) {
            throw new IllegalArgumentException("Invalid tank configuration: " + config);
        }

        MaterialNode pipeMaterial = materialRoot.getChildMaterial(MaterialType.valueOf(configParts[0]));
        if (pipeMaterial == null) {
            throw new IllegalArgumentException("Invalid material: " + configParts[0]);
        }

        setMaterial(pipeMaterial);
        clearEnds();

        for (int i = 1; i < 5; i++) {
            if (configParts[i].equals("null")) {
                continue;
            }
            
            MaterialNode outsideMaterial = materialRoot.getChildMaterial(MaterialType.valueOf(configParts[i]));
            if (outsideMaterial == null) {
                throw new IllegalArgumentException("Invalid end material: " + configParts[i]);
            }
            addEnd(new MaterialWaterTankEnd(Direction.values()[i - 1], outsideMaterial));
        }
    }
 
}
