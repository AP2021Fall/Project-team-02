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
        // Code
        System.out.println("exit to close the program");
    }


    public void execute() {
        String input = getInput() ;
        String[] inputParse = parseInput(input) ;

    }
}
