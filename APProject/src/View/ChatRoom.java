package View;

import Controller.Controller;
import Controller.dto.ChatRoomResponse;

import java.util.List;

public class ChatRoom extends TeamMenu {
    private String team;
    private Controller controller = new Controller();
    public ChatRoom(String name, Menu parent, String teamName, String username, String role) {
        super(name, parent, username, role);
        this.team = teamName;
    }

    public void show() {
        super.show();
        System.out.println("Team Chatroom :");
        ChatRoomResponse chatRoomResponse = controller.showChatRoom(team);
        if (chatRoomResponse.isSuccessful()){
            List<String> messages = chatRoomResponse.getMessages();
            List<String> senders = chatRoomResponse.getSenders();

            for (int i = 0; i < senders.size(); i++) {
                System.out.println(senders.get(i) + " : " + messages.get(i) );
            }
        }else {
            System.out.println(chatRoomResponse.getMessage());
        }
        System.out.println("send your message to chatroom:send message <message>");
    }

    public void execute() {
        String input = getInput();
        while (!input.equalsIgnoreCase("back")) {
            String[] inputParse = parseInput(input);
            if (Regex.chatRoom(input)) {
                controller.sendMessage(getUsername(), team, inputParse[2]);
                this.nextMenu = this;
            } else {
                System.out.println("invalid input!");
                this.nextMenu = parent;
            }
            nextMenu.show();
            nextMenu.execute();
        }
    }
}
