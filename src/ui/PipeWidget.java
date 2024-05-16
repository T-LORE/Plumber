package ui;

import classes.Direction;
import classes.Water;
import classes.entities.water_tanks.Pipe;
import classes.events.PipeActionEvent;
import classes.events.PipeActionListener;
import classes.events.WaterTankActionEvent;
import ui.events.PipeWidgetEvent;
import ui.events.PipeWidgetListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class PipeWidget extends CellItemWidget {

    Pipe _pipe;

    private ArrayList<PipeWidgetListener> _listeners = new ArrayList<>();

    public PipeWidget(Pipe pipe) {
        super();
        _pipe = pipe;
        _pipe.addListener(new PipeListener());
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //setBackground(Color.decode("#800080"));
        addMouseListener(new PipeWidgetClickListener());
    }

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

    @Override
    protected BufferedImage getImage() {
        ArrayList<Direction> possibleDirections = _pipe.getPossibleDirections();
        Water water = _pipe.getWater();
        BufferedImage image = null;
        if (water != null) {
            image = ImageLoader.loadImage(BASE_FILLED, WIDTH, HEIGHT);
            if (possibleDirections.contains(Direction.UP)) {
                image = addPicture(image, UP_FILLED);
            }
            if (possibleDirections.contains(Direction.DOWN)) {
                image = addPicture(image, DOWN_FILLED);
            }
            if (possibleDirections.contains(Direction.LEFT)) {
                image = addPicture(image, LEFT_FILLED);
            }
            if (possibleDirections.contains(Direction.RIGHT)) {
                image = addPicture(image, RIGHT_FILLED);
            }
            
        } else {
            image = ImageLoader.loadImage(BASE_EMPTY, WIDTH, HEIGHT);
            if (possibleDirections.contains(Direction.UP)) {
                image = addPicture(image, UP_EMPTY);
            }
            if (possibleDirections.contains(Direction.DOWN)) {
                image = addPicture(image, DOWN_EMPTY);
            }
            if (possibleDirections.contains(Direction.LEFT)) {
                image = addPicture(image, LEFT_EMPTY);
            }
            if (possibleDirections.contains(Direction.RIGHT)) {
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



    // PipeWidgetListener
    public class PipeWidgetClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            fireRotateEvent();
            repaint();
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
        }    
    }

    //pipe action listener
    private class PipeListener implements PipeActionListener {

        @Override
        public void pipeRotated(PipeActionEvent e) {
            repaint();
        }

        @Override
        public void tankFilled(WaterTankActionEvent e) {
            repaint();
        }
    }
    
}
