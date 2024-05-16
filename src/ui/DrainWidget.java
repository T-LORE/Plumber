package ui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class DrainWidget extends CellItemWidget {
    private static final String DRAIN = "drain.png";

    public DrainWidget() {
        super();
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    protected BufferedImage getImage() {
        return ImageLoader.loadImage(DRAIN, WIDTH, HEIGHT);
    }
}
