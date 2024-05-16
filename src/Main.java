import classes.*;
import classes.events.*;
import ui.FieldWidget;
import ui.WidgetFactory;
import ui.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;



public class Main {
    Field _field;
    static Game _game;
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(GamePanel::new);
    }



}