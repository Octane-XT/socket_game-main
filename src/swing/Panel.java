package swing;

import listener.Keylistener;
import map.Field;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Panel extends JPanel {
    Field field;

    int pixel;
    Socket socket;
    int playerID;
    GetFromServer getS;
    SendtoServer sendS;

    public Panel() {
        field = new Field();
        pixel = 40;
        connect();
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new Keylistener(field.getLp()[playerID - 1], field.getMap()));

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw_player(g);
        draw_field(g);
        draw_goal(g);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.repaint();
    }

    public void draw_player(Graphics g) {
        for (int i = 0; i < field.getLp().length; i++) {
            g.setColor(field.getLp()[i].getColor());
            g.drawOval(field.getLp()[i].getX() * pixel, field.getLp()[i].getY() * pixel, pixel, pixel);
        }
    }

    public void draw_field(Graphics g) {
        for (int i = 0; i < field.getMap().length; i++) {
            for (int j = 0; j < field.getMap()[0].length; j++) {
                if (field.getMap()[i][j] == 1) {
                    g.setColor(Color.WHITE);
                    g.drawRect(i * pixel, j * pixel, pixel, pixel);
                }
            }
        }

    }

    public void draw_goal(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(field.getGoal().getX() * pixel, field.getGoal().getY() * pixel, pixel, pixel);
    }

    public void connect() {
        try {
            socket = new Socket("localhost", 9999);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream obj_in = new ObjectInputStream(socket.getInputStream());
            playerID = in.readInt();
            this.field = ((Field) obj_in.readObject());
            System.out.println("You are player" + playerID);
            if (playerID == field.getLp().length) {
                System.out.println("Waiting for other player...");
            }
            getS = new GetFromServer(in);
            sendS = new SendtoServer(out);
            getS.waitforstartMsg();

        } catch (Exception ex) {
            System.out.println("connection failed.");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    class GetFromServer implements Runnable {
        DataInputStream in;

        public GetFromServer(DataInputStream input) {
            in = input;

        }

        public void run() {
            try {
                while (true) {
                    for (int i = 0; i < field.getLp().length; i++) {
                        field.getLp()[i].setX(in.readInt());
                        field.getLp()[i].setY(in.readInt());
                    }
                    field.setCheck(in.readBoolean());
                    if(field.isCheck()==true){
                        Finish f = new Finish();
                        f.setVisible(true);
                        break;
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        public void waitforstartMsg() {
            try {
                String startmsg = in.readUTF();
                System.out.println("Message from server :" + startmsg);
                Thread readThread = new Thread(getS);
                Thread writeThread = new Thread(sendS);
                readThread.start();
                writeThread.start();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    class SendtoServer implements Runnable {
        DataOutputStream out;

        public SendtoServer(DataOutputStream output) {
            out = output;
        }

        public void run() {
            try {
                while (true) {
                    if (field.getLp()[playerID - 1] != null) {
                        out.writeInt(field.getLp()[playerID - 1].getX());
                        out.writeInt(field.getLp()[playerID - 1].getY());
                        out.flush();
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
