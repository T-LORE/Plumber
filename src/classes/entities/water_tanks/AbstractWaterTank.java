package classes.entities.water_tanks;

import classes.Cell;
import classes.Diameter;
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

public class AbstractWaterTank extends Entity {
    private ArrayList<AbstractWaterTankEnd> _ends = new ArrayList<AbstractWaterTankEnd>();
    private Water _water;
    protected ArrayList<WaterTankActionListener> _listeners = new ArrayList<WaterTankActionListener>();
    protected MaterialNode _material;
    private Diameter _diameter;
    
    public AbstractWaterTank() {
        super();
    }

    public Diameter getDiameter() {
        return _diameter;
    }

    public void setDiameter(Diameter diameter) {
        _diameter = diameter;
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

    public AbstractWaterTank getNeighbour(Direction direction) {
        Cell cell = getCell().getNeighbor(direction);
        if (cell == null)
        {
            return null;
        }
        Entity entity = cell.getEntity();
        if (entity instanceof AbstractWaterTank abstractWaterTank) {
            return abstractWaterTank;
        }
        return null;
    }

    public HashMap<Direction, AbstractWaterTank> getConnectedWaterTanks() {
        HashMap<Direction, AbstractWaterTank> connectedWaterTanks = new HashMap<>();
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
        // <<Материал трубы>>; <<Диаметр>>; <<Верхний выход (1 или 0)>>; <<Правый выход (1 или 0)>>; <<Нижний выход (1 или 0)>>; <<Левый выход (1 или 0)>>
        String[] configSplit = config.split(";");
        if (configSplit.length != 6) {
            throw new IllegalArgumentException("Invalid one material tank configuration: " + config);
        }

        String materialName = configSplit[0];
        String diameter = configSplit[1];
        String newConfig = materialName + ";" + diameter + ";";

        for (int i = 2; i < 6; i++) {
            newConfig += (configSplit[i].equals("1") ? materialName + ";" +  diameter: "null;0") + ";";
        }

        configureTankEndsToMaterial(newConfig, materialRoot);
    }

    public void configureTankEndsToMaterial(String config, MaterialNode materialRoot) {
        // <<Материал танка>>; <<диметр танка>>; <<материал верхнего конца>>; <<диметр верхнего конца>>; <<материал правого конца>>; <<диаметр правого конца>>; <<материал нижнего конца>>; <<диаметр нижнего конца>>; <материал левого конца>>; <<диаметр левого конца>>
        String[] configParts = config.split(";");
        if (configParts.length != 10) {
            throw new IllegalArgumentException("Invalid tank configuration: " + config);
        }

        MaterialNode pipeMaterial = MaterialNode.getMaterial(MaterialType.valueOf(configParts[0]));
        if (pipeMaterial == null) {
            throw new IllegalArgumentException("Invalid material: " + configParts[0]);
        }

        setMaterial(pipeMaterial);
        setDiameter(new Diameter(Integer.parseInt(configParts[1])));
        clearEnds();

        for (int i = 2; i < configParts.length; i += 2) {
            if (configParts[i].equals("null")) {
                continue;
            }
            
            MaterialNode material = MaterialNode.getMaterial(MaterialType.valueOf(configParts[i]));
            if (material == null) {
                throw new IllegalArgumentException("Invalid material: " + configParts[i]);
            }
            Diameter diameter = new Diameter(Integer.parseInt(configParts[i + 1]));
            addEnd(new MaterialWaterTankEnd(Direction.values()[(i - 2) / 2], material, diameter));
        }

    }
 
}
