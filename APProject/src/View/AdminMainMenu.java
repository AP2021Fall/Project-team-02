package View;

import Controller.Controller;
import Controller.dto.AdminShowProfileResponse;
import Controller.dto.ScoreBoardResponse;

import java.util.Arrays;
import java.util.List;

public class AdminMainMenu extends Menu {
    private String adminUsername;
    private Controller controller = new Controller();

    public AdminMainMenu(String name, Menu parent,String adminUsername) {
        super(name, parent);
        this.adminUsername = adminUsername;
    }
    public void show() {
        System.out.println("Welcome to Admin Menu :");
        System.out.println("Use this commends: ");
        System.out.println("show profile username <username>");
        System.out.println("ban user user <username>");
        System.out.println("change role user <username> role <new role>");
        System.out.println("send notification <notification> all");
        System.out.println("send notification <notification> user <username>");
        System.out.println("send notification <notification> team <teamName>");
        System.out.println("show scoreboard team <team>");
        System.out.println("show pendingTeams") ;
        System.out.println("accept teams <team1,team2,...>");
        System.out.println("reject teams <team1,team2,...>");
        System.out.println("exit to close the program");
    }
    public void execute() {
        String input = getInput() ;
        String[] inputParse = parseInput(input) ;
        if(inputParse[0].trim().equalsIgnoreCase("exit")) {
            System.exit(1) ;
        }
        else if(Regex.adminShowProfile(input)) {
            AdminShowProfileResponse showProfileResponse = controller.adminShowProfile(adminUsername, inputParse[3]) ;
            if (showProfileResponse.isSuccessful()){
                System.out.println(showProfileResponse.getUser().profile());
            }else {
                System.out.println(showProfileResponse.getMessage());
            }
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.adminBanUser(input)) {
            String response = controller.adminBanUser(adminUsername, inputParse[3]);
            System.out.println(response);
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.adminChangeRole(input)) {
            String response = controller.adminChangeUserRole(adminUsername, inputParse[3] , inputParse[5]);
            System.out.println(response);
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.adminSendNotificationAll(input)) {
            String response = controller.adminSendMessageToAllUsers(adminUsername, inputParse[2]) ;
            System.out.println(response);
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.adminSendNotification(input)) {
            String response = controller.adminSendMessageToUser(adminUsername, inputParse[4] , inputParse[2]) ;
            System.out.println(response);
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.adminNotificationTeam(input)) {
            String response = controller.adminSendMessageToTeam(adminUsername, inputParse[4] , inputParse[2]) ;
            System.out.println(response);
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.adminAccept(input)) {
            String[] parseInputUsernames1 = parseInputUsernames(inputParse[2]) ;
            String response = controller.adminAcceptTeams(adminUsername, Arrays.asList(parseInputUsernames1)) ;
            System.out.println(response);
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.adminReject(input)) {
            String[] parseInputUsernames1 = parseInputUsernames(inputParse[2]);
            String response = controller.adminRejectTeams(adminUsername, Arrays.asList(parseInputUsernames1));
            System.out.println(response);
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.adminShowPendingTeam(input)) {

            List<String> pendingTeams = controller.adminShowPendingTeams(adminUsername);
            pendingTeams.forEach(System.out::println);
            this.nextMenu = this;
            nextMenu.execute();
        }else if(Regex.adminShowScoreBoard(input)){
            String teamName = inputParse[3];
            ScoreBoardResponse scoreBoardResponse = controller.showScoreBoard(teamName);
            if (scoreBoardResponse.isSuccessful()){
                scoreBoardResponse.getUsersScores().forEach((username, score) -> System.out.println(username +" : " + score));

            }else{
                System.out.println(scoreBoardResponse.getMessage());
            }
            this.nextMenu = this;
            nextMenu.execute();
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
