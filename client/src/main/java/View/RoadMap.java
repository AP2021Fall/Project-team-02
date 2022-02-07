package View;

import Controller.Controller;
import Controller.dto.RoadMapResponse;

public class RoadMap extends TeamMenu {
    public  Controller controller = new Controller();
    String team;
    String username ;

    public RoadMap(String name, Menu parent, String team, String username, String role) {
        super(name, parent, username, role);
        this.team = team;
        this.username = username ;
    }

    public void show() {
        super.show();
        RoadMapResponse roadMapResponse = controller.showRoadMap(team);
        if (roadMapResponse.isSuccessful()){
            roadMapResponse.getTasksStatus().forEach((task, status) -> System.out.println(task + " " + status + "% done"));
        }else{
            System.out.println(roadMapResponse.getMessage());
        }
    }

    public void execute() {
        String input = getInput();
        if (!input.equals("back")) {
            System.out.println("invalid input");
        }
        this.nextMenu = parent;
        nextMenu.show();
        nextMenu.execute();
    }
}
