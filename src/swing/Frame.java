package swing;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    int width, height;
    Panel pan;

    public Frame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        this.setTitle("TEST ");
        this.setPreferredSize(new Dimension(600, 650));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pan = new Panel();
        this.add(pan);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Frame UI = new Frame();
    }


}
