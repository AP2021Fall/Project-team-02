package View.fx.team.share;

import Controller.Controller;
import Controller.dto.ShowProfileResponse;
import View.fx.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileDashboardController implements Initializable {

    private Controller controller = new Controller();

    @FXML
    private Button RestPasswordButton;

    @FXML
    private Button RestUsernameButton;

    @FXML
    private Button ShowTeamsButton;

    @FXML
    private Button SearchTeamButton;

    @FXML
    private Button NotificationButton;


    @FXML
    private Button BackButton;

    @FXML
    private Label t1;

    @FXML
    private Label t2;

    @FXML
    private Label t4;

    @FXML
    private Label t5;

    @FXML
    private Label t3;



    @FXML
    void onAction_ResetPasswordButton(ActionEvent event) throws IOException {
        AnchorPane resetPassMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("RestPassword.fxml"));

        showDashboard(event, resetPassMenu, "RestPassword");
    }

    @FXML
    void onAction_ResetUsernameButton(ActionEvent event) throws IOException {
        AnchorPane resetPassMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("RestUsername.fxml"));

        showDashboard(event, resetPassMenu, "RestUsername");
    }

    @FXML
    void onAction_BackButton(ActionEvent event) throws IOException {
        int lastMenuIndex = UserInfo.menuStack.size() - 1;
    	AnchorPane lastMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(UserInfo.menuStack.get(lastMenuIndex)));
    	UserInfo.menuStack.remove(lastMenuIndex);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Jira | Dashboard");
        window.setScene(new Scene(lastMenu));
        window.show();
    }

    @FXML
    void onAction_ShowTeamsButton(ActionEvent event) throws IOException {
        AnchorPane resetPassMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ShowTeamLists.fxml"));

        showDashboard(event, resetPassMenu, "Teams List");
    }

    @FXML
    void onAction_SearchTeamButton(ActionEvent event) throws IOException {
        System.out.println("search teams");
    }

    @FXML
    void onAction_NotificationsButton(ActionEvent event) throws IOException {
        AnchorPane resetPassMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ShowNotificationsLists.fxml"));

        showDashboard(event, resetPassMenu, "Notifications");
    }

	@SuppressWarnings("unused")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        ShowProfileResponse profileResponse = controller.showProfile(UserInfo.getUsername());
             t1.setText(profileResponse.getUsername());
             t2.setText(profileResponse.getFullName());
             t4.setText(profileResponse.getEmail());
             t5.setText(profileResponse.getBirthDate());
             t3.setText(profileResponse.getRole());


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




