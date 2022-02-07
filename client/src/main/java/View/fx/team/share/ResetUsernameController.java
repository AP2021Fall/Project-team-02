package View.fx.team.share;

import Controller.Controller;
import Controller.dto.ChangePasswordResponse;
import Controller.dto.ChangeUsernameResponse;
import View.fx.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetUsernameController implements Initializable {

    private Controller controller = new Controller();

	@FXML
    private Button ResetButton;

    @FXML
    private TextField NewUsername;
    
    @FXML
    private Button CancelButton;

    @FXML
    void onClick_CancelButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {
        System.out.println("testss");
        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ProfileDashboard.fxml"));

        showDashboard(event, profileMenu, "Jira | Dashboard | Profile");
    }

    @FXML
    void onClick_ResetButton(ActionEvent event) throws IOException {

    	String newUsername = NewUsername.getText().toString();

    	if (isNotEmpty(newUsername)){
            ChangeUsernameResponse changeUsernameResponse = controller.changeUsername(UserInfo.getUsername(), newUsername);
            if (changeUsernameResponse.isSuccessful()){
                UserInfo.setUsername(changeUsernameResponse.getNewUsername());
            }else{
                JOptionPane.showMessageDialog(null, changeUsernameResponse.getMessage());

            }
            backToProfile(event);
        }else{
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
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
