
import Repository.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {

    static BoardRepository boardRepository = new BoardRepository();
    static CategoryRepository categoryRepository = new CategoryRepository();
    static CommentRepository commentRepository = new CommentRepository();
    static LogRepository logRepository = new LogRepository();
    static MessageRepository messageRepository = new MessageRepository();
    static TaskRepository taskRepository = new TaskRepository();
    static TeamRepository teamRepository = new TeamRepository();
    static UserRepository userRepository = new UserRepository();
    static IdGenerator idGenerator = new IdGenerator();

    private static void fillRepositories() {
        categoryRepository.convertTableToObject();
        boardRepository.convertTableToObject();
        taskRepository.convertTableToObject();
        teamRepository.convertTableToObject();
        userRepository.convertTableToObject();
    }

    private static void getExistingDateFromDataBase() {
        boardRepository.initData();
        categoryRepository.initData();
        commentRepository.initData();
        logRepository.initData();
        messageRepository.initData();
        taskRepository.initData();
        teamRepository.initData();
        userRepository.initData();
        idGenerator.initData();
    }

    private static void initTables() throws SQLException {
        boardRepository.creatTable();
        categoryRepository.creatTable();
        commentRepository.creatTable();
        logRepository.creatTable();
        messageRepository.creatTable();
        taskRepository.creatTable();
        teamRepository.creatTable();
        userRepository.creatTable();
        idGenerator.creatTable();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane mainPane = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
//        AnchorPane mainPane = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("LeaderBoard.fxml")));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            initTables();
            getExistingDateFromDataBase();
            fillRepositories();
        }catch (Exception e){
            e.printStackTrace();
        }

//        WelcomeMenu welcomeMenu = new WelcomeMenu("WelcomeMenu" , null) ;
//        welcomeMenu.show();
//        welcomeMenu.execute();
        launch(args);
    }
}
