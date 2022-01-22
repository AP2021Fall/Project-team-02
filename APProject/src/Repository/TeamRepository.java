package Repository;

import Model.*;
import Repository.table.BoardTable;
import Repository.table.TeamTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamRepository extends AbstractDataBaseConnector {

    public static Map<Integer, Team> teamsById = new HashMap<>();
    private static Map<Integer, TeamTable> teamTablesById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (!tableExists(getTableName())) {
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
                        " messages VARCHAR(255)" +
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
             ResultSet rs = stmt.executeQuery("SELECT id, name, leaderId, active, usersScore, members, messages  FROM teams");
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
        insert(t.getTable());
        teamTablesById.remove(t.getId());
    }

    public void insert(TeamTable teamTable) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("INSERT INTO teams (id, name, leaderId, active, usersScore, members,messages ) VALUES(?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, teamTable.getId());
            preparedStatement.setString(2, teamTable.getName());
            preparedStatement.setInt(3, teamTable.getLeaderId());
            preparedStatement.setBoolean(4, teamTable.isActive());
            preparedStatement.setString(5, teamTable.getUsersScore());
            preparedStatement.setString(6, teamTable.getMembers());
            preparedStatement.setString(7, teamTable.getMessages());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void convertTableToObject() {
        for (Team team : teamsById.values()) {
            TeamTable teamTable = teamTablesById.get(team.getId());

            if (teamTable.getLeaderId() != null) {
                User user = UserRepository.usersById.get(teamTable.getLeaderId());
                if (user != null) {
                    team.setLeader(user);
                    user.getTeams().add(team);
                }
            }

            String[] members = teamTable.getMembers().split(",");
            for (String memberIdStr : members) {
                try {
                    Integer memberId = Integer.parseInt(memberIdStr);
                    User member = UserRepository.usersById.get(memberId);
                    if (member != null) {
                        team.getMembers().add(member);
                        member.getTeams().add(team);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String[] messages = teamTable.getMessages().split(",");
            for (String messageIdStr : messages) {
                try {
                    Integer messageId = Integer.parseInt(messageIdStr);
                    MessageRepository.messages.stream().filter(m -> m.getId().equals(messageId)).findAny().ifPresent(message -> team.getMessages().add(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String[] usersScore = teamTable.getUsersScore().split(",");
            for (int i = 1; i < usersScore.length; i += 2) {
                try {

                    Integer userId = Integer.parseInt(usersScore[i - 1]);
                    Integer score = Integer.parseInt(usersScore[i]);

                    team.getUsersScore().put(userId, score);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }


}
