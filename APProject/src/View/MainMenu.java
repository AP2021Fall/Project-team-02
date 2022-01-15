package View;

import Controller.Controller;
import Model.*;

import java.util.List;

public class MainMenu extends Menu {
    private String username;
    private String role;
    private Controller controller = new Controller();
    private String team;

    public MainMenu(String name, Menu parent, String username, String role, String team) {
        super(name, parent);
        this.username = username;
        this.role = role;
        this.team = team;
    }

    public void show() {
        super.show();
        System.out.println("Use <enter menu <Menu Name>> to select a menu");
        System.out.println("Profile Menu");
        System.out.println("Team Menu");
        System.out.println("Tasks Page");
        System.out.println("Calendar Menu");
        System.out.println("Notification Bar");
        if (Role.TEAM_LEADER.equals(role)) {   // Anita
            System.out.println("-----------------------------------");
            System.out.println("admin commands: ");
            System.out.println("show teams");
            System.out.println("show team <team name or team number>");
            System.out.println("create team <teamName>");
        }
    }

    public void execute() {
        String input = getInput();
        String[] inputParse = parseInput(input);
        if (inputParse[0].trim().equalsIgnoreCase("back")) {
            this.nextMenu = parent;
        } else if (Regex.mainMenuEnterMenu(input)) {
            if (inputParse[1].trim().equalsIgnoreCase("profile")) {
                this.nextMenu = new ProfileMenu("profileMenu", this, username);
            } else if (inputParse[1].trim().equalsIgnoreCase("team")) {
                this.nextMenu = new TeamMenu("teamMenu", this, username, role);
            } else if (inputParse[1].trim().equalsIgnoreCase("tasks")) {
                this.nextMenu = new TasksPage("taskMenu", this, username, role);
            } else if (inputParse[1].trim().equalsIgnoreCase("calender")) {
                this.nextMenu = new CalendarMenu("calendarMenu", this, username);
            } else if (inputParse[1].trim().equalsIgnoreCase("notification")) {
                this.nextMenu = new NotificationBar("notifications", this, username);
            }else if (inputParse[1].trim().equalsIgnoreCase("Board")) {
                this.nextMenu = new BoardMenu("BoardMenu", this, username, role, team);
            }
        } else if (Regex.leaderShowTeam(input) && Role.TEAM_LEADER.equals(role)) {
            String teamName = inputParse[2];
            List<String> teamInfo = controller.showTeam(username, teamName);
            if (teamInfo != null)
                teamInfo.forEach(System.out::println);
            this.nextMenu = this;
            nextMenu.execute();
        } else if (Regex.leaderShowTeams(input)) {
            List<String> teams = controller.showTeams(username);
            if (teams != null)
                teams.forEach(System.out::println);
            this.nextMenu = this;
            nextMenu.execute();
        } else if (Regex.leaderCreateTeam(input)) {
            String response = controller.createNewTeam(username, inputParse[2]);
            System.out.println(response);
            this.nextMenu = this;
            nextMenu.execute();
        } else {
            System.out.println("Your input is not valid");
            this.nextMenu = this;
            nextMenu.execute();
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
