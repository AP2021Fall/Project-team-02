package View;

import Controller.Controller;
import Controller.dto.ShowCalenderResponse;

public class CalendarMenu extends Menu{
    private Controller controller = new Controller();
    private String username;
    public CalendarMenu(String name, Menu parent, String username) {
        super(name, parent);
        this.username = username;
    }

    public void show() {
        super.show();
        System.out.println("Use this to see your calendar:calendar show deadline");
    }

    public void execute() {
        String input = getInput() ;
        String[] inputParse = parseInput(input) ;
        if(inputParse[0].trim().equalsIgnoreCase("back")) {
            this.nextMenu = parent ;
            nextMenu.show();
            nextMenu.execute();
        }
        else if(Regex.calenderMenu(input)) {
            ShowCalenderResponse showCalenderResponse = controller.showCalender(username);
            if (showCalenderResponse.getMessage() != null){
                System.out.println(showCalenderResponse.getMessage());
            }else{
                showCalenderResponse.getDeadLines().forEach(System.out::println);
            }
            this.nextMenu = parent ;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
