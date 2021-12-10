package View;

import Model.Task;

    public class TasksPage extends Menu{
        public TasksPage(String name, Menu parent) {
            super(name, parent);
        }

        public void show() {
            super.show();
            System.out.println(showTasks());
            System.out.println(------------------------------------------);
        }

        public void execute() {

        }
    }

