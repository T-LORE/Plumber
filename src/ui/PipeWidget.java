package ui;

import classes.Direction;
import classes.Water;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.entities.water_tanks.water_tanks_ends.MaterialWaterTankEnd;
import classes.events.RotatableWaterTankActionEvent;
import classes.events.RotatableWaterTankActionListener;
import classes.events.WaterTankActionEvent;
import ui.events.PipeWidgetEvent;
import ui.events.PipeWidgetListener;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;


public class PipeWidget extends AbstractRotatableWaterTankWidget {

    private static final int END_SHIFT_OFFSET = 42;

    public PipeWidget(Pipe pipe) {
        super(pipe);
    }

    private static final String path = "water_tanks/pipe/";

    @Override
    protected BufferedImage getTankTextureBase() {
        String filled = getRotatableWaterTank().getWater() != null ? "filled" : "empty";
        String filepath = path + "base_" + filled + ".png";
        return ImageLoader.loadImage(filepath, CellWidget.getCellSize(), CellWidget.getCellSize());
    }

    @Override
    protected BufferedImage getTankTextureEnd(Direction direction) {
        String material = ((MaterialWaterTankEnd)getWaterTank().getEnd(direction)).getMaterial().toString().toLowerCase();
        String diameter = ((MaterialWaterTankEnd)getWaterTank().getEnd(direction)).getDiameter().toString().toLowerCase();
        String filepath = path + material + "/" + "end_" + diameter + ".png";
        System.out.println(filepath);
        BufferedImage img = ImageLoader.loadImage(filepath, CellWidget.getCellSize(), CellWidget.getCellSize());
        return shiftEndPicture(img, direction);
    }
    
    private BufferedImage shiftEndPicture(BufferedImage image, Direction direction) {
        int x = 0;
        int y = 0;
        switch (direction) {
            case UP:
                y = -END_SHIFT_OFFSET;
                break;
            case RIGHT:
                x = END_SHIFT_OFFSET;
                image = ImageLoader.rotateClockwisePicture(image, 1);
                break;
            case DOWN:
                y = END_SHIFT_OFFSET;
                image = ImageLoader.rotateClockwisePicture(image, 2);
                break;
            case LEFT:
                x = -END_SHIFT_OFFSET;
                image = ImageLoader.rotateClockwisePicture(image, 3);
                break;
        }
        return ImageLoader.shiftPicture(image, x, y);
    }
}
