package View;

public class BoardMenu extends TeamMenu{
    String team ;
    public BoardMenu(String name, Menu parent , String team) {
        super(name, parent);
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
            createBoard(inputParse[3]) ;
            this.nextMenu = this ;
        }
        else if(Regex.boardMenuRemove(input)) {
            System.out.println(menuBoardRemove(inputParse[3]));
            this.nextMenu = this ;
        }
        else if(Regex.boardMenuSelect(input)) {
            System.out.println(menuBoardSelect(inputParse[3])) ;
            this.nextMenu = this ;
        }
        else if(Regex.boardMenuDeSelect(input)) {
            System.out.println(boardDeSelect());
            this.nextMenu = this ;
        }
        else if(Regex.boardShow(input)) {
            System.out.println(showBoard(inputParse[3]));
            this.nextMenu = this ;
        }
        else if(Regex.boardShowDoneFail(input)) {
            if(inputParse.length == 12) {
                System.out.println(boardShowDoneFail1(inputParse[3] , inputParse[5] , inputParse[7] , inputParse[9] , inputParse[11]));
            }
            else if(inputParse[4].equalsIgnoreCase("assign")) {
                System.out.println(boardShowDoneFail2(inputParse[3] , inputParse[5] , inputParse[7] , inputParse[9]));
            }
            else if(inputParse[6].equalsIgnoreCase("category")) {
                System.out.println(boardShowDoneFail3(inputParse[3] , inputParse[7] , inputParse[9] , inputParse[5]));
            }
            else {
                System.out.println(boardShowDoneFail4(inputParse[3] , inputParse[5] , inputParse[7]));
            }
            this.nextMenu = this ;
        }
        else if(Regex.boardShowDoneFail(input)) {
            System.out.println(boardShowDoneOrFail(inputParse[2] , inputParse[5]));
            this.nextMenu = this ;
        }
        else if(Regex.boardCategory(input)) {
            System.out.println(boardShowCategory(inputParse[3] , inputParse[5]));
            this.nextMenu = this ;
        }
        else if(Regex.boardCategoryNext(input)) {
            System.out.println(boardShowCategoryNext(inputParse[4] , inputParse[6]));
            this.nextMenu = this ;
        }
        else if(Regex.boardForce(input)) {
            System.out.println(boardForce(inputParse[3] , inputParse[5] , inputParse[7]));
            this.nextMenu = this;
        }
        else if(Regex.boardAssign(input)) {
            System.out.println(boardAssign(inputParse[2] ,inputParse[4] ,inputParse[6]));
            this.nextMenu = this ;
        }
        else if(Regex.boardAddTask(input)) {
            System.out.println(boardAdd(inputParse[2] , inputParse[4]));
            this.nextMenu = this ;
        }
        else if(Regex.boardDone(input)) {
            System.out.println(boardDone(inputParse[3]));
            this.nextMenu = this ;
        }
        else if(Regex.boardCategoryColumn(input)) {
            System.out.println(boardCategoryColumn(inputParse[2] , inputParse[4] , inputParse[6]));
            this.nextMenu = this ;
        }
        else if(Regex.boardCategory(input)) {
            System.out.println(boardCategory(inputParse[3] , inputParse[5]));
            this.nextMenu = this ;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
