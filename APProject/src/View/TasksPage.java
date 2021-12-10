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
            else if(inputParse[4].trim().equalsIgnoreCase("title")) {
                System.out.println(changeTitle(inputParse[3] , inputParse[5]));
                this.nextMenu = this ;
            }
            else if(inputParse[4].trim().equalsIgnoreCase("description")) {
                System.out.println(changeDiscription(inputParse[3] , inputParse[5]));
                this.nextMenu = this ;
            }
            else if(inputParse[4].trim().equalsIgnoreCase("priority")) {
                System.out.println(changePriority(inputParse[3] , inputParse[5]));
            }
            else if(inputParse[4].trim().equalsIgnoreCase("deadLine")) {
                System.out.println(changeDeadLine(inputParse[3] , inputParse[5]));
            }
            else if(inputParse.length == 7 && inputParse[6].trim().equalsIgnoreCase("add")) {
                String[] userNames = parseInputUsernames(inputParse[5]) ;
                System.out.println(addToTask(inputParse[3] , userNames));
                this.nextMenu = this ;
            }
            else if(inputParse.length == 7 && inputParse[6].trim().equalsIgnoreCase("remove")) {
                String[] userNames = parseInputUsernames(inputParse[5]) ;
                System.out.println(removeFromTask(inputParse[3] , userNames));
                this.nextMenu = this ;
            }
            nextMenu.show();
            nextMenu.execute();
        }
    }

