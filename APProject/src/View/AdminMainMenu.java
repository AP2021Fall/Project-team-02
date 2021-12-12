package View;

public class AdminMainMenu extends Menu {
    public AdminMainMenu(String name, Menu parent) {
        super(name, parent);
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
            showProfile(inputParse[3]) ;
        }
        else if(Regex.adminBanUser(input)) {
            banUser(inputParse[3]) ;
        }
        else if(Regex.adminChangeRole(input)) {
            changeRole(inputParse[3] , inputParse[5]) ;
        }
        else if(Regex.adminSendNotificationAll(input)) {
           sendNotificationAll(inputParse[2]) ;
        }
        else if(Regex.adminSendNotification(input)) {
            showProfile(inputParse[2] , inputParse[4]) ;
        }
        else if(Regex.adminNotificationTeam(input)) {
            showProfile(inputParse[2] , inputParse[4]) ;
        }
        else if(Regex.adminAccept(input)) {
            String[] parseInputUsernames1 = parseInputUsernames(inputParse[2]) ;
            adminAccept(parseInputUsernames1) ;
        }
        else if(Regex.adminReject(input)) {
            String[] parseInputUsernames1 = parseInputUsernames(inputParse[2]);
            adminreject(parseInputUsernames1);
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
