package View.fx.team.member;

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
import java.util.Objects;
import java.util.ResourceBundle;

public class MemberBoardController implements Initializable {

    public static final String SHOW_BOARD = "Show board";
    private Controller controller = new Controller();


    @FXML
    private Button ShowBoardButton;

    @FXML
    private Button MoveTaskToNextButton;


    @FXML
    private TextField BoardName;


    @FXML
    private TextField Task;

    
    @FXML
    private Button BackButton;


    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {

        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("UserTeamDashboard.fxml")));

        showDashboard(event, profileMenu, "Jira | Dashboard");
    }


    @FXML
    void onClick_ShowBoardButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        if (isNotEmpty(boardName)){
           UserInfo.setSelectedBoard(boardName);

            UserInfo.menuStack.add("MemberBoard.fxml");
            AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ShowBoard.fxml"));
            showDashboard(event, profilePage, SHOW_BOARD );

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name");

        }
    }


    @FXML
    void onClick_MoveTaskNextButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        String taskTitle = Task.getText().toString();

        if (isNotEmpty(boardName) &&  isNotEmpty(taskTitle)){
            String response = controller.moveTaskToNextCategory(UserInfo.getUsername(), UserInfo.getSelectedTeam(), boardName, taskTitle);
            if (response != null)
                JOptionPane.showMessageDialog(null, response);

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name and task title");

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
