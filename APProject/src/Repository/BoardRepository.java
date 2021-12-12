package Repository;

import Model.Board;
import Repository.table.BoardTable;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BoardRepository extends AbstractDataBaseConnector{

    private static Map<Integer, Board> boarsById = new HashMap<>();
    private static Map<Integer, BoardTable> boarTablesById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE boards " +
                        "(id SERIAL PRIMARY KEY, " +
                        " name VARCHAR(255), " +
                        " teamId INTEGER, " +
                        " categories VARCHAR(255)," +
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
            ResultSet rs = stmt.executeQuery("SELECT id, name, teamId, categories, tasksCategory FROM boards");
        ) {
            while(rs.next()){
                Board board = new Board(rs.getInt("id"),
                        rs.getString("name"));

                BoardTable boardTable = new BoardTable(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("teamId"),
                        rs.getString("categories"),
                        rs.getString("tasksCategory"));


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
        boarsById.put(board.getId(), board);
    }


    public void remove(Board board) {
        boarsById.remove(board.getId());
        boarTablesById.remove(board.getId());
    }

}
