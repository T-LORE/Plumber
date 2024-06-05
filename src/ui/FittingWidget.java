package ui;

import java.awt.image.BufferedImage;

import classes.Direction;
import classes.entities.water_tanks.AbstractWaterTank;
import classes.entities.water_tanks.Fitting;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.water_tanks_ends.MaterialWaterTankEnd;

public class FittingWidget extends AbstractRotatableWaterTankWidget {

    private static final int END_SHIFT_OFFSET = 42;

    public FittingWidget(Fitting fitting) {
        super(fitting);
    }

    private static final String path = "water_tanks/";

    @Override
    protected BufferedImage getTankTextureBase() {
        String filled = getRotatableWaterTank().getWater() != null ? "filled" : "empty";
        String filepath = path + "fiting/" + "base_" + filled + ".png";
        return ImageLoader.loadImage(filepath, CellWidget.getCellSize(), CellWidget.getCellSize());
    }

    @Override
    protected BufferedImage getTankTextureEnd(Direction direction) {
        String material = ((MaterialWaterTankEnd)getWaterTank().getEnd(direction)).getMaterial().toString().toLowerCase();
        String diameter = ((MaterialWaterTankEnd)getWaterTank().getEnd(direction)).getDiameter().toString().toLowerCase();
        AbstractWaterTank wt = getWaterTank();
        String filepath = path + "pipe/" + material + "/" + "end_" + diameter + ".png";
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
