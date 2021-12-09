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
                "User create <username> <password> <Confirm password> <email>");
        System.out.println("Use This Format To Login : " +
                "user login <username> <password>");
        System.out.println("Use Exit To Exit the Program");
    }
    public void execute() {
        String input = getInput();
        String[] inputParse = parseInput(input);
        if (inputParse[1].equalsIgnoreCase("create")) {
            System.out.println(create(username, password, password));
            // if on create to see its successfully done or not
        }
        else if(inputParse[1].equalsIgnoreCase("login")) {
            System.out.println(login(username, password, password));
            // if on create to see its successfully done or not
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
