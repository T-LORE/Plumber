package ui;

import javax.swing.*;

import classes.Cell;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CellWidget extends JPanel {
    private static final int  CELL_SIZE = 100;

    private Cell _cell;

    private static final Color BACKGROUND_COLOR = Color.decode("#888888");

    public CellWidget(Cell cell) {
        _cell = cell;
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        setBackground(BACKGROUND_COLOR); 
        setOpaque(false); 
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private CellItemWidget _item;

    public void addItem(CellItemWidget item) {
        if (_item != null) {
            throw new IllegalStateException("Cell already has an item");
        }
        add(item);
        _item = item;
        _item.setCellWidget(this);
        
    }

    public CellItemWidget getItem() {
        return _item;
    }

    public Cell getCell() {
        return _cell;
    }

    public void setCell(Cell cell) {
        _cell = cell;
    }

    public void removeItem(CellItemWidget item) {
        remove(item);
    }
}
