package View;

public class MainMenu extends Menu{
    public MainMenu(String name, Menu parent) {
        super(name, parent);
    }

    public void show() {
        super.show();
        System.out.println("Use <enter menu <Menu Name>> to select a menu");

    }

    public void execute() {

    }
}
