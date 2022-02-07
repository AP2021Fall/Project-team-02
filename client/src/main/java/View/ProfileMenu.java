package View;

import Controller.Controller;
import Controller.dto.ChangePasswordResponse;
import Controller.dto.ChangeUsernameResponse;
import Controller.dto.ShowProfileResponse;
import Model.Log;
import Model.Message;

import java.util.List;

public class ProfileMenu extends Menu{
    private String username;
    private Controller controller = new Controller();
    public ProfileMenu(String name, Menu parent, String username) {
        super(name, parent);
        this.username = username;
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
        else if(Regex.changePassword(input)) {
            ChangePasswordResponse changePasswordResponse = controller.changePassword(username, inputParse[3], inputParse[5]);
            if (changePasswordResponse.isSuccessful()){
                this.nextMenu = new WelcomeMenu("welcomeMenu" , null) ;
            }else {
                System.out.println(changePasswordResponse.getMessage());
                this.nextMenu = this ;
                nextMenu.execute();
            }
        }
        else if(Regex.changeUsername(input)) {
            ChangeUsernameResponse changeUsernameResponse = controller.changeUsername(username, inputParse[3]);
            if (changeUsernameResponse.isSuccessful()){
                username = changeUsernameResponse.getNewUsername();
            }else{
                System.out.println(changeUsernameResponse.getMessage());
            }
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if(Regex.profileShowTeams(input)) {
            controller.showTeams(username);
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if(Regex.profileShowTeam(input)) {
            List<String> teamInfo = controller.showTeam(username, inputParse[2]);
            if (teamInfo != null)
                teamInfo.forEach(System.out::println);
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if(Regex.showMyProfile(input)) {
            ShowProfileResponse showProfileResponse = controller.showProfile(username);
            System.out.println(showProfileResponse.toString());
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if((Regex.profileShowLogs(input))) {
            List<Log> logs = controller.showLog(username);
            if (logs != null)
                logs.forEach(System.out::println);
            this.nextMenu = this ;
            nextMenu.execute();
        }
        else if(Regex.showNotification(input)) {
            List<Message> messages = controller.showNotification(username);
            if (messages != null)
                messages.stream().map(Message::getTxt).forEach(System.out::println);
            this.nextMenu = this ;
            nextMenu.execute();
        }
        nextMenu.show();
        nextMenu.execute();
    }

}

