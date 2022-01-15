package View;

import Controller.Controller;
import Model.Message;

import java.util.List;

public class NotificationBar extends Menu{

    private String username;
    private  Controller controller = new Controller();
    public NotificationBar(String name, Menu parent, String username) {
        super(name, parent);
        this.username = username;

    }

    public void show() {
        super.show();
        List<Message> messages = controller.showNotification(username);
        for (Message message : messages) {
            System.out.println(message);

        }
    }

    public void execute() {
        String input = getInput() ;
        if(input.equals("back")) {
            this.nextMenu = parent ;
            nextMenu.show();
            nextMenu.execute();
        }
        else {
            System.out.println("invalid input!");
            this.nextMenu = this ;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
