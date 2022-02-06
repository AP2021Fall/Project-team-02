package View.fx.admin;

import Controller.Controller;
import Controller.dto.AdminShowProfileResponse;
import View.fx.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationAdminController {
    private Controller controller = new Controller();
    @FXML
    private Button TeamsButton ;

    @FXML
    private Button UsersButton ;

    @FXML
    private Button ScoreBoardButton ;

    @FXML
    private Button showData ;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button SendToUser;

    @FXML
    private Button SendToTeam ;

    @FXML
    private Button SendToAll;

    @FXML
    private TextField UserNameField ;

    @FXML
    private TextField TeamNameField ;

    @FXML
    private TextField MessageField ;

    @FXML
    void onAction_SendToAll(ActionEvent event) throws IOException {
        try {
            String userNames ;
            userNames = MessageField.getText();
            String response = controller.adminSendMessageToAllUsers(UserInfo.getUsername(), userNames);
            UserNameField.setText(response);
        }
        catch (Exception e) {
            UserNameField.setText("Error");
        }
    }
    @FXML
    void onAction_SendNotificationToUser(ActionEvent event) throws IOException {
        try {
            String input , userNames;
            input = MessageField.getText();
            userNames = UserNameField.getText();
            String response = controller.adminSendMessageToUser(UserInfo.getUsername(), userNames ,input);
            UserNameField.setText(response);
        }
        catch (Exception e) {
            UserNameField.setText("Error");
        }
    }

    @FXML
    void onAction_SendUserName(ActionEvent event) throws IOException {
        try {
            String input , teamNames;
            input = MessageField.getText();
            teamNames = TeamNameField.getText();
            String response = controller.adminSendMessageToUser(UserInfo.getUsername(), teamNames ,input);
            UserNameField.setText(response);
        }
        catch (Exception e) {
            UserNameField.setText("Error");
        }
    }

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
    private void showDashboard(ActionEvent event, AnchorPane teamMemberDashboard, String title) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(teamMemberDashboard));
        window.centerOnScreen();
        window.setResizable(false);
        window.show();
    }

}
