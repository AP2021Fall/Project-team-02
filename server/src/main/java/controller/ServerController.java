package controller;

import database.UserRepository;

import java.util.*;

public class ServerController {


    public static String process(String input){
        String result = " ";
        if(input.startsWith("login")){
            String[] parts = input.split(" ");
            String token = login(parts[1],parts[2]);
            result = token;
        }else if(input.startsWith("logout")){
            String[] parts = input.split(" ");
            logout(parts[1]);
        }
        return result;
    }

    public static String login(String username,String password){
        LoginDto userData = UserRepository.getUserData(username);
        if (userData != null){
            if (userData.getPassword().equals(password)){
                String token = UUID.randomUUID().toString().replace("-", "");
                UserRepository.update(username, token);
                return  token;
            }else {
                return "1";
            }
        }
       return "2";
    }

    public static void logout(String token){
        UserRepository.logout(token);
    }

}
