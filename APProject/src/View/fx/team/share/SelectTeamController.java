package View.fx.team.share;

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
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class SelectTeamController implements Initializable {

    private Controller controller = new Controller();

	@FXML
    private Button SelectButton;

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

        int lastMenuIndex = UserInfo.menuStack.size() - 1;
        AnchorPane lastMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(UserInfo.menuStack.get(lastMenuIndex)));
        UserInfo.menuStack.remove(lastMenuIndex);
        showDashboard(event, lastMenu, "Jira | Dashboard");
    }

    @FXML
    void onClick_SelectButton(ActionEvent event) throws IOException {

    	String teamName = TeamName.getText().toString();

    	if (isNotEmpty(teamName)){
            boolean hasTeam = controller.userHasThisTeam(UserInfo.getUsername(), teamName);
            if (hasTeam) {
                UserInfo.setSelectedTeam(teamName);
                AnchorPane lastMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(UserInfo.targetPanel));

                showDashboard(event, lastMenu, "Jira | Dashboard");
            } else {
                JOptionPane.showMessageDialog(null, "this team not exist");

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
        List<String> teams = controller.showTeams(UserInfo.getUsername());
        if (teams != null){
            Set<String> teamsSet = new HashSet<>(teams);
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(teamsSet);

            TeamListView.setItems(observableArrayList);
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
