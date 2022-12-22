package map;

import player.Player;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Field implements Serializable {
    int x;
    int y;
    int[][] map;
    int wall;

    Player[] lp;
    Goal goal;

    boolean check;

    public Field() {
        this.wall = 1;
    }

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.wall = 1;
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

    public Player[] getLp() {
        return lp;
    }

    public void setLp(Player[] lp) {
        this.lp = lp;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int[][] fill_map() {
        map = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                map[i][j] = 0;
            }
        }
        return map;
    }

    public void set_wall(int x, int y) {
        if (this.map[x][y] == 0) {
            this.map[x][y] = 1;
        }
    }

    public void generate_wall() {
        int i = 0;
        while (i < (x * y) / 5) {
            Random rand = new Random();
            int a = rand.nextInt(x);
            int b = rand.nextInt(y);
            set_wall(a, b);
            i++;
        }
    }

    public void generate_player(int nb) {
        lp = new Player[nb];
        for (int i = 0; i < nb; i++) {
            Random rand = new Random();
            int u = rand.nextInt(x);
            int v = rand.nextInt(y);
            if (map[u][v] == wall) {
                u = rand.nextInt(x);
                v = rand.nextInt(y);
            }
            lp[i] = new Player(u, v);
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            lp[i].setColor(new Color(r, g, b));
        }
    }

    public void goal() {
        Random rand = new Random();
        int a = rand.nextInt(x);
        int b = rand.nextInt(y);
        if (map[a][b] != 0) {
            a = rand.nextInt(x);
            b = rand.nextInt(y);
        }
        goal = new Goal(a,b);
    }
    public void check(int i){
            if(lp[i].getX()==goal.getX() && lp[i].getY()== goal.getY()){
                check = true;
            }else{
                check = false;
            }
    }
}
