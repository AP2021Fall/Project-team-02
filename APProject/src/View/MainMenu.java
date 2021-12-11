package View;

public class MainMenu extends Menu{
    public MainMenu(String name, Menu parent) {
        super(name, parent);
    }

    public void show() {
        super.show();
        System.out.println("Use <enter menu <Menu Name>> to select a menu");
        System.out.println("Profile Menu");
        System.out.println("Team Menu");
        System.out.println("Tasks Page");
        System.out.println("Calendar Menu");
        System.out.println("Notification Bar");
        if(userAdmin()) {
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
        if(inputParse[0].trim().equalsIgnoreCase("back")) {
            this.nextMenu = parent ;
        }
        else if(inputParse[1].trim().equalsIgnoreCase("profile")) {
            this.nextMenu = ProfileMenu ;
        }
        else if(inputParse[1].trim().equalsIgnoreCase("team")) {
            this.nextMenu = TeamMenu ;
        }
        else if(inputParse[1].trim().equalsIgnoreCase("tasks")) {
            this.nextMenu = TasksPage ;
        }
        else if(inputParse[1].trim().equalsIgnoreCase("calender")) {
            this.nextMenu = CalendarMenu ;
        }
        else if(inputParse[1].trim().equalsIgnoreCase("notification")) {
            this.nextMenu = NotificationBar ;
        }
        else if(inputParse[1].trim().equalsIgnoreCase("teams")) {
            System.out.println(showAllTeams());
            // if
        }
        else if(inputParse[1].trim().equalsIgnoreCase("team")) {
            // bug!
        }
        else if(inputParse[0].trim().equalsIgnoreCase("create")) {
            System.out.println(createTeam(inputParse[2]));
        }
        else {
            System.out.println("Your input is not valid");
            this.nextMenu = this ;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
