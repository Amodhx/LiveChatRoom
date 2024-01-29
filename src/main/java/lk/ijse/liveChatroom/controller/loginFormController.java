package lk.ijse.liveChatroom.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class loginFormController {

    @FXML
    private TextField textField;

    @FXML
    void onlogging(ActionEvent event) throws IOException {
        serverFormController.clientName = textField.getText();
        Stage stage = new Stage();
        clientFormController.clientName = textField.getText();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/clientForm.fxml"))));
        stage.setResizable(false);
        stage.show();

    }

}
