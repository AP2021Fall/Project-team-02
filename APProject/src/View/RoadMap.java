package View;

public class RoadMap extends TeamMenu {
    String team;

    public RoadMap(String name, Menu parent, String team) {
        super(name, parent);
        this.team = team;
    }

    public void show() {
        super.show();
        System.out.println(showRoadMap(team));
    }

    public void execute() {
        super.execute();
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
