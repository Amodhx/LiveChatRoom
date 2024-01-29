package lk.ijse.liveChatroom.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class serverFormController implements Initializable  {

    @FXML
    private VBox vbox;
    ServerSocket serverSocket ;
    public static String name  = "Amodh";
    Socket socket ;
    DataInputStream dataInputStream ;
     static DataOutputStream dataOutputStream ;
    static ArrayList<Socket> sockets = new ArrayList<>();

    public static String clientName ;
    Text text = new Text("");

    public static void sendingMsg(String msg, String name) throws IOException {
        for (Socket socket : sockets) {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(name+" : "+msg);
            dataOutputStream.flush();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            text.setText("Server Started");
            vbox.getChildren().add(text);
            serverSocket = new ServerSocket(3001);
            new Thread(() ->{
                try {
                    while (true) {
                        socket = serverSocket.accept();
                        sockets.add(socket);
                        new Thread(() ->{
                            try {
                                dataInputStream = new DataInputStream(socket.getInputStream());
                                String s = dataInputStream.readUTF();
                                System.out.println(s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
