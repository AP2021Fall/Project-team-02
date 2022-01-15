package View;

import Controller.Controller;
import Model.Role;

public class TasksPage extends Menu {

    private String username;
    private String role;
    private Controller controller = new Controller();
    public TasksPage(String name, Menu parent, String username, String role) {
        super(name, parent);
        this.username = username;
        this.role = role;
    }

    public void show() {
        super.show();
        if (Role.TEAM_LEADER.equals(role)) {
            System.out.println("------------------------------------------");
            System.out.println("Use this formats : ");
            System.out.println("change title:edit task id <id> title <new title>");
            System.out.println("change description:edit task id <id> description <new description>");
            System.out.println("change priority:edit task id <id> priority <new priority>");
            System.out.println("change deadLine:edit task id <id> deadLine <new deadLine>");
            System.out.println("remove user(s) from task:edit task id <id> assignedUsers <username,username ..> remove");
            System.out.println("add user(s) to task: edit task id <id> assignedUsers <username,username ..> add");
        }
    }

    public void execute() {
        String input = getInput();
        String[] inputParse = parseInput(input);
        if (inputParse[0].trim().equalsIgnoreCase("back")) {
            this.nextMenu = parent;
            nextMenu.show();
            nextMenu.execute();
        } else if (Regex.changeTitle(input)) {
            try {
                Integer taskId = Integer.parseInt(inputParse[3]);
                String response = controller.editTaskTitle(username, taskId, inputParse[5]);
                System.out.println(response);
            }catch (Exception e){

            }
            this.nextMenu = this;
            nextMenu.execute();
        } else if (Regex.changeDescription(input)) {
            try {
                Integer taskId = Integer.parseInt(inputParse[3]);
                String response = controller.editTaskDescription(username, taskId, inputParse[5]);
                System.out.println(response);
            }catch (Exception e){

            }
            this.nextMenu = this;
            nextMenu.execute();
        } else if (Regex.changePriority(input)) {
            try {
                Integer taskId = Integer.parseInt(inputParse[3]);
                String response = controller.editTaskPriority(username, taskId, inputParse[5]);
                System.out.println(response);
            }catch (Exception e){

            }
            this.nextMenu = this;
            nextMenu.execute();
        } else if (Regex.changeDeadline(input)) {
            try {
                Integer taskId = Integer.parseInt(inputParse[3]);
                String response = controller.editTaskDeadline(username, taskId, inputParse[5]);
                System.out.println(response);
            }catch (Exception e){

            }
            this.nextMenu = this;
            nextMenu.execute();
        } else if (Regex.removeUser(input)) {

            try {
                Integer taskId = Integer.parseInt(inputParse[3]);
                String response = controller.removeUserFromTask(username, taskId, inputParse[5]);
                System.out.println(response);
            }catch (Exception e){

            }
            this.nextMenu = this;
            nextMenu.execute();
        } else if (Regex.addUser(input)) {
            try {
                Integer taskId = Integer.parseInt(inputParse[3]);
                String response = controller.addUserToTask(username, taskId, inputParse[5]);
                System.out.println(response);
            }catch (Exception e){

            }
            this.nextMenu = this;
            nextMenu.execute();
        }
        nextMenu.show();
        nextMenu.execute();
    }
}

