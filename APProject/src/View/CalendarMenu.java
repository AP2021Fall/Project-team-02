package View;

import Controller.*;
import Controller.dto.ShowCalenderResponse;

public class CalendarMenu extends Menu{
    public CalendarMenu(String name, Menu parent) {
        super(name, parent);
    }
    public  ShowCalenderResponse showcalenderresponse = new ShowCalenderResponse();

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
            System.out.println(showcalenderresponse.ShowCalenderResponse());
            this.nextMenu = parent ;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
