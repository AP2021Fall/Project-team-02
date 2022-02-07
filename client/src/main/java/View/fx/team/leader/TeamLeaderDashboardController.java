package View.fx.team.leader;

import Controller.ClientController;
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

    public String name = "Leader Dashboard";

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
        showDashboard(event, logoutPage, "Jira | Login");
    }

    @FXML
    void onClick_Tasks_Button(ActionEvent event) throws IOException {
        AnchorPane createTeam = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("EditTasks.fxml"));
        showDashboard(event, createTeam, "Jira | Edit Tasks");

    }

    @FXML
    void onClick_Team_Button(ActionEvent event) throws IOException {
        UserInfo.menuStack.add("TeamLeaderDashboard.fxml");
        UserInfo.setTargetPanel("TeamDashboard.fxml");
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("SelectTeam.fxml"));
        showDashboard(event, profilePage, name + " | select team" );
    }

    @FXML
    void onClick_Teams_Button(ActionEvent event) throws IOException {
        AnchorPane createTeam = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("CreateTeam.fxml"));
        showDashboard(event, createTeam, "Jira | Create Team");

    }

    @FXML
    void onClick_Profile_Button(ActionEvent event) throws IOException {
        UserInfo.menuStack.add("TeamLeaderDashboard.fxml");
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ProfileDashboard.fxml"));
        showDashboard(event, profilePage, name + " | profile" );
    }

    @FXML
    void onClick_Calender_Button(ActionEvent event) throws IOException {
        UserInfo.menuStack.add("TeamLeaderDashboard.fxml");
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ShowCalender.fxml"));
        showDashboard(event, profilePage, name + " | calender" );
    }

    @FXML
    void onClick_Notifications_Button(ActionEvent event) throws IOException {
        UserInfo.menuStack.add("TeamLeaderDashboard.fxml");

        AnchorPane resetPassMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ShowNotificationsLists.fxml"));

        showDashboard(event, resetPassMenu, name + "Notifications");

    }

    private void showDashboard(ActionEvent event, AnchorPane teamMemberDashboard, String title) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(teamMemberDashboard));
        window.centerOnScreen();
        window.setResizable(false);
        window.show();
    }
}
