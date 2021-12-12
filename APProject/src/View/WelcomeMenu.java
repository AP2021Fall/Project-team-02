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
            String outPut = createUser(inputParse[3], inputParse[5], inputParse[7] , inputParse[9]) ;
            System.out.println(outPut) ;
            if(outPut.equalsIgnoreCase("user created successfully!")) {
                nextMenu = new MainMenu("mainMenu" , this) ;
            }
            else if(Regex.errorUsernameExists(outPut)) {
                nextMenu = this ;
            }
            else if(outPut.equalsIgnoreCase("Your passwords are not the same!")) {
                nextMenu = this ;
            }
            else if(outPut.equalsIgnoreCase("User with this email already exists!")) {
                nextMenu = this ;
            }
            else if(outPut.equalsIgnoreCase("Email address is invalid!")) {
                nextMenu = this ;
            }
        }
        else if(Regex.logIn(input)) {
            String outPut = login(inputParse[3] , inputParse[5]);
            System.out.println(outPut);
            if(outPut.equalsIgnoreCase("user logged in successfully!")) {
                nextMenu = new MainMenu("mainMenu" , this) ;
            }
            if(Regex.errorUsernameExists(outPut)) {
                nextMenu = this ;
            }
            if(outPut.equalsIgnoreCase("Username and password didn't match!")) {
                nextMenu = this ;
            }
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
