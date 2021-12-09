package View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    protected Scanner scanner = new Scanner(System.in) ;
    private String name;
    protected Menu parent;
    protected HashMap<Integer , Menu> subMenus ;
    static protected ArrayList<Menu> allMenus;
    static ArrayList<Menu> getAllMenus() {
        return allMenus ;
    }
    public Menu(String name , Menu parent) {
        setName(name) ;
        setParent(parent);
        allMenus.add(this) ;
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
        Menu nextMenu = this;
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
