package View.fx.team.member;

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

public class TeamMemberDashboardController {

    public String name = "Team Member Dashboard";

    @FXML
    private Button Profile_Button;

    @FXML
    private Button Calender_Button;

    @FXML
    private Button Team_Button;

    @FXML
    private Button Notifications_Button;

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
    void onClick_Team_Button(ActionEvent event) throws IOException {
        UserInfo.menuStack.add("TeamMemberDashboard.fxml");
        UserInfo.setTargetPanel("UserTeamDashboard.fxml");
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("SelectTeam.fxml"));
        showDashboard(event, profilePage, name + " | select team" );

    }

    @FXML
    void onClick_Profile_Button(ActionEvent event) throws IOException {
        UserInfo.menuStack.add("TeamMemberDashboard.fxml");
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ProfileDashboard.fxml"));
        showDashboard(event, profilePage, name + " | profile" );

    }

    @FXML
    void onClick_Calender_Button(ActionEvent event) throws IOException {
        UserInfo.menuStack.add("TeamMemberDashboard.fxml");
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ShowCalender.fxml"));
        showDashboard(event, profilePage, name + " | calender" );
    }

    @FXML
    void onClick_Notifications_Button(ActionEvent event) throws IOException {
        UserInfo.menuStack.add("TeamMemberDashboard.fxml");

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
