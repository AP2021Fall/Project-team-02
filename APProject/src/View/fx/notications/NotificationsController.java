package View.fx.notications;


import View.fx.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class NotificationsController {
    @FXML
    private Button Back;
    @FXML
    private Button LogoutButton;


    void onAction_LogoutButton(ActionEvent event) throws IOException {
        UserInfo.logout();
        AnchorPane logoutPage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("Notification.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Jira | Notifications");
        window.setScene(new Scene(logoutPage));
        window.centerOnScreen();
        window.setResizable(false);
        window.show();
    }
    void onClick_BackButton(ActionEvent event) throws IOException {
        System.out.println("back");

    }

}
