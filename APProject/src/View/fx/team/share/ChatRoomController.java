package View.fx.team.share;

import Controller.Controller;
import Controller.dto.ChatRoomResponse;
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
import java.util.*;

public class ChatRoomController implements Initializable {

    private Controller controller = new Controller();

	@FXML
    private Button SendButton;

    @FXML
    private TextField Message;
    
    @FXML
    private Button CancelButton;

    @FXML
    private ListView MessageListView;


    @FXML
    void onClick_CancelButton(ActionEvent event) throws IOException {
        backToProfile(event);
    }

    private void backToProfile(ActionEvent event) throws IOException {

        int lastMenuIndex = UserInfo.menuStack.size() - 1;
        AnchorPane lastMenu = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(UserInfo.menuStack.get(lastMenuIndex)));
        UserInfo.menuStack.remove(lastMenuIndex);
        showDashboard(event, lastMenu, "Jira | Dashboard");
    }

    @FXML
    void onClick_SelectButton(ActionEvent event) throws IOException {

    	String message = Message.getText().toString();
    	if (isNotEmpty(message)){
            controller.sendMessage(UserInfo.getUsername(), UserInfo.getSelectedTeam(), message);
            updateMessages();
        }else{
            JOptionPane.showMessageDialog(null, "message is empty");
        }

    }

    public boolean isNotEmpty(String input) {
        return input != null && input.length() > 0;
    }


    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        updateMessages();


    }

    public void updateMessages(){
        ChatRoomResponse chatRoomResponse = controller.showChatRoom(UserInfo.getSelectedTeam());
        List<String> response = new ArrayList<>();
        if (chatRoomResponse.isSuccessful()){
            List<String> messages = chatRoomResponse.getMessages();
            List<String> senders = chatRoomResponse.getSenders();

            for (int i = 0; i < senders.size(); i++) {
                response.add(senders.get(i) + " : " + messages.get(i) );
            }
        }else {
            response.add(chatRoomResponse.getMessage());
        }

        ObservableList<String> observableArrayList =
                FXCollections.observableArrayList(response);

        MessageListView.setItems(observableArrayList);
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
