package Repository;

import Model.*;
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
                        " messages VARCHAR(255)," +
                        " suspendMembers VARCHAR(255)," +
                        " tasks VARCHAR(255)" +
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
             ResultSet rs = stmt.executeQuery("SELECT id, name, leaderId, active, usersScore, members, messages, tasks, suspendMembers  FROM teams");
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
                        rs.getString("tasks"),
                        rs.getString("suspendMembers"),
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
        insert(team.getTable());
        return teamsById.put(team.getId(), team);
    }

    public List<Team> findAll() {
        return new ArrayList<>(teamsById.values());
    }

    public void remove(Team t) {
        teamsById.remove(t.getId());
        teamTablesById.remove(t.getId());
    }

    public void insert(TeamTable teamTable) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("INSERT INTO teams (id, name, leaderId, active, usersScore, members,messages, tasks, suspendMembers) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, teamTable.getId());
            preparedStatement.setString(2, teamTable.getName());
            preparedStatement.setInt(3, teamTable.getLeaderId());
            preparedStatement.setBoolean(4, teamTable.isActive());
            preparedStatement.setString(5, teamTable.getUsersScore());
            preparedStatement.setString(6, teamTable.getMembers());
            preparedStatement.setString(7, teamTable.getMessages());
            preparedStatement.setString(8, teamTable.getTasks());
            preparedStatement.setString(9, teamTable.getSuspendMembers());

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
//                    e.printStackTrace();
                }
            }

            String[] messages = teamTable.getMessages().split(",");
            for (String messageIdStr : messages) {
                try {
                    Integer messageId = Integer.parseInt(messageIdStr);
                    MessageRepository.messages.stream().filter(m -> m.getId().equals(messageId)).findAny().ifPresent(message -> team.getMessages().add(message));
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }

            String[] tasks = teamTable.getTasks().split(",");
            for (String taskIdStr : tasks) {
                try {
                    Integer taskId = Integer.parseInt(taskIdStr);
                    Task task = TaskRepository.tasksById.get(taskId);
                    if (task != null)
                        team.getTasks().add(task);
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }

            String[] usersScore = teamTable.getUsersScore().split(",");
            for (int i = 1; i < usersScore.length; i += 2) {
                try {

                    Integer userId = Integer.parseInt(usersScore[i - 1]);
                    Integer score = Integer.parseInt(usersScore[i]);

                    team.getUsersScore().put(userId, score);
                } catch (Exception e) {
//                    e.printStackTrace();
                }

            }

            String[] suspendMembers = teamTable.getSuspendMembers().split(",");
            for (String memberIdStr : suspendMembers) {
                try {
                    Integer memberId = Integer.parseInt(memberIdStr);
                    User member = UserRepository.usersById.get(memberId);
                    if (member != null) {
                        team.getSuspendMembers().add(member);
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }
        }

    }

    public void update(Team team){
        update(team.getTable());
    }

    private void update(TeamTable teamTable) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("update teams " +
                            " set name = ?, leaderId = ?, active = ?, usersScore = ?, members = ?,messages = ?, tasks = ?" +
                            " where id = ?");

            preparedStatement.setString(1, teamTable.getName());
            preparedStatement.setInt(2, teamTable.getLeaderId());
            preparedStatement.setBoolean(3, teamTable.isActive());
            preparedStatement.setString(4, teamTable.getUsersScore());
            preparedStatement.setString(5, teamTable.getMembers());
            preparedStatement.setString(6, teamTable.getMessages());
            preparedStatement.setString(7, teamTable.getTasks());
            preparedStatement.setInt(8, teamTable.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
