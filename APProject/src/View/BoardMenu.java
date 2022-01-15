package View;

import Controller.Controller;
import Controller.dto.ShowBoardResponse;
import java.util.List;

public class BoardMenu extends TeamMenu{
    private String team;
    private String selectedBoard;

    private Controller controller = new Controller();

    public BoardMenu(String name, Menu parent , String team, String username, String role) {
        super(name, parent, username, role);
        this.team = team;
    }
    public void show() {
        super.show();
        System.out.println("Use to create board: " +
                "board new name <board name>");
        System.out.println("Use to remove board " +
                "board remove name <board name>");
        System.out.println("Use to select board " +
                "board select name <board name>");
        System.out.println("Use to deselect " +
                "board deselect");
        System.out.println("Board show name <board name>");
    }
    public void execute() {
        String input = getInput();
        String[] inputParse = parseInput(input) ;
        if(input.equalsIgnoreCase("back")) {
            this.nextMenu = parent ;
        }
        else if(Regex.boardMenuCreate(input)) {
            String response = controller.createBoard(getUsername(), team, inputParse[3]);
            if (response != null)
                System.out.println(response);
            this.nextMenu = this ;
        }
        else if(Regex.boardMenuRemove(input)) {
            String response = controller.removeBoard(getUsername(), team, inputParse[3]);
            if (response != null)
                System.out.println(response);
            this.nextMenu = this ;
        }
        else if(Regex.boardMenuSelect(input)) {
            selectedBoard = inputParse[3];
            this.nextMenu = this ;
        }
        else if(Regex.boardMenuDeSelect(input)) {
            selectedBoard = null;
            this.nextMenu = this ;
        }
        else if(Regex.boardShow(input)) {
            ShowBoardResponse response = controller.showBoard(getUsername(), team, inputParse[3]);
            if (response != null){
                response.print();
            }
            this.nextMenu = this ;
        }
        else if(Regex.boardShowDoneFail(input)) {
            List<String> response = controller.showDoneFailedTasks(getUsername(), team, inputParse[5], inputParse[2]);
            response.forEach(System.out::println);
            this.nextMenu = this ;
        }
        else if(Regex.boardCategory(input)) {
            String response = controller.addCategoryToBoard(getUsername(), team, inputParse[5], inputParse[3], null);
            if (response != null)
                System.out.println(response);
            this.nextMenu = this ;
        }
        else if(Regex.boardCategoryNext(input)) {
            String response = controller.moveTaskToNextCategory(getUsername(), team, inputParse[6], inputParse[4]);
            if (response != null)
                System.out.println(response);
            this.nextMenu = this ;
        }
        else if(Regex.boardForce(input)) {
            String response = controller.forceUpdateTaskCategory(getUsername(), team, inputParse[7], inputParse[3], inputParse[5]);
            if (response != null)
                System.out.println(response);
            this.nextMenu = this;
        }
        else if(Regex.boardAssign(input)) {
            try {
                int taskId = Integer.parseInt(inputParse[4]);
                String response = controller.assignTaskToMember(getUsername(), team, inputParse[2], inputParse[6], taskId);
                if (response != null)
                    System.out.println(response);
            }catch (Exception e){

            }
            this.nextMenu = this ;
        }
        else if(Regex.boardAddTask(input)) {
            try {
                int taskId = Integer.parseInt(inputParse[4]);
                String response = controller.addTaskToBoard(getUsername(), team, inputParse[4], taskId);
                if (response != null) {
                    System.out.println(response);
                }
            }catch (Exception e){

            }
            this.nextMenu = this ;
        }
        else if(Regex.boardDone(input)) {
            String response = controller.doneBoard(getUsername(), team, inputParse[3]);
            if (response != null)
                System.out.println(response);
            this.nextMenu = this ;
        }
        else if(Regex.boardCategoryColumn(input)) {
            try {
                int index = Integer.parseInt(inputParse[4]);
                String response = controller.updateCategoryColumn(getUsername(), team, inputParse[6], inputParse[2], index);
                if (response != null) {
                    System.out.println(response);
                }
            }catch (Exception e){

            }
            this.nextMenu = this ;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
