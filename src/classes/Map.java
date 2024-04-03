package classes;

import classes.entities.water_tanks.Drain;
import classes.entities.water_tanks.Source;
import classes.entities.water_tanks.WaterTank;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    private HashMap<String, ArrayList<Direction>> _parseDict; 
    public Field loadFromFile(String filename) throws IOException {
        /*
        file example:
        "<<height>> <<width>>
        <<cell1>> <<cell2>> ... <<celln>>
        <<cell1>> <<cell2>> ... <<celln>>
        ...
        <<cell1>> <<cell2>> ... <<celln>>

        "

        cell example:
        if can be empty: "□" or with pipe
        pipes:
        UP DOWN	LEFT RIGHT char
        0	0	0	0	○
        0	0	0	1	╴
        0	0	1	0	╴
        0	0	1	1	═
        0	1	0	0	╷
        0	1	0	1	╔
        0	1	1	0	╗
        0	1	1	1	╦
        1	0	0	0	╵
        1	0	0	1	╚
        1	0	1	0	╝
        1	0	1	1	╩
        1	1	0	0	║
        1	1	0	1	╠
        1	1	1	0	╣
        1	1	1	1	╬

        source: "s"
        drain: "d"
         */
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
                    newCell.setEntity(newSource);
                    field.setCell(newCell);
                } else if (cell.equals(drain.toString())) {
                    Drain newDrain = new Drain();
                    newCell.setEntity(newDrain);
                    field.setCell(newCell);
                } else  if (_parseDict.containsKey(cell)){
                    ArrayList<Direction> possibleDirections = _parseDict.get(cell);
                    WaterTank watertank = new WaterTank();
                    watertank.addPossibleDirections(possibleDirections);
                    newCell.setEntity(watertank);
                    field.setCell(newCell);
                } else {
                    //log in console
                    System.out.println("Invalid cell type: " + cell + "at position " + i + " " + j);
                }   
            }
        }
        reader.close();
        return field;
    }

    public void saveToFile(Field field, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(field.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save field to file", e);
        }
    }
}