package View.fx.login;

import Controller.Controller;
import Controller.dto.LoginResponse;
import Model.Role;
import View.AdminMainMenu;
import View.MainMenu;
import View.fx.UserInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Login_Controller implements Initializable {

    static String username, password;

    @FXML
    private TextField UsernameTextfield;

    @FXML
    private PasswordField PasswordTextField;

    private Controller controller = new Controller();


    @FXML
    void SignupAction(ActionEvent event) {
        try {
            AnchorPane signupPage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("Signup.fxml"));
            showDashboard(event, signupPage, "Jira | Sign up");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void LoginAction(ActionEvent event) throws IOException {

        username = UsernameTextfield.getText().toString();
        password = PasswordTextField.getText().toString();

        if (isNotEmpty(username) && isNotEmpty(password)) {
            LoginResponse loginResponse = controller.loginUser(username, password);
            JOptionPane.showMessageDialog(null, loginResponse.getMessage());

            if (loginResponse.getMessage().equalsIgnoreCase("user logged in successfully!")) {
                UserInfo.setData(loginResponse.getUsername(), loginResponse.getRole());
                switch (loginResponse.getRole()) {
                    case Role.SYSTEM_ADMINISTRATOR:
                        AnchorPane adminDashboard = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("AdminDashboard.fxml"));
                        showDashboard(event, adminDashboard, "Jira | Admin Dashboard");
                        break;
                    case Role.TEAM_LEADER:
                        AnchorPane teamLeaderDashboard = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("TeamLeaderDashboard.fxml"));
                        showDashboard(event, teamLeaderDashboard, "Jira | Team Leader Dashboard");
                        break;
                    case Role.TEAM_MEMBER:
                        AnchorPane teamMemberDashboard = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("TeamMemberDashboard.fxml"));
                        showDashboard(event, teamMemberDashboard, "Jira | Team Member Dashboard");
                        break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
        }
    }

    private void showDashboard(ActionEvent event, AnchorPane teamMemberDashboard, String title) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(teamMemberDashboard));
        window.centerOnScreen();
        window.setResizable(false);
        window.show();
    }

    public boolean isNotEmpty(String input) {
        return input != null && input.length() > 0;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
