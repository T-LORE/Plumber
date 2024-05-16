package ui;

import classes.Game;
import classes.events.GameActionEvent;
import classes.events.GameActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JFrame {

    private Game _game;
    private WidgetFactory _widgetFactory;
    private JPanel _content;
    private PlayerActionWidget _playerActionWidget;
    private FieldWidget _fieldWidget;

    public GamePanel() throws HeadlessException {
        setVisible(true);

        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createGameMenu());
        setJMenuBar(menuBar);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        _widgetFactory = new WidgetFactory();

        _game = new Game();
        _game.addListener(new GameObserver());
        _content = (JPanel) getContentPane();

        _playerActionWidget = _widgetFactory.createPlayerActionWidget(this, _game.getPlayer());
        _content.setLayout(new BorderLayout());
        _content.add(_playerActionWidget, BorderLayout.SOUTH);
        pack();
    }

    private JMenu createGameMenu() {
        JMenu gameMenu = new JMenu("Игра");
        JMenuItem exitMenuItem = new JMenuItem(new ExitAction());
        gameMenu.add(exitMenuItem);
        return gameMenu;
    }

    public FieldWidget repaintField() {

        _content.removeAll();
        _fieldWidget = _widgetFactory.createFieldWidget(_game.getGameField());
        _content.add(_fieldWidget, BorderLayout.NORTH);
        _content.add(_playerActionWidget);

        pack();
        return _fieldWidget;
    }

    private void winPanel() {
        JOptionPane.showMessageDialog(this, "Поздравляем! Вы выиграли!", "Победа", JOptionPane.INFORMATION_MESSAGE);
    }

    private void losePanel() {
        JOptionPane.showMessageDialog(this, "К сожалению, вы проиграли. Попробуйте еще раз!", "Поражение", JOptionPane.ERROR_MESSAGE);
    }


    private static class ExitAction extends AbstractAction {

        public ExitAction() {
            putValue(NAME, "Выход");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class GameObserver implements GameActionListener {

        @Override
        public void winEvent(GameActionEvent event) {
            System.out.println("WIN! :>");
            winPanel();
        }

        @Override
        public void loseEvent(GameActionEvent event) {
            System.out.println("LOSE! D:");
            losePanel();
        }

        @Override
        public void waterTick(GameActionEvent event) {
            System.out.println("Water tick");
        }
    }

}