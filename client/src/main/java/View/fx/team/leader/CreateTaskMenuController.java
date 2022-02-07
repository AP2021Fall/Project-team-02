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
import java.util.ResourceBundle;

public class CreateTaskMenuController implements Initializable {

    private Controller controller = new Controller();

    @FXML
    private Button CreateTaskButton;

    @FXML
    private TextField Title;

    @FXML
    private TextField StartDate;

    @FXML
    private TextField DeadLine;
    
    @FXML
    private Button BackButton;


    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {

        AnchorPane profileMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("TeamDashboard.fxml"));

        showDashboard(event, profileMenu, "Jira | Dashboard | Team");
    }

    @FXML
    void onClick_CreateButton(ActionEvent event) throws IOException {
        String title = Title.getText().toString();
        String startDate = StartDate.getText().toString();
        String deadline = DeadLine.getText().toString();
        if (isNotEmpty(title) && isNotEmpty(startDate) && isNotEmpty(deadline)){
            String response = controller.createTask(UserInfo.getUsername(), UserInfo.getSelectedTeam(), title, startDate, deadline);
            JOptionPane.showMessageDialog(null, response);

        }else{
            JOptionPane.showMessageDialog(null, "Please Enter Fields");
        }

    }

    public boolean isNotEmpty(String input) {
        return input != null && input.length() > 0;
    }


    private void showDashboard(ActionEvent event, AnchorPane teamMemberDashboard, String title) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(teamMemberDashboard));
        window.centerOnScreen();
        window.setResizable(false);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
