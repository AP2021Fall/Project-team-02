package View;

import Controller.Controller;
import Controller.dto.ShowTaskResponse;
import Model.Role;
import Model.Task;
import Model.User;

import java.util.Arrays;
import java.util.List;

public class TeamMenu extends Menu {

    private String username;
    private String role;
    private Controller controller = new Controller();

    public TeamMenu(String name, Menu parent, String username, String role) {
        super(name, parent);

        this.username = username;
        this.role = role;
    }


    public void show() {
        super.show();
        List<String> teams = controller.showTeamMenu(username);
        teams.forEach(System.out::println);
        System.out.println("Use to enter your teamMenu : "
                + "enter team <teamName>");
    }


    public void execute() {
        String input = getInput();
        String[] inputParse = parseInput(input);
        if (inputParse[0].trim().equalsIgnoreCase("back")) {
            this.nextMenu = parent;
            nextMenu.show();
            nextMenu.execute();
        } else if (inputParse[1].trim().equalsIgnoreCase("team")) {
            boolean hasTeam = controller.userHasThisTeam(username, inputParse[2]);
            if (hasTeam) {
                show2(inputParse[2]);
            } else {
                System.out.println("this team not exist");
                this.nextMenu = this;
                nextMenu.show();
                nextMenu.execute();
            }
        } else {
            System.out.println("invalid input!");
            this.nextMenu = this;
            nextMenu.show();
            nextMenu.execute();
        }
    }

    public void show2(String team) {
        System.out.println("Use to see team info :");
        System.out.println("board Menu");
        System.out.println("scoreboard show");
        System.out.println("roadmap show");
        System.out.println("chatroom show");
        System.out.println("show tasks");
        System.out.println("show task id <id>");
        if (Role.TEAM_LEADER.equals(role)) {
            System.out.println("Admin menu: ");
            System.out.println("-------------------------------");
            System.out.println("sudo show all tasks");
            System.out.println("create task title <taskTitle> startTime <start time> deadline <deadline>");
            System.out.println("show members");
            System.out.println("add member username <username>");
            System.out.println("delete member username <username>");
            System.out.println("suspend member username <username>");
            System.out.println("promote username <username> rate <rate>");
            System.out.println("assign member task <taskId> username <username>");
            System.out.println("send notification <notification> username <username>");
            System.out.println("send notification <notification> teamName <teamName>");
        }
        execute2(team);
    }

    public void execute2(String team) {
        String input2 = getInput();
        String[] inputParse2 = parseInput(input2);
        if (inputParse2[0].trim().equalsIgnoreCase("back")) {
            this.nextMenu = parent;
        } else if (inputParse2[0].trim().equalsIgnoreCase("boardMenu")) {
            this.nextMenu = new BoardMenu("BoardMenu", this, username, role, team);
            nextMenu.show();
            nextMenu.execute();

        } else if (inputParse2[0].trim().equalsIgnoreCase("scoreboard")) {
            this.nextMenu = new ScoreBoard("scoreBoard", this, team, username, role);
        } else if (inputParse2[0].trim().equalsIgnoreCase("roadmap")) {
            this.nextMenu = new RoadMap("roadMap", this, team, username, role);
        } else if (inputParse2[0].trim().equalsIgnoreCase("chatroom")) {
            this.nextMenu = new ChatRoom("chatroom", this, team, username, role);
        }else if (inputParse2[0].trim().equalsIgnoreCase("showTasks")) {
                List<Task> tasks = controller.showAllTasks(username, team);
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    System.out.println(i + 1
                            + ".title: " + task.getTitle()
                            +":id" + task.getId()
                            + ",creation date:" + task.getCreationDate()
                            +",deadline:" + task.getDeadLine()
                            + ",assign to:" + Arrays.toString(task.getUsers().stream().map(User::getUsername).toArray())
                            + ",priority:" + task.getPriority());
                }
                this.nextMenu = this;
                this.execute2(team);
        } else if (inputParse2[1].trim().equalsIgnoreCase("task") && !inputParse2[0].trim().equalsIgnoreCase("create")) {
            try {
                int taskId = Integer.parseInt(inputParse2[3]);
                ShowTaskResponse showTaskResponse = controller.showTask(username, taskId);
                if (showTaskResponse.getMessage() != null) {
                    System.out.println(showTaskResponse.getMessage());
                } else {
                    Task task = showTaskResponse.getTask();

                    System.out.println(team
                            + ":id"
                            + task.getId()
                            + ",creation date:" + task.getCreationDate()
                            + ",deadline:" + task.getDeadLine()
                            + ",assign to:" + Arrays.toString(task.getUsers().stream().map(User::getUsername).toArray())
                            + ",priority:" + task.getPriority());
                }

            } catch (Exception e) {
            }
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[0].

                trim().

                equalsIgnoreCase("sudo")) {
            List<Task> tasks = controller.showAllTasksOfTeam(username, team);
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.println(i + 1
                        + ".title: " + task.getTitle()
                        + ":id" + task.getId()
                        + ",creation date:" + task.getCreationDate()
                        + ",deadline:" + task.getDeadLine()
                        + ",assign to:" + Arrays.toString(task.getUsers().stream().map(User::getUsername).toArray())
                        + ",priority:" + task.getPriority());
            }
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[0].

                trim().

                equalsIgnoreCase("create")) {
            String response = controller.createTask(username, team, inputParse2[3], inputParse2[5], inputParse2[7]);
            System.out.println(response);
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[1].

                trim().

                equalsIgnoreCase("members")) {
            List<String> members = controller.showMembersName(username, team);
            members.forEach(System.out::println);
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[0].

                trim().

                equalsIgnoreCase("Add")) {
            String response = controller.addMemberToTeam(username, team, inputParse2[3]);
            System.out.println(response);
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[0].

                trim().

                equalsIgnoreCase("delete")) {
            String response = controller.deleteTeamMember(username, team, inputParse2[3]);
            System.out.println(response);
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[0].

                trim().

                equalsIgnoreCase("suspend")) {
            String response = controller.suspendTeamMember(username, team, inputParse2[3]);
            System.out.println(response);
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[0].

                trim().

                equalsIgnoreCase("promote")) {
            String response = controller.promoteTeamMember(username, team, inputParse2[2]);
            System.out.println(response);
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[0].

                trim().

                equalsIgnoreCase("assign")) {
            try {
                String response = controller.assignTaskToMember(username, team, inputParse2[5], Integer.parseInt(inputParse2[3]));
                System.out.println(response);
            } catch (Exception e) {

            }
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[1].

                trim().

                equalsIgnoreCase("notification") && inputParse2[3].

                trim().

                equalsIgnoreCase("username")) {
            String response = controller.sendMessageToMember(username, team, inputParse2[4], inputParse2[2]);
            System.out.println(response);
            this.nextMenu = this;
            this.execute2(team);
        } else if (inputParse2[1].

                trim().

                equalsIgnoreCase("notification") && inputParse2[3].

                trim().

                equalsIgnoreCase("teamName")) {
            String response = controller.sendMessageToTeam(username, inputParse2[4], inputParse2[2]);
            System.out.println(response);
            this.nextMenu = this;
            this.execute2(team);
        } else {
            System.out.println("Invalid input try again");
            this.nextMenu = this;
            this.execute2(team);
        }
        nextMenu.show();
        nextMenu.execute();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
