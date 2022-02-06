package View.fx.team.share;

import Controller.Controller;
import Controller.dto.ShowCalenderResponse;
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

public class ShowCalenderController implements Initializable {

    private Controller controller = new Controller();
    
    @FXML
    private Button BackButton;

    @FXML
    private ListView CalenderListView;

    @FXML
    void onClick_BackButton(ActionEvent event) throws IOException {
        int lastMenuIndex = UserInfo.menuStack.size() - 1;
        AnchorPane lastMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(UserInfo.menuStack.get(lastMenuIndex)));
        UserInfo.menuStack.remove(lastMenuIndex);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Jira | Dashboard");
        window.setScene(new Scene(lastMenu));
        window.show();
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        ShowCalenderResponse showCalenderResponse = controller.showCalender(UserInfo.getUsername());
        if (showCalenderResponse.getMessage() != null){
            JOptionPane.showMessageDialog(null, showCalenderResponse.getMessage());
        }else{
            ObservableList<String> observableArrayList = FXCollections.observableArrayList(showCalenderResponse.getDeadLines());
            CalenderListView.setItems(observableArrayList);
        }

    }

}
