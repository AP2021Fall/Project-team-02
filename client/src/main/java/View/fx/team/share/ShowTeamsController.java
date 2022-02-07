package View.fx.team.share;

import Controller.Controller;
import Controller.dto.ChangeUsernameResponse;
import View.fx.UserInfo;
import javafx.beans.value.ObservableValue;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowTeamsController implements Initializable {

    private Controller controller = new Controller();
    
    @FXML
    private Button BackButton;

    @FXML
    private ListView teamsListView;

    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {
        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ProfileDashboard.fxml"));
        showDashboard(event, profileMenu, "Jira | Dashboard | Profile");
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        List<String> teams = controller.showTeams(UserInfo.getUsername());
        if (teams != null){
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(teams);

            teamsListView.setItems(observableArrayList);
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
}
