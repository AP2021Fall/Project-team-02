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

public class CreateTeamController implements Initializable {

    private Controller controller = new Controller();

	@FXML
    private Button CreateButton;

    @FXML
    private TextField NewTeamName;
    
    @FXML
    private Button BackButton;

    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        back(event);
    }

    private void back(ActionEvent event) throws IOException {

        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("TeamLeaderDashboard.fxml"));

        showDashboard(event, profileMenu, "Jira | Dashboard");
    }

    @FXML
    void onClick_CreateButton(ActionEvent event) throws IOException {

    	String newTeamName = NewTeamName.getText().toString();

    	if (isNotEmpty(newTeamName)){
            String response = controller.createNewTeam(UserInfo.getUsername(), newTeamName);
            JOptionPane.showMessageDialog(null, response);
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
