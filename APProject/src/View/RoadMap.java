package View;

import Controller.Controller;
public class RoadMap extends TeamMenu {
    public static Controller controller = new Controller();
    String team;

    public RoadMap(String name, Menu parent, String team) {
        super(name, parent);
        this.team = team;
    }

    public void show() {
        super.show();
        System.out.println(controller.showRoadMap(team));
    }

    public void execute() {
        String input = getInput();
        if(input.equals("back")) {
            this.nextMenu = parent;
        } else {
            System.out.println("invalid input");
            this.nextMenu = parent;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
