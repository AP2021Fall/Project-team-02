package View.fx.team.share;

import Controller.Controller;
import Model.Log;
import Model.Task;
import Model.User;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TeamMenuTasksController implements Initializable {

    private Controller controller = new Controller();
    
    @FXML
    private Button BackButton;

    @FXML
    private ListView TasksListView;

    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {
        int lastMenuIndex = UserInfo.menuStack.size() - 1;
        AnchorPane lastMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(UserInfo.menuStack.get(lastMenuIndex)));
        UserInfo.menuStack.remove(lastMenuIndex);
        showDashboard(event, lastMenu, "Jira | Dashboard | TeamMenu");
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        List<Task> tasks = controller.showAllTasks(UserInfo.getUsername(), UserInfo.getSelectedTeam());

        List<String> response = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String taskStr = i + 1
                    + ".title: " + task.getTitle()
                    +":id" + task.getId()
                    + ",creation date:" + task.getCreationDate()
                    +",deadline:" + task.getDeadLine()
                    + ",assign to:" + Arrays.toString(task.getUsers().stream().map(User::getUsername).toArray())
                    + ",priority:" + task.getPriority();

            response.add(taskStr);
        }


        if (!response.isEmpty()){

            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(response);

            TasksListView.setItems(observableArrayList);
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
