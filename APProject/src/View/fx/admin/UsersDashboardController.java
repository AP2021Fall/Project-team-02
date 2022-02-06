package View.fx.admin;

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
import java.net.URL;
import java.util.ResourceBundle;

public class UsersDashboardController {
    @FXML
    private Button showData ;

    @FXML
    private Button onAction_NotificationButton;

    @FXML
    private Button UsersButton;

    @FXML
    private Button ScoreBoardButton;

    @FXML
    private Button TeamsButton;

    @FXML
    private Button LogoutButton;

    @FXML
    void onAction_ScoreBoardButton(ActionEvent event) throws IOException {
        AnchorPane adminScore = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("AdminScoreBoard.fxml")) ;
        showDashboard(event , adminScore , "Jira | Admin | ScoreBoard");
    }

    @FXML
    void onAction_NotificationButton(ActionEvent event) throws IOException {
        AnchorPane adminScore = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("NotificationAdmin.fxml")) ;
        showDashboard(event , adminScore , "Jira | Admin | Notifications");
    }

    @FXML
    void onAction_UsersButton(ActionEvent event) throws IOException {
        // to do
    }

    @FXML
    void onAction_TeamsButton(ActionEvent event) throws IOException {
        AnchorPane adminScore = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("AdminTeams.fxml")) ;
        showDashboard(event , adminScore , "Jira | Admin | Team");
    }
    @FXML
    void onAction_DataButton(ActionEvent event) throws  IOException{
        AnchorPane adminScore = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("DataDashbord.fxml")) ;
        showDashboard(event , adminScore , "Jira | Admin | Dashboard");
    }
    @FXML
    void onAction_LogoutButton(ActionEvent event) throws IOException {
        UserInfo.logout();
        AnchorPane loginPage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Jira | Login");
        window.setScene(new Scene(loginPage));
        window.centerOnScreen();
        window.setResizable(false);
        window.show();

    }
    
    public void initialize(URL arg0, ResourceBundle arg1) {

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
