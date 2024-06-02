package ui;

import classes.Direction;
import classes.Water;
import classes.entities.water_tanks.Pipe;
import classes.entities.water_tanks.water_tanks_ends.AbstractWaterTankEnd;
import classes.events.RotatableWaterTankActionEvent;
import classes.events.RotatableWaterTankActionListener;
import classes.events.WaterTankActionEvent;
import ui.events.PipeWidgetEvent;
import ui.events.PipeWidgetListener;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;


public class PipeWidget extends CellItemWidget {
    //constant path pipe
    private static final String BASE_EMPTY = "pipes/pipe_base_empty.png";
    private static final String BASE_FILLED = "pipes/pipe_base_filled.png";
    private static final String UP_EMPTY = "pipes/pipe_up_empty.png";
    private static final String UP_FILLED = "pipes/pipe_up_filled.png";
    private static final String DOWN_EMPTY = "pipes/pipe_down_empty.png";
    private static final String DOWN_FILLED = "pipes/pipe_down_filled.png";
    private static final String LEFT_EMPTY = "pipes/pipe_left_empty.png";
    private static final String LEFT_FILLED = "pipes/pipe_left_filled.png";
    private static final String RIGHT_EMPTY = "pipes/pipe_right_empty.png";
    private static final String RIGHT_FILLED = "pipes/pipe_right_filled.png";

    Pipe _pipe;

    private ArrayList<PipeWidgetListener> _listeners = new ArrayList<>();

    public PipeWidget(Pipe pipe) {
        super();
        _pipe = pipe;
        _pipe.addListener(new PipeListener());
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(new PipeWidgetClickListener());
    }

    @Override
    protected BufferedImage getImage() {
        ArrayList<Direction> endsDirection = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            if (_pipe.getEnd(direction) != null) {
                endsDirection.add(direction);
            }
        }

        Water water = _pipe.getWater();
        BufferedImage image = null;
        if (water != null) {
            image = ImageLoader.loadImage(BASE_FILLED, WIDTH, HEIGHT);
            if (endsDirection.contains(Direction.UP)) {
                image = addPicture(image, UP_FILLED);
            }
            if (endsDirection.contains(Direction.DOWN)) {
                image = addPicture(image, DOWN_FILLED);
            }
            if (endsDirection.contains(Direction.LEFT)) {
                image = addPicture(image, LEFT_FILLED);
            }
            if (endsDirection.contains(Direction.RIGHT)) {
                image = addPicture(image, RIGHT_FILLED);
            }
            
        } else {
            image = ImageLoader.loadImage(BASE_EMPTY, WIDTH, HEIGHT);
            if (endsDirection.contains(Direction.UP)) {
                image = addPicture(image, UP_EMPTY);
            }
            if (endsDirection.contains(Direction.DOWN)) {
                image = addPicture(image, DOWN_EMPTY);
            }
            if (endsDirection.contains(Direction.LEFT)) {
                image = addPicture(image, LEFT_EMPTY);
            }
            if (endsDirection.contains(Direction.RIGHT)) {
                image = addPicture(image, RIGHT_EMPTY);
            }
        }

        return image;
    }

    private BufferedImage addPicture(BufferedImage image, String path) {
        BufferedImage picture = ImageLoader.loadImage(path, WIDTH, HEIGHT);
        image.getGraphics().drawImage(picture, 0, 0, null);
        return image;
    }

    private void fireRotateEvent() {
        PipeWidgetEvent event = new PipeWidgetEvent(this);
        event.coords =  getCellWidget().getCell().getCoords();
        for (PipeWidgetListener listener : _listeners) {
            listener.rotateClockwise(event);
        }
    }

    public void addListener(PipeWidgetListener listener) {
        _listeners.add(listener);
    }

    public class PipeWidgetClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            fireRotateEvent();
            repaint();
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            printPipeInfo();
        }
    }

    private void printPipeInfo() {
       
        int x = (int) getCellWidget().getCell().getCoords().getX();
        int y = (int) getCellWidget().getCell().getCoords().getY();

        String isWaterIn = _pipe.getWater() != null ? "true" : "false";
        String endsInfo = "";
        for (AbstractWaterTankEnd end : _pipe.getEnds()) {  
           endsInfo += end.toString() + "; ";
        }

        System.out.println("{x: " + x + "; y: " + y + "; isWater: " + isWaterIn + "; " + endsInfo + "}");
    }

    private class PipeListener implements RotatableWaterTankActionListener {

        @Override
        public void pipeRotated(RotatableWaterTankActionEvent e) {
            repaint();
        }

        @Override
        public void tankFilled(WaterTankActionEvent e) {
            repaint();
        }
    }
    
}
