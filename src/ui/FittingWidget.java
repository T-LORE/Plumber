package ui;

import java.awt.image.BufferedImage;

import classes.Direction;
import classes.entities.water_tanks.AbstractWaterTank;
import classes.entities.water_tanks.Fitting;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.water_tanks_ends.MaterialWaterTankEnd;

public class FittingWidget extends AbstractRotatableWaterTankWidget {

    private static final int END_SHIFT_OFFSET = CellWidget.getCellSize() / 64 * 21;

    public FittingWidget(Fitting fitting) {
        super(fitting);
    }

    private static final String path = "water_tanks/pipe/";

    @Override
    protected BufferedImage getTankTextureBase() {
        String filled = getRotatableWaterTank().getWater() != null ? "filled" : "empty";
        String filepath = path + "universal/" + "base_" + filled + ".png";
        return ImageLoader.loadImage(filepath, CellWidget.getCellSize(), CellWidget.getCellSize());
    }

    @Override
    protected BufferedImage getTankTextureEnd(Direction direction) {
        String filled = getRotatableWaterTank().getWater() != null ? "filled" : "empty";
        //TODO: неправильно блять
        String material = ((MaterialWaterTankEnd)getWaterTank().getEnd(direction)).getMaterial().toString().toLowerCase();
        String diameter = getWaterTank().getDiameter().toString().toLowerCase();
        String filepath = path + material + "/" + "end_" + diameter + "_" + filled + ".png";
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
