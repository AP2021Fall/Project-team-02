package View;

public class ChatRoom extends TeamMenu{
    private String team ;
    public ChatRoom(String name, Menu parent , String teamName) {
        super(name, parent);
        this.team = teamName ;
    }

    public void show() {
        super.show();
        System.out.println("Team Chatroom :");
        System.out.println(getChatRoomName(team));
        System.out.println("send your message to chatroom:send message <message>");
    }

    public void execute() {
        String input = getInput() ;
        while(!input.equalsIgnoreCase("back")) {
            String[] inputParse = parseInput(input) ;
            if(inputParse[0].trim().equalsIgnoreCase("send")) {
                sendMessage(inputParse[2]) ;
            }
            else {
                System.out.println("wrong input!");
            }
            input = getInput() ;
        }
        this.nextMenu = parent ;
        nextMenu.show();
        nextMenu.execute();
    }
}
