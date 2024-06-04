package ui;

import static ui.AbstractWaterTankWidget.END_SHIFT_OFFSET;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import classes.Direction;
import classes.entities.water_tanks.AbstractWaterTank;
import classes.entities.water_tanks.Drain;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.entities.water_tanks.water_tanks_ends.MaterialWaterTankEnd;

public class DrainWidget extends AbstractWaterTankWidget {

    private static final String path = "water_tanks/drain/";
    private static final int END_SHIFT_OFFSET = CellWidget.getCellSize() / 64 * 21;
    
    public DrainWidget(AbstractWaterTank watertank) {
        super(watertank);
    }

    @Override
    protected BufferedImage getTankTextureBase() {
        String material = getWaterTank().getMaterial().toString().toLowerCase();
        String filepath = path + material + "/" + "base.png";
        return ImageLoader.loadImage(filepath, CellWidget.getCellSize(), CellWidget.getCellSize());
    }

    @Override
    protected BufferedImage getTankTextureEnd(Direction direction) {
        String material = ((MaterialWaterTankEnd)getWaterTank().getEnd(direction)).getMaterial().toString().toLowerCase();
        String diameter = getWaterTank().getDiameter().toString().toLowerCase();
        String filepath = path + material + "/" + "end_" + diameter + ".png";
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
