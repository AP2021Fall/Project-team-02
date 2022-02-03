package View.fx.signup;

import Controller.Controller;
import com.sun.deploy.util.StringUtils;
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
            AnchorPane EmployeeHomePane = (AnchorPane) FXMLLoader.load(getClass().getResource("../login/login.fxml"));
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
    void SignupAction(ActionEvent event) throws IOException {

        username = UsernameTextfield.getText().toString();
        email = EmailTextfield.getText().toString();
        password = PasswordTextField.getText().toString();
        confirmPassword = PasswordConfirmTextField.getText().toString();

        if (isNotEmpty(username) && isNotEmpty(email) && isNotEmpty(password) && isNotEmpty(confirmPassword)) {
            String response = controller.createUser(username, password, confirmPassword, email);
            JOptionPane.showMessageDialog(null, response);
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
