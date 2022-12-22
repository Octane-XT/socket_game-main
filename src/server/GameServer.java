package server;

import map.Field;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    ServerSocket socket;
    int player;
    int max_player;
    Socket[] psocket;

    GetFromClient[] pget;

    SendtoClient[] psend;

    Field field;

    public GameServer() {
        this.player = 0;
        this.max_player = 3;
        field = new Field(15, 15);
        field.fill_map();
        field.generate_wall();
        field.generate_player(max_player);
        field.goal();

        try {
            socket = new ServerSocket(9999);
        } catch (IOException ex) {
            System.out.println("Failed to create GameServer...");
        }
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.acceptConnection();
    }

    public void acceptConnection() {
        try {
            System.out.println("Waiting for connection...");
            psocket = new Socket[max_player];
            pget = new GetFromClient[max_player];
            psend = new SendtoClient[max_player];
            Thread[] readThread = new Thread[max_player];
            Thread[] writeThread = new Thread[max_player];
            while (player < max_player) {
                Socket s = socket.accept();
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                DataInputStream in = new DataInputStream(s.getInputStream());
                ObjectOutputStream obj_out = new ObjectOutputStream(s.getOutputStream());
                player++;
                out.writeInt(player);
                obj_out.writeObject(field);
                System.out.println("Player" + player + "connected.");

                GetFromClient getc = new GetFromClient(player, in);
                SendtoClient sendc = new SendtoClient(player, out);

                psocket[player - 1] = s;
                pget[player - 1] = getc;
                psend[player - 1] = sendc;
                psend[player - 1].sendstartmsg();
                readThread[player - 1] = new Thread(pget[player - 1]);
                writeThread[player - 1] = new Thread(psend[player - 1]);
                readThread[player - 1].start();
                writeThread[player - 1].start();

            }
            System.out.println("Server full...");
        } catch (IOException ex) {
            System.out.println("connection failed.");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    class GetFromClient implements Runnable {
        int playerID;
        DataInputStream in;

        public GetFromClient(int pid, DataInputStream input) {
            playerID = pid;
            in = input;
        }

        public void run() {
            try {
                while (true) {
                    field.getLp()[playerID - 1].setX(in.readInt());
                    field.getLp()[playerID - 1].setY(in.readInt());
                    field.check(playerID - 1);
                    System.out.println(field.isCheck());
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    class SendtoClient implements Runnable {
        int playerID;
        DataOutputStream out;
        ObjectOutputStream ob;

        public SendtoClient(int pid, DataOutputStream output) {
            playerID = pid;
            out = output;
        }

        public void run() {
            try {
                while (true) {
                    for (int i = 0; i < field.getLp().length; i++) {
                        out.writeInt(field.getLp()[i].getX());
                        out.writeInt(field.getLp()[i].getY());
                    }
                    out.writeBoolean(field.isCheck());
                    out.flush();
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        public void sendstartmsg() {
            try {
                out.writeUTF("go");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
