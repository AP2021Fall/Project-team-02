package View.fx.team.leader;

import Controller.Controller;
import View.fx.UserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {

    private Controller controller = new Controller();


	@FXML
    private Button EditUserButton;


    @FXML
    private TextField UsernameData;
    @FXML
    private TextField NewUsernameData;
    @FXML
    private TextField EmailData;
    @FXML
    private TextField PasswordData;


    
    @FXML
    private Button BackButton;


    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {

        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("TeamUserMenu.fxml"));

        showDashboard(event, profileMenu, "Jira | Dashboard | Team");
    }


    @FXML
    void onClick_EditButton(ActionEvent event) throws IOException {
        String username = UsernameData.getText().toString();
        if (isNotEmpty(username)){
            String response = controller.editUser(UserInfo.getUsername(),username, NewUsernameData.getText(), PasswordData.getText(), EmailData.getText());
            JOptionPane.showMessageDialog(null, response);

        }else{
            JOptionPane.showMessageDialog(null, "Please Enter Username");
        }

    }


    public boolean isNotEmpty(String input) {
        return input != null && input.length() > 0;
    }


    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        updateUsersList();
	}

    private void updateUsersList() {

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
