package Repository;

import Model.Category;
import Repository.table.CategoryTable;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryRepository extends AbstractDataBaseConnector{

    private static Map<Integer, Category> categoriesById = new HashMap<>();
    private static Map<Integer, CategoryTable> categoryTablesById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE categories " +
                        "(id SERIAL PRIMARY KEY, " +
                        " boardId INTEGER, " +
                        " name VARCHAR(255), " +
                        " tasks VARCHAR(255)) ";

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
            ResultSet rs = stmt.executeQuery("SELECT id, boardId, name, tasks FROM categories");
        ) {
            while(rs.next()){
                Category category = new Category(rs.getInt("id"),
                        rs.getString("name"));

                CategoryTable categoryTable = new CategoryTable(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("boardId"),
                        rs.getString("tasks"));


                categoriesById.put(category.getId(), category);
                categoryTablesById.put(categoryTable.getId(), categoryTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "categories";
    }

    public void removeByBoard(Integer boardId) {
        List<Category> toRemove = categoriesById.values().stream().filter(c -> c.getBoard().getId().equals(boardId)).collect(Collectors.toList());
        toRemove.forEach(c -> {
            categoriesById.remove(c.getId());
            categoryTablesById.remove(c.getId());
        });
    }

}
