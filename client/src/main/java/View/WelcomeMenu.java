package View;


import Controller.Controller;
import Controller.dto.LoginResponse;
import Model.Role;

public class WelcomeMenu extends Menu {
    public WelcomeMenu(String name, Menu parent) {
        super(name, parent);
    }
    private Controller controller = new Controller();

    public void show() {
        System.out.println("Welcome To Our Program!");
        System.out.println("I Hope You Enjoy");
        System.out.println("--------------" + this.name + "--------------");
        System.out.println("Use This Format To SignUp: " +
                "User create username <username> password <password> confirmPassword <Confirm password> email <email>");
        System.out.println("Use This Format To Login : " +
                "user login username <username> password <password>");
        System.out.println("Use Exit To Exit the Program");
    }
    public void execute() {
        String input = getInput();
        String[] inputParse = parseInput(input);
        if(input.equalsIgnoreCase("exit")) {
            System.exit(1);
        }
        else if (Regex.signUp(input)) {
            String outPut = controller.createUser(inputParse[3], inputParse[5], inputParse[7], inputParse[9]);
            System.out.println(outPut);
            nextMenu = this;
        }else if(Regex.logIn(input)) {
            LoginResponse loginResponse = controller.loginUser(inputParse[3] , inputParse[5]);
            System.out.println(loginResponse.getMessage());
            if(loginResponse.getMessage().equalsIgnoreCase("user logged in successfully!")) {
                if(loginResponse.getRole().equals(Role.SYSTEM_ADMINISTRATOR))
                    nextMenu = new AdminMainMenu("adminMainMenu" , this, loginResponse.getUsername()) ;
                else
                    nextMenu = new MainMenu("mainMenu" , this, loginResponse.getUsername(),
                            loginResponse.getRole()) ;
            }else{
                nextMenu = this ;
            }
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
