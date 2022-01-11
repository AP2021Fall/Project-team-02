package View;

import Controller.Controller;

public class ChatRoom extends TeamMenu {
    private String team;

    public ChatRoom(String name, Menu parent, String teamName) {
        super(name, parent);
        this.team = teamName;
    }

    public void show() {
        super.show();
        System.out.println("Team Chatroom :");
        System.out.println(getChatRoomName(team));  // i don't know what is it
        System.out.println("send your message to chatroom:send message <message>");
    }

    public void execute() {
        String input = getInput();
        while (!input.equalsIgnoreCase("back")) {
            String[] inputParse = parseInput(input);
            if (Regex.chatRoom(input)) {
              Controller.sendMessage(inputParse[2]) ;
              this.nextMenu = this ;
            }
            else {
                System.out.println("invalid input!");
                this.nextMenu = parent ;
            }
            nextMenu.show();
            nextMenu.execute();
        }
    }
}
