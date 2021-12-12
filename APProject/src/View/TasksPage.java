package View;

import Model.Task;

    public class TasksPage extends Menu{
        public TasksPage(String name, Menu parent) {
            super(name, parent);
        }

        public void show() {
            super.show();
            System.out.println(showTasks());
            System.out.println("------------------------------------------");
            System.out.println("Use this formats : ");
            System.out.println("change title:edit task id <id> title <new title>");
            System.out.println("change description:edit task id <id> description <new description>");
            System.out.println("change priority:edit task id <id> priority <new priority>");
            System.out.println("change deadLine:edit task id <id> deadLine <new deadLine>");
            System.out.println("remove user(s) from task:edit task id <id> assignedUsers <username,username ..> remove");
            System.out.println("add user(s) to task: edit task id <id> assignedUsers <username,username ..> add");

        }

        public void execute() {
            String input = getInput() ;
            String[] inputParse = parseInput(input) ;
            if(inputParse[0].trim().equalsIgnoreCase("back")) {
                this.nextMenu = parent ;
                nextMenu.show();
                nextMenu.execute();
            }

            nextMenu.show();
            nextMenu.execute();
        }
    }

