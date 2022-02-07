package View.fx.team.share;

import Controller.Controller;
import Controller.dto.RoadMapResponse;
import Model.Log;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RoadMapController implements Initializable {

    private Controller controller = new Controller();
    
    @FXML
    private Button BackButton;

    @FXML
    private ListView logsListView;

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
        RoadMapResponse roadMapResponse = controller.showRoadMap(UserInfo.getSelectedTeam());
        if (roadMapResponse.isSuccessful()){
            List<String> response =  new ArrayList<>();
            roadMapResponse.getTasksStatus().forEach((task, status) -> response.add(task + " " + status + "% done"));
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(response);

            logsListView.setItems(observableArrayList);
        }else{
            JOptionPane.showMessageDialog(null, roadMapResponse.getMessage());

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
