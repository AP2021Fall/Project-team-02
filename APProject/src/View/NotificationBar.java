package View;

import Controller.*;
public class NotificationBar extends Menu{
    public NotificationBar(String name, Menu parent) {
        super(name, parent);
    }

    public void show() {
        super.show();
        System.out.println(Controller.showNotification());
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
