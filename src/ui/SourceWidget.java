package ui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import classes.entities.water_tanks.Source;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;

public class SourceWidget  extends CellItemWidget {
    private static final String SOURCE = "source.png";
    Source _source;

    public SourceWidget(Source source) {
        super();
        _source = source;
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(new SourceWidgetMouseListener());
    }

    @Override
    protected BufferedImage getImage() {
        return ImageLoader.loadImage(SOURCE, WIDTH, HEIGHT);
    }

    private void printSourceInfo() {
       
        int x = (int) getCellWidget().getCell().getCoords().getX();
        int y = (int) getCellWidget().getCell().getCoords().getY();

        String isWaterIn = _source.getWater() != null ? "true" : "false";
        String endsInfo = "";
        for (AbstractWaterTankEnd end : _source.getEnds()) {  
           endsInfo += end.toString() + "; ";
        }

        System.out.println("{x: " + x + "; y: " + y + "; isWater: " + isWaterIn + "; " + endsInfo + "}");
    }

    public class SourceWidgetMouseListener extends MouseAdapter {

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            printSourceInfo();
        }
    }
}
