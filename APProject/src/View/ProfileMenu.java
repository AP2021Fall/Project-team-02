package View;

import Model.User;
import Controller.Controller;
import View.Regex;

import static View.Regex.changePassword;
import static View.Regex.changeUsername;

public class ProfileMenu extends Menu{
    public static Controller controller = new Controller();
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
            nextMenu.show();
            nextMenu.execute();
        }
        else if(changePassword(input)) {
            String output = changePassword(inputParse[3],inputParse[5]) ;      //kollan nemidoonam fazesh chie :|
            System.out.println(output);
            if(output.equalsIgnoreCase("wrong old password !")) {
                this.nextMenu = this ;
                nextMenu.execute();
            }
            if(output.equalsIgnoreCase("Please type a New Password !")) {
                this.nextMenu = this ;
                nextMenu.execute();
            }
            if(output.equalsIgnoreCase("Please Choose A strong Password (Containing at least 8 characters including " +
                    "1 digit and 1 Capital Letter)")) {
                this.nextMenu = this ;
                nextMenu.execute();
            }
            else {
                this.nextMenu = new WelcomeMenu("welcomeMenu" , null) ;

            }
        }
        else if(changeUsername(input)) {
            System.out.println(changeUsername(inputParse[3]));
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if(Regex.profileShowTeams(input)) {
            System.out.println(controller.showTeams());
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if(Regex.profileShowTeam(input)) {
            System.out.println(Controller.showTeam(inputParse[2]));
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if(Regex.showMyProfile(input)) {
            System.out.println(controller.showProfile());    //aslan nadarim chizi be onvane show myprofile
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if((Regex.profileShowLogs(input))) {
            System.out.println(Controller.showLog());
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if(Regex.showNotification(input)) {
            System.out.println(Controller.showNotification());
            this.nextMenu = this ;
            nextMenu.execute();
        }
        nextMenu.show();
        nextMenu.execute();
    }

}

