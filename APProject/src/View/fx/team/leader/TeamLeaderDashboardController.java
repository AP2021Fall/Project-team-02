package View.fx.team.leader;

import View.fx.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TeamLeaderDashboardController {

    @FXML
    private Button Profile_Button;

    @FXML
    private Button Tasks_Button;

    @FXML
    private Button Calender_Button;

    @FXML
    private Button Team_Button;

    @FXML
    private Button Notifications_Button;

    @FXML
    private Button Teams_Button;

    @FXML
    private Button LogoutButton;

    @FXML
    void onAction_LogoutButton(ActionEvent event) throws IOException {
        UserInfo.logout();
        AnchorPane logoutPage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Jira | Login");
        window.setScene(new Scene(logoutPage));
        window.centerOnScreen();
        window.setResizable(false);
        window.show();
    }

    @FXML
    void onClick_Tasks_Button(ActionEvent event) throws IOException {
        System.out.println("tasks");

    }

    @FXML
    void onClick_Team_Button(ActionEvent event) throws IOException {
        System.out.println("team");

    }

    @FXML
    void onClick_Teams_Button(ActionEvent event) throws IOException {
        System.out.println("teams");

    }

    @FXML
    void onClick_Profile_Button(ActionEvent event) throws IOException {
        System.out.println("profile");

        System.out.println(UserInfo.getUsername());
        System.out.println(UserInfo.getRole());

    }

    @FXML
    void onClick_Calender_Button(ActionEvent event) throws IOException {
        System.out.println("calender");

    }

    @FXML
    void onClick_Notifications_Button(ActionEvent event) throws IOException {
        System.out.println("notification");


    }
}
