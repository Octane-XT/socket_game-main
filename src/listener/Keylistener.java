package listener;

import player.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keylistener implements KeyListener {
    Player p;
    int[][] map;

    public Keylistener(Player x, int[][] map) {
        p = x;
        this.map = map;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (map[p.getX()][p.getY() - 1] == 0) {
                    p.Ymoov(-1);
                } else {
                    p.Ymoov(0);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (map[p.getX()][p.getY() + 1] == 0) {
                    p.Ymoov(1);
                } else {
                    p.Ymoov(0);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (map[p.getX() - 1][p.getY()] == 0) {
                    p.Xmoov(-1);
                } else {
                    p.Ymoov(0);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (map[p.getX() + 1][p.getY()] == 0) {
                    p.Xmoov(1);
                } else {
                    p.Ymoov(0);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
