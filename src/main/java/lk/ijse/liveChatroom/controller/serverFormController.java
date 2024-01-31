package lk.ijse.liveChatroom.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    static Socket socket ;
    DataInputStream dataInputStream ;
     static DataOutputStream dataOutputStream ;
    static ArrayList<Socket> sockets = new ArrayList<>();

    public static String clientName ;
    static ArrayList<String> names = new ArrayList<>();
    Text text = new Text("");
    public static void sendImage(String msgtoSend ,String name) throws IOException {

        for (int i = 0; i < sockets.size(); i++) {
            dataOutputStream = new DataOutputStream(sockets.get(i).getOutputStream());
            if (name.equals(names.get(i))){
                dataOutputStream.writeUTF("me: "+msgtoSend);
                dataOutputStream.flush();
                continue;
            }
            clientFormController.imageClientName =name;
            dataOutputStream.writeUTF(name+" : "+msgtoSend);
            dataOutputStream.flush();
        }
    }

    public static void sendingMsg(String msg, String name) throws IOException {

        for (int i = 0; i < sockets.size(); i++) {
            dataOutputStream = new DataOutputStream(sockets.get(i).getOutputStream());
            if (name.equals(names.get(i))){
                dataOutputStream.writeUTF("me: "+msg);
                dataOutputStream.flush();
                continue;
            }
            clientFormController.imageClientName = name;
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
