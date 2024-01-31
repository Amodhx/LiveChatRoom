package lk.ijse.liveChatroom.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class loginFormController {

    @FXML
    private TextField textField;
    ArrayList<String> names = new ArrayList<>();

    @FXML
    void onlogging(ActionEvent event) throws IOException {
        serverFormController.clientName = textField.getText();
        Stage stage = new Stage();
        clientFormController.clientName = textField.getText();
        names.add(textField.getText());
        serverFormController.names = this.names;
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/clientForm.fxml"))));
        stage.setResizable(false);
        stage.show();

    }

}
