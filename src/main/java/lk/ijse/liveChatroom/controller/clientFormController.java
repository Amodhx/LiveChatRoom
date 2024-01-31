package lk.ijse.liveChatroom.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lk.ijse.liveChatroom.emojiPicker.EmojiPicker;

import javax.naming.spi.InitialContextFactory;
import java.awt.*;
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
    private  Label nameField;
    @FXML
    private TextField text;
    Socket socket;

    @FXML
    private JFXButton emojiButton;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton imgebutton;
    public static String imageClientName = "";
    public static String imgPath;
    @FXML
    void imageOnClick(ActionEvent event) throws IOException {
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getDirectory()+dialog.getFile();
        dialog.dispose();
        sendImage(file);
        imgPath =file;
        System.out.println(file + " chosen.");
    }
    private  void sendImage(String msgToSend) throws IOException {
        serverFormController.sendImage(msgToSend,nameField.getText());
    }



    @FXML
    void onEmojiClick(ActionEvent event) {
        emojiPick();

    }

    private void emojiPick() {
        EmojiPicker emojiPicker = new EmojiPicker();

        VBox vBox = new VBox(emojiPicker);
        vBox.setPrefSize(150,300);
        vBox.setLayoutX(260);
        vBox.setLayoutY(230);
        vBox.setStyle("-fx-font-size: 30");
        pane.getChildren().add(vBox);

        emojiPicker.setVisible(true);

        emojiButton.setOnAction(event3 -> {
            if (emojiPicker.isVisible()){
                emojiPicker.setVisible(false);
            }else {
                emojiPicker.setVisible(true);
            }
        });

        emojiPicker.getEmojiListView().setOnMouseClicked(event3-> {
            String selectedEmoji = emojiPicker.getEmojiListView().getSelectionModel().getSelectedItem();
            if (selectedEmoji != null) {
                text.setText(text.getText()+selectedEmoji);
            }
            emojiPicker.setVisible(false);
        });
    }

    @FXML
    void onsendButtonClick(ActionEvent event) throws IOException {
        serverFormController.sendingMsg(text.getText(), nameField.getText());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            nameField.setText(clientName);
            new Thread(()->{
                try {
                     socket = new Socket("localhost",3001);
                    while (true){
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        String s = dataInputStream.readUTF();
                        Platform.runLater(()->{
                            Text text1 = new Text(s);
                            if (s.startsWith("me")) {
                                if (s.startsWith("me: C")) {
                                    Image image = new Image(imgPath);
                                    ImageView imageView = new ImageView(image);
                                    imageView.setFitHeight(200);
                                    imageView.setFitWidth(200);
                                    HBox hBox = new HBox();
                                    hBox.setPadding(new Insets(5, 5, 5, 10));
                                    Text text11 = new Text("me: ");
                                    HBox hBox1 = new HBox();
                                    hBox1.getChildren().add(text11);
                                    hBox.getChildren().add(imageView);
                                    hBox.setAlignment(Pos.CENTER_RIGHT);
                                    hBox1.setAlignment(Pos.CENTER_RIGHT);

                                    vbox.getChildren().add(hBox1);
                                    vbox.getChildren().add(hBox);
                                } else {
                                        HBox hBox = new HBox();
                                        hBox.getChildren().add(text1);
                                        hBox.setAlignment(Pos.CENTER_RIGHT);
                                        vbox.getChildren().add(hBox);
                                    }
                                } else {

                                System.out.println(imageClientName);
                                if (s.startsWith(imageClientName+" : C")) {
                                    Image image = new Image(imgPath);
                                    ImageView imageView = new ImageView(image);
                                    imageView.setFitHeight(200);
                                    imageView.setFitWidth(200);
                                    HBox hBox = new HBox();
                                    hBox.setPadding(new Insets(5, 5, 5, 10));
                                    hBox.getChildren().add(imageView);
                                    Text text11 = new Text(imageClientName+": ");
                                    HBox hBox1 = new HBox();
                                    hBox1.getChildren().add(text11);
                                    hBox.setAlignment(Pos.CENTER_LEFT);
                                    hBox1.setAlignment(Pos.CENTER_LEFT);

                                    vbox.getChildren().add(text11);
                                    vbox.getChildren().add(hBox);
                                }else {
                                    HBox hBox = new HBox();
                                    hBox.getChildren().add(text1);
                                    hBox.setAlignment(Pos.CENTER_LEFT);
                                    vbox.getChildren().add(hBox);
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
    }
}
