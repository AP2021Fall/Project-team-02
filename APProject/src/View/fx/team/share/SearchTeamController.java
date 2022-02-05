package View.fx.team.share;

import Controller.Controller;
import Controller.dto.ChangeUsernameResponse;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SearchTeamController implements Initializable {

    private Controller controller = new Controller();

	@FXML
    private Button SearchButton;

    @FXML
    private TextField TeamName;
    
    @FXML
    private Button CancelButton;

    @FXML
    private ListView TeamListView;


    @FXML
    void onClick_CancelButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {

        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ProfileDashboard.fxml"));

        showDashboard(event, profileMenu, "Jira | Dashboard | Profile");
    }

    @FXML
    void onClick_SearchButton(ActionEvent event) throws IOException {

    	String teamName = TeamName.getText().toString();

    	if (isNotEmpty(teamName)){
            List<String> teamInfo = controller.showTeam(UserInfo.getUsername(), teamName);

            if (teamInfo != null){
                ObservableList<String> observableArrayList =
                        FXCollections.observableArrayList(teamInfo);

                TeamListView.setItems(observableArrayList);
            }else{
                JOptionPane.showMessageDialog(null, "team not found");
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
