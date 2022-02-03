package View.fx.login;

import Controller.Controller;
import Controller.dto.LoginResponse;
import Model.Role;
import View.AdminMainMenu;
import View.MainMenu;
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
            AnchorPane EmployeeHomePane = (AnchorPane) FXMLLoader.load(getClass().getResource("../signup/signup.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Sign up");
            window.setScene(new Scene(EmployeeHomePane));
            window.centerOnScreen();
            window.setResizable(false);
            window.show();
        } catch (Exception e) {

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
                //	if (loginResponse.getRole().equals(Role.SYSTEM_ADMINISTRATOR))
                // TODO: 2/3/2022 go to admin panel
                //	else
                // TODO: 2/3/2022  go to main panel
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
        }
    }

    public boolean isNotEmpty(String input) {
        return input != null && input.length() > 0;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
