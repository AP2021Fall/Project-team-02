package View;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private String name;
    protected Menu parent;
    protected ArrayList<Menu> subMenus;
    protected ArrayList<Menu> allMenus;
    public Scanner scanner;

    public Menu() {
    }

    public String getName() {
        return name;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public void setSubMenus(ArrayList<Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    public void show(){
        System.out.println("salam");
    }
    public void execute(){

    }
}
