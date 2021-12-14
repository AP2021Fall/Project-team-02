package View;

public class TeamMenu extends Menu{
    public TeamMenu(String name, Menu parent) {
        super(name, parent);
    }


    public void show() {
        super.show();
        System.out.println(showUserTeams());
        System.out.println("Use to enter your teamMenu : "
        + "enter team <teamName>");
    }


    public void execute() {
        String input = getInput() ;
        String[] inputParse = parseInput(input) ;
        if(inputParse[0].trim().equalsIgnoreCase("back")) {
            this.nextMenu = parent ;
            nextMenu.show();
            nextMenu.execute();
        }
        else if(inputParse[1].trim().equalsIgnoreCase("team")) {
            String outPut = Team(inputParse[2]) ;
            System.out.println(outPut);
            show2(inputParse[2]);
            if(outPut.equalsIgnoreCase("this team not exist")) {
                this.nextMenu = this ;
                nextMenu.show();
                nextMenu.execute();
            }
        }
        else  {
            System.out.println("invalid input!");
            this.nextMenu = this ;
            nextMenu.show();
            nextMenu.execute();
        }}

        public void show2(String team) {
        System.out.println("Use to see team info :") ;
        System.out.println("board Menu");
        System.out.println("scoreboard show");
        System.out.println("roadmap show");
        System.out.println("chatroom show");
        System.out.println("show tasks");
        System.out.println("show task id <id>");
        if(userLeader(team)) {
            System.out.println("Admin menu: ");
            System.out.println("-------------------------------");
            System.out.println("sudo show all tasks");
            System.out.println("create task title <taskTitle> startTime <start time> deadline <deadline>");
            System.out.println("show members");
            System.out.println("add member username <username>");
            System.out.println("delete member username <username>");
            System.out.println("suspend member username <username>");
            System.out.println("promote username <username> rate <rate>");
            System.out.println("assign member task <taskId> username <username>");
            System.out.println("send notification <notification> username <username>");
            System.out.println("send notification <notification> teammember <teammember>");
        }
        execute2(team);
    }
        public void execute2(String team) {
        String input2 = getInput() ;
        String[] inputParse2 = parseInput(input2) ;
        if(inputParse2[0].trim().equalsIgnoreCase("back")) {
            this.nextMenu = parent ;
        }
        else if (inputParse2[0].trim().equalsIgnoreCase("scoreboard")) {
            this.nextMenu = new ScoreBoard("scoreBoard" , this , team) ;
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("roadmap")) {
            this.nextMenu = new RoadMap("roadMap" , this , team) ;
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("chatroom")) {
            this.nextMenu = new ChatRoom("chatroom" , this , team) ;
        }
        else if(inputParse2[1].trim().equalsIgnoreCase("tasks")) {
            this.nextMenu = new TasksPage("taskPage" , this ) ;
        }
        else if(inputParse2[1].trim().equalsIgnoreCase("task")) {
            String outPut = showTaskById(team , inputParse2[3]) ;
            System.out.println(outPut);
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("sudo")) {
            System.out.println(showAllTasks());
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("create")) {
            System.out.println(createTask(inputParse2[2] ,inputParse2[4] , inputParse2[6]));
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[1].trim().equalsIgnoreCase("members")) {
            System.out.println(showMembersTeam(team));
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("Add")) {
            System.out.println(addMember(inputParse2[3] , team));
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("delete")) {
            System.out.println(deleteMember(inputParse2[3] , team));
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("suspend")) {
            System.out.println(suspendMember(inputParse2[3] , team));
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("promote")) {
            System.out.println(promoteMember(inputParse2[2] , inputParse[4] ,team));
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("assign")) {
            System.out.println(assignMember(inputParse2[3] , inputParse2[5] , team));
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("notification") && inputParse2[3].trim().equalsIgnoreCase("username")) {
            System.out.println(sendNotificationToMember(inputParse2[2],inputParse2[4]));
            this.nextMenu = this ;
            this.execute2(team);
        }
        else if(inputParse2[0].trim().equalsIgnoreCase("notification") && inputParse2[3].trim().equalsIgnoreCase("teamName")) {
            System.out.println(sendNotificationToMember(inputParse2[2],inputParse2[4]));
            this.nextMenu = this ;
            this.execute2(team);
        }
        else {
            System.out.println("Invalid input try again");
            this.nextMenu = this ;
            this.execute2(team);
        }
        nextMenu.show();
        nextMenu.execute();
    }}
