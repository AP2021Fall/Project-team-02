import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static controller.ServerController.process;

public class Main {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while (true){
                Socket socket = serverSocket.accept();

                new Thread(()->{
                    try{
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while(true){
                            String input = dataInputStream.readUTF();
                            String result = process(input);
                            if(result == null || result == ""){
                                result = " ";
                            }
                            dataOutputStream.writeUTF(result);
                        }
                    }catch (Exception x){
                        x.printStackTrace();
                    }

                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
