package ui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import classes.Direction;
import classes.Water;
import classes.entities.water_tanks.AbstractWaterTank;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.events.RotatableWaterTankActionEvent;
import classes.events.RotatableWaterTankActionListener;
import classes.events.WaterTankActionEvent;
import classes.events.WaterTankActionListener;
import ui.events.PipeWidgetEvent;
import ui.events.PipeWidgetListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public abstract class AbstractWaterTankWidget extends CellItemWidget{
    protected BufferedImage _image;
    private static final int END_SHIFT_OFFSET = CellWidget.getCellSize() / 64 * 21;
    AbstractWaterTank _waterTank;

    public AbstractWaterTankWidget(AbstractWaterTank watertank) {
        super();
        _waterTank = watertank;
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(new TankWidgetClickListener());
        _waterTank.addListener(new AbstractWaterTankListener());
    }

    protected abstract BufferedImage getTankTextureBase();

    // Default ends is watching up
    protected abstract BufferedImage getTankTextureEnd(Direction direction);

    private BufferedImage addEndPicture(BufferedImage image, Direction direction) {
        return mergePictures(image, getTankTextureEnd(direction));
    }

    @Override
    protected BufferedImage getImage() {
        _image = getTankTextureBase();
        for (AbstractWaterTankEnd end : _waterTank.getEnds()) {
            _image = addEndPicture(_image, end.getDirection());
        }

        return _image;
    }

    private BufferedImage mergePictures(BufferedImage picA, BufferedImage picB){
        BufferedImage merged = new BufferedImage(picA.getWidth(), picA.getHeight(), picA.getType());
        merged.getGraphics().drawImage(picA, 0, 0, null);
        merged.getGraphics().drawImage(picB, 0, 0, null);
        return merged;
    }

    public class TankWidgetClickListener extends MouseAdapter {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            printTankInfo();
        }
    }

    public AbstractWaterTank getWaterTank() {
        return _waterTank;
    }

    private void printTankInfo() {
       
        int x = (int) getCellWidget().getCell().getCoords().getX();
        int y = (int) getCellWidget().getCell().getCoords().getY();

        String isWaterIn = _waterTank.getWater() != null ? "true" : "false";
        String diameter = "diameter: " + _waterTank.getDiameter();
        String endsInfo = "";
        
        for (AbstractWaterTankEnd end : _waterTank.getEnds()) {  
           endsInfo += end.toString() + "; ";
        }

        System.out.println("{x: " + x + "; y: " + y + "; isWater: " + isWaterIn + "; " + diameter +"; " + endsInfo + "}");
    }

    private class AbstractWaterTankListener implements WaterTankActionListener {

        @Override
        public void tankFilled(WaterTankActionEvent e) {
            repaint();
        }
    }

    
}
