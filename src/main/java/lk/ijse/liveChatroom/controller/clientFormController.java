package lk.ijse.liveChatroom.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.naming.spi.InitialContextFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class clientFormController implements Initializable {
    public static String clientName;

    @FXML
    private VBox vbox;

    @FXML
    private Label nameField;
    @FXML
    private TextField text;

    @FXML
    void onsendButtonClick(ActionEvent event) throws IOException {
        serverFormController.sendingMsg(text.getText(), nameField.getText());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            nameField.setText(clientName);
            new Thread(()->{
                try {
                    Socket socket = new Socket("localhost",3001);
                    while (true){
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        String s = dataInputStream.readUTF();
                        Platform.runLater(()->{
                            vbox.getChildren().add(new Text(s));
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
    }
}
