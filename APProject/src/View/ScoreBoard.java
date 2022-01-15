package View;

import Controller.Controller;
import Controller.dto.ScoreBoardResponse;

public class ScoreBoard extends TeamMenu {
    private String team ;
    private Controller controller = new Controller();
    public ScoreBoard(String name, Menu parent, String team, String username, String role) {
        super(name, parent, username, role);
        this.team = team ;
    }

    public void show() {
        super.show();
        ScoreBoardResponse scoreBoardResponse = controller.showScoreBoard(team);
        if (scoreBoardResponse.isSuccessful()){
            scoreBoardResponse.getUsersScores().forEach((username, score) -> System.out.println(username +" : " + score));

        }else{
            System.out.println(scoreBoardResponse.getMessage());
        }
    }

    public void execute() {
        String input = getInput();
        if (!input.equals("back")) {
            System.out.println("invalid input");
        }
        this.nextMenu = parent ;
        nextMenu.show();
        nextMenu.execute();
    }
}
