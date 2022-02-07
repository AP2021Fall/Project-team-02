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
import java.util.Objects;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable {

    public static final String SHOW_BOARD = "Show board";
    private Controller controller = new Controller();

	@FXML
    private Button CreateBoardButton;
    @FXML
    private Button RemoveBoardButton;
    @FXML
    private Button ShowBoardButton;
    @FXML
    private Button AddCategoryButton;
    @FXML
    private Button ForceTaskCatButton;
    @FXML
    private Button AssignTaskButton;

    @FXML
    private Button MoveTaskToNextButton;
    @FXML
    private Button AddTaskToBoardButton;
    @FXML
    private Button DoneBoardButton;
    @FXML
    private Button CategoryColumnButton;

    @FXML
    private TextField BoardName;

    @FXML
    private TextField CategoryName;

    @FXML
    private TextField Username;

    @FXML
    private TextField Task;

    @FXML
    private TextField Column;
    
    @FXML
    private Button BackButton;


    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {

        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TeamDashboard.fxml")));

        showDashboard(event, profileMenu, "Jira | Dashboard");
    }

    @FXML
    void onClick_CreateButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        if (isNotEmpty(boardName)){
            String response = controller.createBoard(UserInfo.getUsername(), UserInfo.getSelectedTeam(), boardName);
            if (response != null)
                JOptionPane.showMessageDialog(null, response);
        }else {
            JOptionPane.showMessageDialog(null, "please enter board name");

        }
    }

    @FXML
    void onClick_RemoveBoardButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        if (isNotEmpty(boardName)){
            String response = controller.removeBoard(UserInfo.getUsername(), UserInfo.getSelectedTeam(), boardName);
            if (response != null)
                JOptionPane.showMessageDialog(null, response);

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name");

        }
    }

    @FXML
    void onClick_ShowBoardButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        if (isNotEmpty(boardName)){
           UserInfo.setSelectedBoard(boardName);

            UserInfo.menuStack.add("LeaderBoard.fxml");
            AnchorPane profilePage = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("ShowBoard.fxml"));
            showDashboard(event, profilePage, SHOW_BOARD );

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name");

        }
    }

    @FXML
    void onClick_AddCategoryButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        String categoryName = CategoryName.getText().toString();
        if (isNotEmpty(boardName) && isNotEmpty(categoryName)){
            String response = controller.addCategoryToBoard(UserInfo.getUsername(), UserInfo.getSelectedTeam(),boardName, categoryName, null);
            if (response != null)
                JOptionPane.showMessageDialog(null, response);

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name and category name");

        }

    }

    @FXML
    void onClick_ForceTaskCatButton(ActionEvent event) throws IOException {

        String boardName = BoardName.getText().toString();
        String categoryName = CategoryName.getText().toString();
        String taskTitle = Task.getText().toString();

        if (isNotEmpty(boardName) && isNotEmpty(categoryName) && isNotEmpty(taskTitle)){
            String response = controller.forceUpdateTaskCategory(UserInfo.getUsername(), UserInfo.getSelectedTeam(),boardName, categoryName, taskTitle);
            if (response != null)
                JOptionPane.showMessageDialog(null, response);

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name and category name and task title");

        }
    }

    @FXML
    void onClick_AssignButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        String username = Username.getText().toString();
        String taskIdStr = Task.getText().toString();

        if (isNotEmpty(boardName) && isNotEmpty(username) && isNotEmpty(taskIdStr) && taskIdStr.chars().allMatch(Character::isDigit)){
            try {
                int taskId = Integer.parseInt(taskIdStr);

                String response = controller.assignTaskToMember(UserInfo.getUsername(), UserInfo.getSelectedTeam(), username, boardName, taskId);
                if (response != null)
                    JOptionPane.showMessageDialog(null, response);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "invalid task id");

            }

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name and category name and task id");

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

    @FXML
    void onClick_AddTaskToBoardButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        String taskIdStr = Task.getText().toString();

        if (isNotEmpty(boardName) &&  isNotEmpty(taskIdStr) && taskIdStr.chars().allMatch(Character::isDigit)){
            try {
                int taskId = Integer.parseInt(taskIdStr);

                String response = controller.addTaskToBoard(UserInfo.getUsername(), UserInfo.getSelectedTeam(),boardName, taskId);
                if (response != null)
                    JOptionPane.showMessageDialog(null, response);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "invalid task id");

            }

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name and task id");

        }

    }

    @FXML
    void onClick_DoneBoardButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        if (isNotEmpty(boardName) ){
            String response = controller.doneBoard(UserInfo.getUsername(), UserInfo.getSelectedTeam(), boardName);
            if (response != null)
                JOptionPane.showMessageDialog(null, response);

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name");

        }
    }

    @FXML
    void onClick_CategoryColumnButton(ActionEvent event) throws IOException {
        String boardName = BoardName.getText().toString();
        String categoryName = CategoryName.getText().toString();
        String columnStr = Column.getText().toString();

        if (isNotEmpty(boardName) && isNotEmpty(categoryName) && isNotEmpty(columnStr) && columnStr.chars().allMatch(Character::isDigit)){
            try {
                int columnId = Integer.parseInt(columnStr);

                String response = controller.updateCategoryColumn(UserInfo.getUsername(), UserInfo.getSelectedTeam(),boardName, categoryName, columnId);
                if (response != null)
                    JOptionPane.showMessageDialog(null, response);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "invalid task id");

            }

        }else {
            JOptionPane.showMessageDialog(null, "please enter board name and category name and column id");

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
