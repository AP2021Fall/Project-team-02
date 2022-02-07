package Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientController {

    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;

    public static void initializeNetwork(){
        try{
            socket = new Socket("localhost",7777);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException x){
            x.printStackTrace();
        }
    }

    public static String login(String username,String password){
        try {
            dataOutputStream.writeUTF("login"+" "+username+" "+password);
            dataOutputStream.flush();
            return  dataInputStream.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
            return "3";
        }

    }

    public static void logout(String token){

        try {
            dataOutputStream.writeUTF("logout"+" "+token);
            dataOutputStream.flush();
            dataInputStream.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
