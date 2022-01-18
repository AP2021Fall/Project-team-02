package View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    protected Menu nextMenu = this;
    protected Scanner scanner = new Scanner(System.in) ;
    protected String name;
    protected Menu parent;
    protected HashMap<Integer , Menu> subMenus ;
    public Menu(String name , Menu parent) {
        setName(name) ;
        setParent(parent);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public Menu getParent() {
        return parent ;
    }

    public HashMap<Integer,Menu> getSubMenus() {
        return subMenus ;
    }

    protected String getInput() {
        return scanner.nextLine() ;
    }
    protected String[] parseInput(String input) {
        return input.split("\\s+");
    }
    protected String[] parseInputUsernames(String input) {
        return input.split(",") ;
    }
    public void setNextMenu(Menu nextMenu) {
        this.nextMenu = nextMenu;
    }
    public void show(){
        System.out.println("--------" + this.name + "--------");
        if (this.parent != null) {
            System.out.println("Use Back to Previous Menu");
        }
        else {
            System.out.println("Use Exit To Exit the Program");
        }
    }
    public void execute() {
        String input = getInput() ;
        String[] inputParse = parseInput(input) ;
        if (inputParse[0].equalsIgnoreCase("exit")) {
            System.exit(1);
        }
        else if (inputParse[0].equalsIgnoreCase("back")) {
            nextMenu = parent ;
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
