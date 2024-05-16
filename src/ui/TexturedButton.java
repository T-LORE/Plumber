package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.JButton;

import ui.events.TexturedButtonEvent;
import ui.events.TexturedButtonListener;

public class TexturedButton extends JButton {
    // size constants
    private final int WIDTH;
    private final int HEIGHT;

    ArrayList<TexturedButtonListener> _listeners = new ArrayList<TexturedButtonListener>();

    public TexturedButton(String texture, int width, int height) {
        super("Textured button");
        TEXTURE = texture;
        WIDTH = width;
        HEIGHT = height;
        _textureWidth = WIDTH - 40;
        _textureHeight = HEIGHT - 20;
        addMouseListener(new ButtonListener());
        setFocusable(false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    private void firePressEvent() {
        for (TexturedButtonListener listener : _listeners) {
            listener.buttonPressed(new TexturedButtonEvent(this));
        }
    }

    public void addListener(TexturedButtonListener listener) {
        _listeners.add(listener);
    }

    private class ButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            firePressEvent();
        }
        
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            _textureHeight = HEIGHT;
            _textureWidth = WIDTH;
            _textureX = 0;
            _textureY = 0;
            revalidate();
            repaint();
        }
        
        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            _textureHeight = HEIGHT -20;
            _textureWidth = WIDTH - 40;
            _textureX = 20;
            _textureY = 10;
            revalidate();
            repaint();
        }
    }

    private int _textureWidth;
    private int _textureHeight;
    private int _textureX = 20;
    private int _textureY = 10;

    private final String TEXTURE;
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLoader.loadImage(TEXTURE, _textureWidth, _textureHeight), _textureX, _textureY, null);
    }

}
