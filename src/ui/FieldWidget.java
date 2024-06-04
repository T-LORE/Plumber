package ui;

import classes.Cell;
import classes.Field;
import ui.events.FieldWidgetEvent;
import ui.events.FieldWidgetListener;
import ui.events.PipeWidgetEvent;
import ui.events.PipeWidgetListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.ArrayList;

public class FieldWidget extends JPanel {
    private final Field _field;
    private final WidgetFactory _widgetFactory;

    private static final int PADDING = 20; // Отступы
    
    private ArrayList<FieldWidgetListener> _listeners = new ArrayList<>();
    private ArrayList<CellWidget> _cellWidgets = new ArrayList<>();

    public FieldWidget(Field field, WidgetFactory widgetFactory) {
        _field = field;
        _widgetFactory = widgetFactory;
        setLayout(new GridBagLayout());
        
        add(createCellGrid(), new GridBagConstraints());

        // Подключить слушателей к трубам
        setUpPipeListeners();

        setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
    }

    private JPanel createCellGrid() { 
        // Заполнить поле виджетами клеток
        JPanel gridPanel = new JPanel(new GridLayout(_field.getHeight(), _field.getWidth()));
        gridPanel.setOpaque(false);
        for (int i = 0; i < _field.getHeight(); i++) {
            for (int j = 0; j < _field.getWidth(); j++) {
                Cell cell = _field.getCell(new Point(j, i));
                CellWidget cellWidget = createCellWidget(cell);
                gridPanel.add(cellWidget);
            }
        }
        return gridPanel;
    }

    private CellWidget createCellWidget(Cell cell) {
        CellWidget newCellWidget = _widgetFactory.createCellWidget(cell);
        _cellWidgets.add(newCellWidget);        
        return newCellWidget;
    }

    private void setUpPipeListeners() {
        for (CellWidget cellWidget : _cellWidgets) {
            if (cellWidget.getItem() instanceof AbstractRotatableWaterTankWidget) {
                ((AbstractRotatableWaterTankWidget) cellWidget.getItem()).addListener(new PipeListener());
            }
        }
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
