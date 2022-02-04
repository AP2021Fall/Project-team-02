package View.fx.signup;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SignupController implements Initializable {

    static String username, password, email, confirmPassword;

    @FXML
    private TextField UsernameTextfield;

    @FXML
    private TextField EmailTextfield;

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    private PasswordField PasswordConfirmTextField;

    private Controller controller = new Controller();


    @FXML
    void LoginAction(ActionEvent event) {
        try {
            goToLoginPanel(event);
        } catch (Exception e) {

        }

    }


    @FXML
    void SignupAction(ActionEvent event) throws IOException {

        username = UsernameTextfield.getText().toString();
        email = EmailTextfield.getText().toString();
        password = PasswordTextField.getText().toString();
        confirmPassword = PasswordConfirmTextField.getText().toString();

        if (isNotEmpty(username) && isNotEmpty(email) && isNotEmpty(password) && isNotEmpty(confirmPassword)) {
            String response = controller.createUser(username, password, confirmPassword, email);
            JOptionPane.showMessageDialog(null, response);

            if("user created successfully!".equals(response)){
                goToLoginPanel(event);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
        }

    }

    private void goToLoginPanel(ActionEvent event) throws IOException {
        AnchorPane loginPage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Jira | Login");
        window.setScene(new Scene(loginPage));
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
