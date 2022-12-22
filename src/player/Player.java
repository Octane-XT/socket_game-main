package player;

import java.awt.*;
import java.io.Serializable;

public class Player implements Serializable {
    int x, y;
    int p = 2;
    Color color;

    public Player() {
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void Xmoov(int values) {
        x += values;
    }

    public void Ymoov(int values) {
        y += values;
    }

}
