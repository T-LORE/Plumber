package ui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class SourceWidget  extends CellItemWidget {
    private static final String SOURCE = "source.png";

    
    public SourceWidget() {
        super();
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    protected BufferedImage getImage() {
        return ImageLoader.loadImage(SOURCE, WIDTH, HEIGHT);
    }
}
