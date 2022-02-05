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

public class EditeTasksController implements Initializable {

    private Controller controller = new Controller();

	@FXML
    private Button EditTitleButton;
    @FXML
    private Button EditDesButton;
    @FXML
    private Button EditProButton;
    @FXML
    private Button EditDeadButton;
    @FXML
    private Button AddUserButton;
    @FXML
    private Button RemoveUserButton;

    @FXML
    private TextField NewData;

    @FXML
    private TextField TaskId;
    
    @FXML
    private Button BackButton;


    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {

        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("TeamLeaderDashboard.fxml"));

        showDashboard(event, profileMenu, "Jira | Dashboard");
    }

    @FXML
    void onClick_TitleButton(ActionEvent event) throws IOException {
        String tasksIdStr = TaskId.getText().toString();
        String newData = NewData.getText().toString();
        if (isNotEmpty(tasksIdStr) && isNotEmpty(newData) && tasksIdStr.chars().allMatch(Character::isDigit)){
            try {
                int taskId = Integer.parseInt(tasksIdStr);
                String response = controller.editTaskTitle(UserInfo.getUsername(), taskId, newData);
                JOptionPane.showMessageDialog(null, response);

            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "invalid task id");
            }
        }else {
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
        }


    }

    @FXML
    void onClick_DesButton(ActionEvent event) throws IOException {
        String tasksIdStr = TaskId.getText().toString();
        String newData = NewData.getText().toString();
        if (isNotEmpty(tasksIdStr) && isNotEmpty(newData) && tasksIdStr.chars().allMatch(Character::isDigit)){
            try {
                int taskId = Integer.parseInt(tasksIdStr);

                String response = controller.editTaskDescription(UserInfo.getUsername(), taskId, newData);
                JOptionPane.showMessageDialog(null, response);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "invalid task id");
            }
        }else {
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
        }

    }

    @FXML
    void onClick_ProButton(ActionEvent event) throws IOException {
        String tasksIdStr = TaskId.getText().toString();
        String newData = NewData.getText().toString();
        if (isNotEmpty(tasksIdStr) && isNotEmpty(newData) && tasksIdStr.chars().allMatch(Character::isDigit)){
            try {
                int taskId = Integer.parseInt(tasksIdStr);

                String response = controller.editTaskPriority(UserInfo.getUsername(), taskId, newData);
                JOptionPane.showMessageDialog(null, response);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "invalid task id");
            }
        }else {
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
        }

    }

    @FXML
    void onClick_DeadButton(ActionEvent event) throws IOException {
        String tasksIdStr = TaskId.getText().toString();
        String newData = NewData.getText().toString();
        if (isNotEmpty(tasksIdStr) && isNotEmpty(newData) && tasksIdStr.chars().allMatch(Character::isDigit)){
            try {
                int taskId = Integer.parseInt(tasksIdStr);

                String response = controller.editTaskDeadline(UserInfo.getUsername(), taskId, newData);
                JOptionPane.showMessageDialog(null, response);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "invalid task id");
            }
        }else {
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
        }

    }

    @FXML
    void onClick_AddUserButton(ActionEvent event) throws IOException {
        String tasksIdStr = TaskId.getText().toString();
        String newData = NewData.getText().toString();
        if (isNotEmpty(tasksIdStr) && isNotEmpty(newData) && tasksIdStr.chars().allMatch(Character::isDigit)){
            try {
                int taskId = Integer.parseInt(tasksIdStr);

                String response = controller.addUserToTask(UserInfo.getUsername(), taskId, newData);
                JOptionPane.showMessageDialog(null, response);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "invalid task id");
            }
        }else {
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
        }

    }

    @FXML
    void onClick_RemoveUserButton(ActionEvent event) throws IOException {
        String tasksIdStr = TaskId.getText().toString();
        String newData = NewData.getText().toString();
        if (isNotEmpty(tasksIdStr) && isNotEmpty(newData) && tasksIdStr.chars().allMatch(Character::isDigit)){
            try {
                int taskId = Integer.parseInt(tasksIdStr);
                String response = controller.removeUserFromTask(UserInfo.getUsername(), taskId, newData);
                JOptionPane.showMessageDialog(null, response);

            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "invalid task id");
            }
        }else {
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
