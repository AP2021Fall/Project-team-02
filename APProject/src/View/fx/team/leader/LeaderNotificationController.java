package View.fx.team.leader;

import Controller.Controller;
import View.fx.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeaderNotificationController implements Initializable {

    private Controller controller = new Controller();

    @FXML
    private Button SendToUserButton;
    @FXML
    private Button SendToTeamButton;

    @FXML
    private TextField MessageData;

    @FXML
    private TextField UsernameData;
    
    @FXML
    private Button BackButton;


    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {

        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("TeamDashboard.fxml"));

        showDashboard(event, profileMenu, "Jira | Dashboard | Team");
    }

    @FXML
    void onClick_UserButton(ActionEvent event) throws IOException {
        String message = MessageData.getText().toString();
        String username = UsernameData.getText().toString();

        if (isNotEmpty(message) && isNotEmpty(username)){
            String response = controller.sendMessageToMember(UserInfo.getUsername(), UserInfo.getSelectedTeam(), username, message);
            JOptionPane.showMessageDialog(null, response);

        }else{
            JOptionPane.showMessageDialog(null, "Please Enter Message && Username Fields");
        }


    }

    @FXML
    void onClick_TeamButton(ActionEvent event) throws IOException {
        String message = MessageData.getText().toString();

        if (isNotEmpty(message)){
            String response = controller.sendMessageToTeam(UserInfo.getUsername(),UserInfo.getSelectedTeam(), message);
            JOptionPane.showMessageDialog(null, response);
        }else{
            JOptionPane.showMessageDialog(null, "Please Enter Message");
        }


    }


    public boolean isNotEmpty(String input) {
        return input != null && input.length() > 0;
    }


    @Override
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
