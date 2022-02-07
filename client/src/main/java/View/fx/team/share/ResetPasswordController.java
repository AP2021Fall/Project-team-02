package View.fx.team.share;

import Controller.Controller;
import Controller.dto.ChangePasswordResponse;
import View.fx.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {

    private Controller controller = new Controller();

	@FXML
    private Button ResetButton;

    @FXML
    private PasswordField OldPass1;

    @FXML
    private PasswordField NewPass1;
    
    @FXML
    private Button CancelButton;

    @FXML
    void onClick_CancelButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {
        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ProfileDashboard.fxml"));

        showDashboard(event, profileMenu, "Jira | Dashboard | Profile");
    }

    @FXML
    void onClick_ResetButton(ActionEvent event) throws IOException {

    	String oldPass = OldPass1.getText().toString();
    	String newPass = NewPass1.getText().toString();
    	if (isNotEmpty(oldPass) && isNotEmpty(newPass)){
            ChangePasswordResponse changePasswordResponse = controller.changePassword(UserInfo.getUsername(),oldPass, newPass);
            if (changePasswordResponse.isSuccessful()){

                UserInfo.logout();

                AnchorPane loginMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
                Stage window1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window1.setTitle("Jira | Login");
                window1.setScene(new Scene(loginMenu));
                window1.show();
            }else {
                System.out.println(changePasswordResponse.getMessage());
                JOptionPane.showMessageDialog(null, changePasswordResponse.getMessage());
                backToProfile(event);
            }
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
