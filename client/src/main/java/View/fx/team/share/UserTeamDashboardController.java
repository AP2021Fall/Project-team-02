package View.fx.team.share;

import Controller.Controller;
import View.fx.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserTeamDashboardController implements Initializable {

    private String dashboardName = "UserTeamDashboard.fxml";

    private Controller controller = new Controller();

    @FXML
    private Button BoardButton;

    @FXML
    private Button ScoreButton;

    @FXML
    private Button RoadMapButton;

    @FXML
    private Button ChatRoomButton;

    @FXML
    private Button TasksButton;

    @FXML
    private Button BackButton;

    @FXML
    void onAction_BoardButton(ActionEvent event) throws IOException {
        UserInfo.menuStack.add(dashboardName);
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("MemberBoard.fxml"));
        showDashboard(event, profilePage, "Jira | Dashboard | Team Menu | BoardMenu" );
    }

    @FXML
    void onAction_ScoreButton(ActionEvent event) throws IOException {
        UserInfo.menuStack.add(dashboardName);
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ScoreBoard.fxml"));
        showDashboard(event, profilePage, "Jira | Dashboard | Team Menu | ScoreBoard" );
    }

    @FXML
    void onAction_BackButton(ActionEvent event) throws IOException {
    	AnchorPane lastMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("SelectTeam.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Jira | Dashboard | select team");
        window.setScene(new Scene(lastMenu));
        window.show();
    }

    @FXML
    void onAction_RoadMapButton(ActionEvent event) throws IOException {
        UserInfo.menuStack.add(dashboardName);
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("RoadMap.fxml"));
        showDashboard(event, profilePage, "Jira | Dashboard | Team Menu | RoadMap" );
    }

    @FXML
    void onAction_ChatRoomButton(ActionEvent event) throws IOException {
        UserInfo.menuStack.add(dashboardName);
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ChatRoom.fxml"));
        showDashboard(event, profilePage, "Jira | Dashboard | Team Menu | ChatRoom" );
    }

    @FXML
    void onAction_TasksButton(ActionEvent event) throws IOException {
        UserInfo.menuStack.add(dashboardName);
        AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("TeamMenuTasks.fxml"));
        showDashboard(event, profilePage, "Jira | Dashboard | Team Menu | tasks" );
    }

    @SuppressWarnings("unused")
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




