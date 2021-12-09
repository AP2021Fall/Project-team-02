package View;

import Model.User;

public class ProfileMenu extends Menu{
    public ProfileMenu(String name, Menu parent) {
        super(name, parent);
    }
    public void show() {
        super.show();
        System.out.println("Use this format to change your password: " + "" +
                "profile change <old password> <new password>");
        System.out.println("Use this format to change username : " +
                "profile change <username>");
        System.out.println("Use this format to see teams: " +
                "profile showTeams");
        System.out.println("Use this format to see specific team :" +
                "profile showTeam <team name>");
        System.out.println("Use this format to see your profile : " +
                "profile show myProfile");
        System.out.println("Use this format to see your logs : " +
                "profile show logs");
        System.out.println("Use this format to see your notifications : " +
                "profile show notifications");
    }
    public void execute() {
        String input = getInput() ;
        String[] inputParse = parseInput(input);
        if(inputParse[0].trim().equalsIgnoreCase("back")) {
            this.nextMenu = parent ;
        }
        // ...
        else if(inputParse[1].trim().equalsIgnoreCase("showTeams")) {
            System.out.println(showTeams());
            this.nextMenu = parent ;
        }
        else if(inputParse[1].trim().equalsIgnoreCase("showTeam")) {
            System.out.println(showTeam(inputParse[2]));
            this.nextMenu = parent ;
        }
        else if(inputParse[2].trim().equalsIgnoreCase("myProfile")) {
            System.out.println(showMyProfile());
            this.nextMenu = parent ;
        }
        else if(inputParse[2].trim().equalsIgnoreCase("logs")) {
            System.out.println(showLog());
            this.nextMenu = parent ;
        }
        else if(inputParse[2].trim().equalsIgnoreCase("notifications")) {
            System.out.println(showNotif());
            this.nextMenu = parent ;
        }

        nextMenu.show();
        nextMenu.execute();
    }
}

