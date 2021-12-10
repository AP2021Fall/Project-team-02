package View;

public class BoardMenu extends TeamMenu{
    public BoardMenu(String name, Menu parent) {
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
        
    }

    public void execute() {

    }
}
