package ui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import classes.entities.water_tanks.Drain;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;

public class DrainWidget extends CellItemWidget {
    private static final String DRAIN = "drain.png";
    private Drain _drain;

    public DrainWidget(Drain drain) {
        super();
        _drain = drain;
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(new DrainWidgetMouseListener());
    }

    @Override
    protected BufferedImage getImage() {
        return ImageLoader.loadImage(DRAIN, WIDTH, HEIGHT);
    }

    private void printDrainInfo() {
       
        int x = (int) getCellWidget().getCell().getCoords().getX();
        int y = (int) getCellWidget().getCell().getCoords().getY();

        String isWaterIn = _drain.getWater() != null ? "true" : "false";
        String endsInfo = "";
        for (AbstractWaterTankEnd end : _drain.getEnds()) {  
           endsInfo += end.toString() + "; ";
        }

        System.out.println("{x: " + x + "; y: " + y + "; isWater: " + isWaterIn + "; " + endsInfo + "}");
    }

    public class DrainWidgetMouseListener extends MouseAdapter {

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            printDrainInfo();
        }
    }
}
