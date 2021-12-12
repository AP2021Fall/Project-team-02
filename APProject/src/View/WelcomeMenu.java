package View;


public class WelcomeMenu extends Menu {
    public WelcomeMenu(String name, Menu parent) {
        super(name, parent);
    }

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
        if (Regex.SignUp(input)) {
            System.out.println(createUser(inputParse[3], inputParse[5], inputParse[7] , inputParse[9]));
            // if on create to see its successfully done or not
        }
        else if(Regex.logIn(input)) {
            System.out.println(login(inputParse[3] , inputParse[5]));
            // if on create to see its successfully done or not
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
