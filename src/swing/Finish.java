package swing;

import javax.swing.*;
import java.awt.*;

public class Finish extends JFrame {
    int width, height;
    JPanel pan;
    JLabel l;

    public Finish() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        this.setTitle("TEST ");
        this.setPreferredSize(new Dimension(200, 100));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pan = new JPanel();
        l = new JLabel("Partie terminer");
        pan.add(l);
        this.add(pan);

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Finish UI = new Finish();
    }


}

