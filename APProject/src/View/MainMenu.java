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
        if(userLeader()) {   // Anita
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
        else if(Regex.mainMenuEnterMenu(input)) {

            if(inputParse[1].trim().equalsIgnoreCase("profile")) {
                this.nextMenu = new ProfileMenu("profileMenu" , this) ;
            }
            else if(inputParse[1].trim().equalsIgnoreCase("team")) {
                this.nextMenu = new TeamMenu("profileMenu" , this) ;
            }
            else if(inputParse[1].trim().equalsIgnoreCase("tasks")) {
                this.nextMenu = new TasksPage("taskMenu" , this) ;
            }
            else if(inputParse[1].trim().equalsIgnoreCase("calender")) {
                this.nextMenu = new CalendarMenu("calendarMenu" , this) ;
            }
            else if(inputParse[1].trim().equalsIgnoreCase("notification")) {
                this.nextMenu = new NotificationBar("notifications" , this) ;
            }
        }
        else if(Regex.leaderShowTeam(input)) {
            System.out.println(showTeams());
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.leaderShowTeams(input)) {
            String outPut = showTeam(inputParse[2]) ;
            System.out.println(outPut);
            this.nextMenu = this;
            nextMenu.execute();
        }
        else if(Regex.leaderCreateTeam(input)) {
            System.out.println(createTeam(inputParse[2]));
            this.nextMenu = this;
            nextMenu.execute();
        }
        else {
            System.out.println("Your input is not valid");
            this.nextMenu = this;
            nextMenu.execute();
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
