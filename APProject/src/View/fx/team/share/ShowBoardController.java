package View.fx.team.share;

import Controller.Controller;
import Controller.dto.ShowBoardResponse;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowBoardController implements Initializable {

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
        showDashboard(event, lastMenu, "Jira | Board Menu");
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        ShowBoardResponse response = controller.showBoard(UserInfo.getUsername(), UserInfo.getSelectedTeam(), UserInfo.getSelectedBoard());
        if (response != null){
            List<String> responseString = response.print();

            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(responseString);

            logsListView.setItems(observableArrayList);
        }else {
            JOptionPane.showMessageDialog(null, "Board not found");
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
