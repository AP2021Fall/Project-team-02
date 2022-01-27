
import Repository.*;
import View.WelcomeMenu;

import java.sql.SQLException;

public class Main {

    static BoardRepository boardRepository = new BoardRepository();
    static CategoryRepository categoryRepository = new CategoryRepository();
    static CommentRepository commentRepository = new CommentRepository();
    static LogRepository logRepository = new LogRepository();
    static MessageRepository messageRepository = new MessageRepository();
    static TaskRepository taskRepository = new TaskRepository();
    static TeamRepository teamRepository = new TeamRepository();
    static UserRepository userRepository = new UserRepository();
    static IdGenerator idGenerator = new IdGenerator();

    public static void main(String[] args) {
        try {
            initTables();
            getExistingDateFromDataBase();
            fillRepositories();
        }catch (Exception e){
//            e.printStackTrace();
        }

        WelcomeMenu welcomeMenu = new WelcomeMenu("WelcomeMenu" , null) ;
        welcomeMenu.show();
        welcomeMenu.execute();
    }

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

}
