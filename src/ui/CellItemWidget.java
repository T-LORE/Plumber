package ui;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

public abstract class CellItemWidget extends JPanel {
    protected static final int WIDTH = 100;
    protected static final int HEIGHT = 100;

    protected CellWidget _cellWidget;

    protected void setCellWidget(CellWidget cell) {
        _cellWidget = cell;
    }

    protected CellWidget getCellWidget() {
        return _cellWidget;
    }

    public CellItemWidget() {
        setOpaque(false);
    }

    protected abstract BufferedImage getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }
}
