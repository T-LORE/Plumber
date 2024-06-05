package ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import classes.Player;
import ui.events.FieldWidgetEvent;
import ui.events.FieldWidgetListener;
import ui.events.TexturedButtonEvent;
import ui.events.TexturedButtonListener;

public class PlayerActionWidget extends JPanel {
    // size constants
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 110;

    private Player _player;
    private FieldWidget _fieldWidget;
    private String _currentLevelPath;
    private GamePanel _gamePanel;

    private TexturedButton _restartButton;
    private TexturedButton _startFlowButton;
    private TexturedButton _loadLevelButton;
    private TexturedButton _helpButton;

    public PlayerActionWidget(GamePanel panel, WidgetFactory factory, Player player) {
        setOpaque(false);

        _player = player;
        _gamePanel = panel;
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0)); 
        //добавить границу с толщиной
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // конопки располагаются горизонтально
        setLayout(new FlowLayout(java.awt.FlowLayout.CENTER));
        // добавляем кнопку "Перезапустить"
        TexturedButton restartButton = factory.createRestartButton();
        restartButton.addListener(new RestartButtonListener());
        _restartButton = restartButton;
        // добавляем кнопку "Открыть кран"
        TexturedButton startFlowButton = factory.createStartWaterflowButton();
        startFlowButton.addListener(new StartFlowButtonListener());
        _startFlowButton = startFlowButton;
        // добавляем кнопку "Загрузить уровень"
        TexturedButton loadLevelButton = factory.createLoadLevelButton();
        loadLevelButton.addListener(new LoadLevelButtonListener());
        _loadLevelButton = loadLevelButton;
        add(_loadLevelButton);

        // добавляем кнопку "Справка"
        TexturedButton helpButton = factory.createHelpButton();
        helpButton.addListener(new HelpButtonAtionListener());
        _helpButton = helpButton;
        add(_helpButton);
    }

    private void startLevel(String levelPath) {
        _currentLevelPath = levelPath;
        _player.startLevel(levelPath);
        _fieldWidget = _gamePanel.repaintField();
        _fieldWidget.addListener(new FieldWidgetActionListener());
        removeAll();
        add(_restartButton);
        add(_startFlowButton);
        add(_loadLevelButton);
        add(_helpButton);
    }

    private static final String TEXTURE = "paper.png";
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.loadImage(TEXTURE, 1920, 600), 0, 0, null);
    }

    private class StartFlowButtonListener implements TexturedButtonListener {

        @Override
        public void buttonPressed(TexturedButtonEvent e) {
            
            System.out.println("Start water flow");
            _player.startWaterFlow();
        }

    }

    private class RestartButtonListener implements TexturedButtonListener {

        @Override
        public void buttonPressed(TexturedButtonEvent e) {
            System.out.println("Restart");
            startLevel(_currentLevelPath);
        }

    }

    private class LoadLevelButtonListener implements TexturedButtonListener {

        @Override
        public void buttonPressed(TexturedButtonEvent e) {
            System.out.println("Load level");
            JFileChooser fileChooser = new JFileChooser("levels");
            int result = fileChooser.showOpenDialog(PlayerActionWidget.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                startLevel(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }

    }

    private class FieldWidgetActionListener implements FieldWidgetListener {

        @Override
        public void rotatePipe(FieldWidgetEvent event) {
            _player.rotateClockwise(event.coords);
        }

    }

    private class HelpButtonAtionListener implements TexturedButtonListener {

        //Открыть картинку по пути /Sprites/Help.png
        @Override
        public void buttonPressed(TexturedButtonEvent e) {
            System.out.println("Help");
            File f = new File("Sprites/help.png");
            Desktop dt = Desktop.getDesktop();
            try {
                dt.open(f);
            } catch (Exception ex) {
                System.out.println("Cant open help.");
            }
            

        }

    }
}
