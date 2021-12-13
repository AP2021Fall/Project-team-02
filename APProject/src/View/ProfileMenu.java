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
            String output = changePassword1(inputParse[3],inputParse[5]) ;
            System.out.println(output);
            if(output.equalsIgnoreCase("wrong old password !")) {
                this.nextMenu = this ;
            }
            if(output.equalsIgnoreCase("Please type a New Password !")) {
                this.nextMenu = this ;
            }
            if(output.equalsIgnoreCase("Please Choose A strong Password (Containing at least 8 characters including " +
                    "1 digit and 1 Capital Letter)")) {
                this.nextMenu = this ;
            }
            else {
                this.nextMenu = new WelcomeMenu("welcomeMenu" , null) ;
            }
        }
        else if(Regex.changeUsername(input)) {
            System.out.println(changeUsername1(inputParse[3]));
            this.nextMenu = this ;
        }
        else if(Regex.profileShowTeams(input)) {
            System.out.println(showTeams());
            this.nextMenu = this ;
        }
        else if(Regex.profileShowTeam(input)) {
            System.out.println(showTeam(inputParse[2]));
            this.nextMenu = this ;
        }
        else if(Regex.showMyProfile(input)) {
            System.out.println(showMyProfile());
            this.nextMenu = this ;
        }
        else if((Regex.profileShowLogs(input))) {
            System.out.println(showLogs());
            this.nextMenu = this ;
        }
        else if(Regex.showNotifications(input)) {
            System.out.println(showNotification());
            this.nextMenu = this ;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}

