package View;

import Model.User;

public class ProfileMenu extends Menu{
    public ProfileMenu(String name, Menu parent) {
        super(name, parent);
    }
    public void show() {
        super.show();
        System.out.println("Use this format to change your password: " + "" +
                "profile change oldPassword <old password> newPassword <new password>");
        System.out.println("Use this format to change username : " +
                "profile change userName <username>");
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
        else if(Regex.changePassword(input)) {
            System.out.println(changePassword1(inputParse[3],inputParse[5]));
            // if
        }
        else if(Regex.changeUsername(input)) {
            System.out.println(changeUsername1(inputParse[3]));
            // if
        }
        else if(Regex.profileShowTeams(input)) {
            System.out.println(showTeams());
        }
        else if(Regex.profileShowTeam(input)) {
            System.out.println(showTeam(inputParse[2]));
            // if
        }
        else if(Regex.showMyProfile(input)) {
            System.out.println(showMyProfile());
        }
        else if((Regex.profileShowLogs(input))) {
            System.out.println(showLogs());
        }
        else if(Regex.showNotifications(input)) {
            System.out.println(showNotification());
        }
        nextMenu.show();
        nextMenu.execute();
    }
}

