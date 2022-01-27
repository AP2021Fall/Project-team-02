package Repository;

import Model.Board;
import Model.Team;
import Repository.table.BoardTable;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BoardRepository extends AbstractDataBaseConnector{

    public static Map<Integer, Board> boarsById = new HashMap<>();
    private static Map<Integer, BoardTable> boarTablesById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (!tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE boards " +
                        "(id SERIAL PRIMARY KEY, " +
                        " name VARCHAR(255), " +
                        " teamId INTEGER, " +
                        " active INTEGER , " +
                        " tasksCategory VARCHAR(255)) ";

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initData() {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name, teamId, tasksCategory, active FROM boards");
        ) {
            while(rs.next()){
                boolean isActive = rs.getInt("active") == 1;

                Board board = new Board(rs.getInt("id"),
                        rs.getString("name"));
                board.setActive(isActive);

                BoardTable boardTable = new BoardTable(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("teamId"),
                        rs.getString("tasksCategory"));
                boardTable.setActive(isActive);

                boarsById.put(board.getId(), board);
                boarTablesById.put(boardTable.getId(), boardTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "boards";
    }

    public void createBoard(Board board) {
        board.setId(IdGenerator.getNewId());
        insert(board.getTable());
        boarsById.put(board.getId(), board);
    }


    public void remove(Board board) {
        boarsById.remove(board.getId());
        removeById(board.getId());
        boarTablesById.remove(board.getId());
    }

    private void removeById(Integer id) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement = conn
                    .prepareStatement("delete from boards where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public void insert(BoardTable boardTable){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("INSERT INTO boards (id, name, teamId, active, tasksCategory) VALUES(?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, boardTable.getId());
            preparedStatement.setString(2, boardTable.getName());
            preparedStatement.setInt(3, boardTable.getTeamId());
            preparedStatement.setInt(4, boardTable.getActive());
            preparedStatement.setString(5, boardTable.getTasksCategory());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convertTableToObject(){
        for (Board board : boarsById.values()) {
            BoardTable boardTable = boarTablesById.get(board.getId());
            if (boardTable.getTeamId() != null){
                Team team = TeamRepository.teamsById.get(boardTable.getTeamId());
                if (team != null){
                    board.setTeam(team);
                    team.getBoards().add(board);
                }
            }
        }

    }

    public void update(Board board){
        update(board.getTable());
    }

    private void update(BoardTable boardTable){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("update boards " +
                            " set name = ?, teamId = ?, active = ?, tasksCategory = ?" +
                            " where id = ?");

            preparedStatement.setString(1, boardTable.getName());
            preparedStatement.setInt(2, boardTable.getTeamId());
            preparedStatement.setInt(3, boardTable.getActive());
            preparedStatement.setString(4, boardTable.getTasksCategory());
            preparedStatement.setInt(5, boardTable.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
