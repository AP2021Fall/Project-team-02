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
    public static boolean mainMenuEnterMenu(String input) {
        pattern = Pattern.compile("^(enter menu .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean leaderShowTeams(String input) {
        pattern = Pattern.compile("^(show team)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean leaderShowTeam(String input) {
        pattern = Pattern.compile("^(show team .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean leaderCreateTeam(String input) {
        pattern = Pattern.compile("^(create team .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean changePassword(String input) {
        pattern = Pattern.compile("^(profile change oldPassowrd .+ newPassowrd .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input);
        return matcher.find();
    }
    public static boolean changeUsername (String input) {
        pattern = Pattern.compile("^(profile change userName .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input);
        return matcher.find();
    }
    public static boolean profileShowTeams(String input) {
        pattern = Pattern.compile("^(profile showTeams)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean profileShowTeam(String input) {
        pattern = Pattern.compile("^(profile showTeam .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean showMyProfile(String input) {
        pattern = Pattern.compile("^(profile show myProfile)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean profileShowLogs(String input) {
        pattern = Pattern.compile("^(profile show logs)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean showNotifications(String input) {
        pattern = Pattern.compile("^(profile show notifications)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean chatRoom(String input) {
        pattern = Pattern.compile("^(send message .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean calenderMenu(String input) {
        pattern = Pattern.compile("^(calendar show deadline)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean changeTitle(String input) {
        pattern = Pattern.compile("^(edit task id <id> newtitle)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean changeDescription(String input) {
        pattern = Pattern.compile("^(edit task id .+ description .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean changePriority(String input) {
        pattern = Pattern.compile("^(edit task id .+ priority .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean changeDeadline(String input) {
        pattern = Pattern.compile("^(edit task id .+ deadLine .+)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean removeUser(String input) {
        pattern = Pattern.compile("^(edit task id .+ assignedUsers .+ remove)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean addUser(String input) {
        pattern = Pattern.compile("^(edit task id .+ assignedUsers .+ add)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean signUp(String input) {
        pattern = Pattern.compile("^(User create username .+ password .+ confirmPassword .+ email .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean logIn(String input) {
        pattern = Pattern.compile("^(user login username .+ password .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean errorUsernameExists(String input) {
        pattern = Pattern.compile("^(user with username .+ already exists!)$" , Pattern.CASE_INSENSITIVE) ;
        matcher = pattern.matcher(input) ;
        return matcher.find();
    }
    public static boolean errorUsernameNotExists(String input) {
        pattern = Pattern.compile("^(There is not any user with username: .+!)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardMenuCreate(String input) {
        pattern = Pattern.compile("^(board new name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardMenuRemove(String input) {
        pattern = Pattern.compile("^(board remove name .+)$ , Pattern.CASE_INSENSITIVE");
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardMenuSelect(String input) {
        pattern = Pattern.compile("^(board select name .+)$ , Pattern.CASE_INSENSITIVE");
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardMenuDeSelect(String input) {
        pattern = Pattern.compile("^(board deselect)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardShow(String input) {
        pattern = Pattern.compile("^(Board show name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardAddTask(String input) {
        pattern = Pattern.compile("^(Board add .+ name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardAssign(String input) {
        pattern = Pattern.compile("^(board assign .+ task .+ name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardForce(String input) {
        pattern = Pattern.compile("^(board force category .+ task .+ name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardDoneFail(String input) {
        pattern = Pattern.compile("^(board show .+ name board .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardCategory(String input) {
        pattern = Pattern.compile("^(board new category .+ name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardCategoryColumn(String input) {
        pattern = Pattern.compile("^(board category .+ columan .+ name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardDone(String input) {
        pattern = Pattern.compile("^(board done name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardShowDoneFail(String input) {
        pattern = Pattern.compile("^(board show (done|fail) name board .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean changeTaskToNotFail(String input) {
        pattern = Pattern.compile("^(board open task .+( assign .+)? deadline .+( category .+)?" +
                " name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
    public static boolean boardCategoryNext(String input) {
        pattern = Pattern.compile("^(board category next task .+ name .+)$" , Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(input) ;
        return matcher.find() ;
    }
}
