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

    private static final int END_SHIFT_OFFSET = 20;

    public PipeWidget(Pipe pipe) {
        super(pipe);
    }

    private static final String path = "water_tanks/pipe/";

    @Override
    protected BufferedImage getTankTextureBase() {
        String material = getWaterTank().getMaterial().toString().toLowerCase();
        String filled = getRotatableWaterTank().getWater() != null ? "filled" : "empty";
        String filepath = path + material + "/" + "base_" + filled + ".png";
        return ImageLoader.loadImage(filepath, 64, 64);
    }

    @Override
    protected BufferedImage getTankTextureEnd(Direction direction) {
        String filled = getRotatableWaterTank().getWater() != null ? "filled" : "empty";
        String material = ((MaterialWaterTankEnd)getWaterTank().getEnd(direction)).getMaterial().toString().toLowerCase();
        String diameter = getWaterTank().getDiameter().toString().toLowerCase();
        String filepath = path + material + "/" + "end_" + diameter + "_" + filled + ".png";
        System.out.println(filepath);
        BufferedImage img = ImageLoader.loadImage(filepath, 64, 64);
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
