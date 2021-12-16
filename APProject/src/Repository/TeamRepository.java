package Repository;

import Model.Team;
import Repository.table.TeamTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamRepository extends AbstractDataBaseConnector {

    private static Map<Integer, Team> teamsById = new HashMap<>();
    private static Map<Integer, TeamTable> teamTablesById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE teams " +
                        "(id SERIAL PRIMARY KEY, " +
                        " name VARCHAR(255), " +
                        " leaderId INTEGER, " +
                        " active boolean," +
                        " usersScore VARCHAR(255)," +
                        " members VARCHAR(255)," +
                        " messages VARCHAR(255)," +
                        " boards VARCHAR(255)" +
                        ") ";

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, leaderId, active, usersScore, members, messages, boards  FROM teams");
        ) {
            while (rs.next()) {
                Team team = new Team(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("active"));

                TeamTable teamTable = new TeamTable(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("leaderId"),
                        rs.getString("usersScore"),
                        rs.getString("members"),
                        rs.getString("messages"),
                        rs.getString("boards"),
                        rs.getBoolean("active"));


                teamsById.put(team.getId(), team);
                teamTablesById.put(teamTable.getId(), teamTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "teams";
    }

    public Team findByTeamName(String teamName) {
        return teamsById.values().stream().filter(team -> team.getName().equals(teamName)).findAny().orElse(null);
    }

    public Team createTeam(Team team) {
        team.setId(IdGenerator.getNewId());
        return teamsById.put(team.getId(), team);
    }

    public List<Team> findAll() {
        return new ArrayList<>(teamsById.values());
    }

    public void remove(Team t) {
        teamsById.remove(t.getId());
        teamTablesById.remove(t.getId());
    }

}
