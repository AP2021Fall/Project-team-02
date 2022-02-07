package View.fx.database;

import Repository.*;

import java.sql.SQLException;

public class DatabaseUpdater {

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

    public static void databaseInit() {
        try {
            initTables();
            getExistingDateFromDataBase();
            fillRepositories();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
