package View;

public class ScoreBoard extends TeamMenu {
    String team ;
    public ScoreBoard(String name, Menu parent , String team) {
        super(name, parent);
        this.team = team ;
    }

    public void show() {
        super.show();
        System.out.println(showScoreBoard(team));
    }

    public void execute() {
        String input = getInput();
        if(input.equals("back")) {
            this.nextMenu = parent ;
        }
        else {
            System.out.println("invalid input");
            this.nextMenu = parent ;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
