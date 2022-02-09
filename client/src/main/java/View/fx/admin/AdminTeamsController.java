package View.fx.admin;

import Controller.Controller;
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
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdminTeamsController {
    private Controller controller = new Controller();

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
    private Button accept;

    @FXML
    private Button reject;

    @FXML
    private TextField pendingTeams ;

    @FXML
    private TextField teamsAccept ;

    @FXML
    private TextField teamsReject ;

    @FXML
    void onAction_Accept(ActionEvent event) throws IOException {
        try {
            String teams, response;
            teams = teamsAccept.getText();
            response = controller.adminAcceptTeams(UserInfo.getUsername(), Arrays.asList(teams));
            teamsAccept.setText(response);
        }
        catch (Exception e) {
            teamsAccept.setText("Error");
        }
    }

    @FXML
    void onAction_Reject(ActionEvent event) throws IOException {
        try {
            String teams , response;
            teams = teamsReject.getText();
            response = controller.adminRejectTeams(UserInfo.getUsername(), Arrays.asList(teams)) ;
            teamsReject.setText(response);
        }
        catch (Exception e) {
            teamsAccept.setText("Error");
        }
    }
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
        List<String> pendingTeams1 = controller.adminShowPendingTeams(UserInfo.getUsername());
        String list = pendingTeams.toString();
        pendingTeams.setText(list);
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
