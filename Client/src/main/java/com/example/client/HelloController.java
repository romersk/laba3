package com.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HelloController {
    private Client client;

    @FXML
    private TextField ipAddressField;

    @FXML
    private TextField portField;

    @FXML
    private Button buttonSend;

    @FXML
    private TextField sendX;

    @FXML
    private TextField sendY;

    @FXML
    private TextArea resultField;

    @FXML
    private Button buttonConnect;

    @FXML
    public void initialize() {
        ipAddressField.setText("127.0.0.1");
        portField.setText("1024");

        buttonConnect.setOnAction(actionEvent -> {
            client = new Client(ipAddressField.getText(), Integer.parseInt(portField.getText()));
            System.out.println("Connected to Server");
        });

        buttonSend.setOnAction(actionEvent -> {
            if (client != null) {
                try {
                    String pointX = sendX.getText();
                    String pointY = sendY.getText();
                    String res = pointX + " " + pointY + " ";
                    client.writeLine(res);
                    resultField.setText(client.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else resultField.setText("Нет соединения");
        });

    }
}
