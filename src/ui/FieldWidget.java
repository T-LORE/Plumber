package ui;

import classes.Cell;
import classes.Field;
import ui.events.FieldWidgetEvent;
import ui.events.FieldWidgetListener;
import ui.events.PipeWidgetEvent;
import ui.events.PipeWidgetListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FieldWidget extends JPanel {
    private final Field _field;
    private final WidgetFactory _widgetFactory;
    
    private ArrayList<FieldWidgetListener> _listeners = new ArrayList<>();
    private ArrayList<CellWidget> _cellWidgets = new ArrayList<>();

    public FieldWidget(Field field, WidgetFactory widgetFactory) {
        _field = field;
        _widgetFactory = widgetFactory;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fillField();
        setBackground(Color.decode("#D3D3D3"));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void fillField() { 
        // Заполнить поле виджетами клеток
        for (int i = 0; i < _field.getHeight(); i++) {
            JPanel newRow = createRow(i);
            add(newRow);
        }

        // Подключить слушателей к трубам
        setUpPipeListeners();
    }

    private void setUpPipeListeners() {
        for (CellWidget cellWidget : _cellWidgets) {
            if (cellWidget.getItem() instanceof PipeWidget) {
                ((PipeWidget) cellWidget.getItem()).addListener(new PipeListener());
            }
        }
    }

    private JPanel createRow(int rowIndex) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        for (int i = 0; i < _field.getWidth(); i++) {
            row.add(createCellWidget(_field.getCell(new Point(i, rowIndex))));
        }

        return row;
    }

    private CellWidget createCellWidget(Cell cell) {
        CellWidget newCellWidget = _widgetFactory.createCellWidget(cell);
        _cellWidgets.add(newCellWidget);
            
        return newCellWidget;
    }

    private static final String TEXTURE = "white-paper-texture.png";
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.loadImage(TEXTURE, 1920, 1000), 0, 0, null);
    }

    private void fireRotatePipeEvent(Point coords) {
        FieldWidgetEvent fieldEvent = new FieldWidgetEvent(this);
        fieldEvent.coords = coords;
        for (FieldWidgetListener listener : _listeners) {
            listener.rotatePipe(fieldEvent);
        }
    }

    public void addListener(FieldWidgetListener listener) {
        _listeners.add(listener);
    }

    private class PipeListener implements PipeWidgetListener{

        @Override
        public void rotateClockwise(PipeWidgetEvent event) {
            System.out.println("Pipe rotated");
            fireRotatePipeEvent(event.coords);
        }
    }

   
}
