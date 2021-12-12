package View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    static Pattern pattern ;
    static Matcher matcher ;
    public static boolean adminShowProfile(String input) {
        pattern = Pattern.compile("^(show profile username .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
     }
    public static boolean adminBanUser(String input) {
        pattern = Pattern.compile("^(ban user user .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean adminChangeRole(String input) {
        pattern = Pattern.compile("^(change role user .+ role .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean adminSendNotificationAll(String input) {
        pattern = Pattern.compile("^(send notification .+ all)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean adminSendNotification(String input) {
        pattern = Pattern.compile("^(send notification .+ user .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean adminNotificationTeam(String input) {
        pattern = Pattern.compile("^(send notification .+ team .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean adminShowScoreBoard(String input) {
        pattern = Pattern.compile("^(show scoreboard team .+)$", Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean adminShowPendingTeam(String input) {
        pattern = Pattern.compile("^(show pendingTeams)$", Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean adminAccept(String input) {
        pattern = Pattern.compile("^(accept teams .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean adminReject(String input) {
        pattern = Pattern.compile("^(reject teams .+)$", Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }

}
