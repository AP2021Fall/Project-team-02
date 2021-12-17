package Controller;

import Controller.dto.*;
import Model.*;
import Repository.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Controller {
    public static BoardRepository boardRepository = new BoardRepository();
    public static CategoryRepository categoryRepository = new CategoryRepository();
    public static CommentRepository commentRepository = new CommentRepository();
    public static LogRepository logRepository = new LogRepository();
    public static MessageRepository messageRepository = new MessageRepository();
    public static TaskRepository taskRepository = new TaskRepository();
    public static TeamRepository teamRepository = new TeamRepository();
    public static UserRepository userRepository = new UserRepository();

    public ShowCalenderResponse showCalender(String username, String teamName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getTeams().stream().anyMatch(t -> t.getName().equals(teamName))) {
                Team team = teamRepository.findByTeamName(teamName);
                List<String> sortedDeadlines = user.getTasks().stream().filter(t -> t.teamName().equals(teamName))
                        .filter(t -> !(t.isFailed() || t.isDone() || t.taskTimeFinished()))
                        .map(Task::getDeadLine)
                        .sorted(String::compareTo)
                        .collect(Collectors.toList());

                if(sortedDeadlines.isEmpty())
                    return new ShowCalenderResponse("no deadlines");

                List<String> responseDeadLines = new ArrayList<>();
                for (String deadline : sortedDeadlines) {
                    int days = Task.getDeadlineDays(deadline);
                    StringBuilder stringBuilder = new StringBuilder();
                    if(days < 4)
                        stringBuilder.append("***");
                    else if (days <= 10)
                        stringBuilder.append("**");
                    else
                        stringBuilder.append("*");

                    int dateIndex = deadline.indexOf("|");
                    if(dateIndex <0)
                        dateIndex = 10;

                    stringBuilder.append(deadline.substring(0, dateIndex));
                    stringBuilder.append("__remaining days:");
                    stringBuilder.append(days);
                    responseDeadLines.add(stringBuilder.toString());

                }

                return new ShowCalenderResponse(responseDeadLines, teamName);

            }
        }
        return null;

    }


    //    public void getInput(String input) {
//
//
//    }
//    public String showDeadlines(String input) {
//
//    }
//    public int BoardFailure(String BoardName) {
//
//    }
//    public int BoardCompletion(String BoardName) {
//
//    }
//    public String showTasks(String BoardName) {
//
//    }
    public ShowBoardResponse showBoard(String username, String teamName, String boardName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getTeams().stream().anyMatch(t -> t.getName().equals(teamName))) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();

                    ShowBoardResponse response = new ShowBoardResponse(board.getName(), team.getLeader().getUsername());

                    List<Task> tasks = board.getCategories().stream().flatMap(c -> c.getTasks().stream()).collect(Collectors.toList());;

                    long doneCount = tasks.stream().filter(Task::isDone).count();
                    long failedCount = tasks.stream().filter(t -> (t.isFailed() || t.taskTimeFinished())).count();

                    if (tasks.size() != 0) {
                        response.setCompletionPercentage((int) (doneCount / tasks.size()) * 100);
                        response.setFailedPercentage((int) (failedCount / tasks.size()) * 100);
                    }
                    return response;
                }
            }
        }
        return null;
    }

    public String openTask(String username, String teamName, String boardName, String taskTitle, String deadLine, String assignUsername, String categoryName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    if (board.getActive()) {
                        Task task = board.getCategories().stream().flatMap(c -> c.getTasks().stream()).filter(t -> t.getTitle().equals(taskTitle)).findAny().orElse(null);
                        if (task == null)
                            return "Invalid task";

                        if(!task.isFailed())
                            return " This task is not in failed section";

                        Category category = null;
                        if(categoryName != null) {

                            Optional<Category> categoryOptional = board.getCategories().stream().filter(c -> c.getName().equals(categoryName)).findAny();
                            if (categoryOptional.isPresent()) {
                                category = categoryOptional.get();
                            } else {
                                return "Invalid category";
                            }
                        }else {
                            category = board.getCategories().get(0);
                        }

                        Category oldCategory = task.getCategory();
                        if (oldCategory != null) {
                            oldCategory.getTasks().remove(task);
                        }
                        task.setCategory(category);
                        category.getTasks().add(task);

                        if(assignUsername != null) {
                            User assignUser = team.findByUsername(assignUsername);
                            if(assignUser == null)
                                return "Invalid teammember";
                            task.getUsers().add(assignUser);
                            assignUser.getTasks().add(task);
                        }

                        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD|HH:mm");
                        dateFormat.setLenient(false);
                        try {
                            Date date = dateFormat.parse(deadLine);
                            if (task.getCreationDate().compareTo(deadLine) <= 0 && date.compareTo(new Date()) >= 0) {
                                task.setDeadLine(deadLine);
                            }else{
                                return  "invalid deadline";
                            }

                        } catch (Exception e) {
                            return  "invalid deadline";
                        }

                    }
                    return null;
                } else {
                    return "There is no board with this name";
                }
            } else
                return "You do not have the permission to do this action!";
        } else
            return "user not found";
    }


    public List<String> showDoneFailedTasks(String username, String teamName, String boardName, String categoryName) {
        //category name should be done or failed
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Team team = teamRepository.findByTeamName(teamName);
            Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
            if (teamBoard.isPresent()) {
                Board board = teamBoard.get();
                if (board.getActive()) {
                    Category category = board.getCategories().stream().filter(c -> c.getName().equals(categoryName)).findAny().orElse(null);
                    if(category != null){
                        return category.getTasks().stream().map(Task::getTitle).collect(Collectors.toList());
                    }
                }
            }
        }

        return null;
    }


//    public String showDoneTasks(String BoardName) {
//
//    }
//    public void changeUserScore() {
//
//    }
//    public void taskDone(int taskId) {
//
//    }
//    public void taskFailed(int taskId) {
//
//    }
//    public String showTasks (String category , String BoardName) {
//
//    }

    public String addTaskToBoard(String username, String teamName, String boardName, Integer taskId) throws ParseException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    if (board.getActive()) {
                        Task task = taskRepository.findById(taskId);
                        if (task == null)
                            return "Invalid task id!";

                        if (task.getCategory() != null && task.getCategory().getBoard().getId().equals(board.getId()))
                            return "This task has already been added to this board";

                        if (task.taskTimeFinished())
                            return "The deadline of this task has already passed";

                        if (task.getUsers().isEmpty())
                            return "Please assign this task to someone first";

                        Category fistCategory = board.getCategories().get(0);
                        task.setCategory(fistCategory);
                        fistCategory.getTasks().add(task);
                    }
                    return null;
                } else {
                    return "There is no board with this name";
                }
            } else
                return "You do not have the permission to do this action!";
        } else
            return "user not found";
    }

//    public void checkCategoryExists(String category) {
//
//    }
//    public void forceTaskTo(String category, String title , String BoardName) {
//
//    }
//    public boolean isTaskFinished(int taskId) {
//
//    }
//    public boolean userInTeam(String activeTeam , String userName ) {
//
//    }
//    public boolean taskOwner(int taskId) {
//
//    }
//    public boolean checkTaskDeadLine(LocalDateTime dealLine) {
//
//    }
//    public boolean taskExistsInBoard(int TaskId) {
//
//    }
//    public void addTaskToBoard(int taskId ,String boardName) {
//
//    }
    public String doneBoard(String username, String teamName, String boardName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    board.setActive(true);
                } else {
                    return "There is no board with this name";
                }
            }
            return "You do not have the permission to do this action!";
        }
        return "user not found";
    }

    public String addCategoryToBoard(String username, String teamName, String boardName, String categoryName, Integer index) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    Optional<Category> categoryOptional = board.getCategories().stream().filter(c -> c.getName().equals(categoryName)).findAny();
                    if (categoryOptional.isPresent())
                        return "The name is already taken for a category!";

                    Category category = new Category(categoryName, board);
                    categoryRepository.createCategory(category);
                    if (index == null)
                        board.getCategories().add(category);
                    else {
                        if (board.getCategories().size() - 1 < index)
                            return "wrong column!";
                        board.getCategories().add(index, category);
                    }
                } else {
                    return "There is no board with this name";
                }
            }
            return "You do not have the permission to do this action!";
        }
        return "user not found";
    }

    public String moveTaskToNextCategory(String username, String teamName, String boardName, String taskTitle) throws ParseException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Team team = teamRepository.findByTeamName(teamName);
            Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
            if (teamBoard.isPresent()) {
                Board board = teamBoard.get();
                if (board.getActive()) {
                    Task task = board.getCategories().stream().flatMap(c -> c.getTasks().stream()).filter(t -> t.getTitle().equals(taskTitle)).findAny().orElse(null);
                    if (task == null)
                        return "Invalid task!";

                    Optional<User> userOption = task.getUsers().stream().filter(u -> u.getUsername().equals(username)).findAny();
                    if(userOption.isPresent() || user.getLeader()) {
                        Category category = task.getCategory();
                        int categoryIndex = board.getCategories().indexOf(category);
                        if(categoryIndex < board.getCategories().size() - 2) {
                            category.getTasks().remove(task);
                            Category nextCategory = board.getCategories().get(categoryIndex + 1);
                            nextCategory.getTasks().add(task);
                            task.setCategory(nextCategory);
                            Integer userScore = team.getUsersScore().get(user.getId());
                            if (task.isDone()) {
                                userScore += 10;
                            }else if(task.isFailed()){
                                userScore -= 5;
                            }else if(task.taskTimeFinished()){
                                userScore -= 5;
                                Category failedCategory = board.getCategories().stream().filter(c -> c.getName().equals("failed")).findAny().orElse(null);
                                if(failedCategory != null) {
                                    Category oldCategory = task.getCategory();
                                    oldCategory.getTasks().remove(task);
                                    task.setCategory(failedCategory);

                                }
                            }
                            team.getUsersScore().put(user.getId(), userScore);
                        }
                    }else {
                        return "This task is not assigned to you";
                    }
                }
                return null;
            } else {
                return "There is no board with this name";
            }
        } else
            return "user not found";
    }

    public String forceUpdateTaskCategory(String username, String teamName, String boardName, String categoryName, String taskTitle) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    if (board.getActive()) {
                        Task task = board.getCategories().stream().flatMap(c -> c.getTasks().stream()).filter(t -> t.getTitle().equals(taskTitle)).findAny().orElse(null);
                        if (task == null)
                            return "There is no task with given information";

                        Optional<Category> categoryOptional = board.getCategories().stream().filter(c -> c.getName().equals(categoryName)).findAny();
                        if (categoryOptional.isPresent()) {
                            Category category = categoryOptional.get();

                            Category oldCategory = task.getCategory();
                            if (oldCategory != null) {
                                oldCategory.getTasks().remove(task);
                            }
                            task.setCategory(category);
                            category.getTasks().add(task);
                        } else {
                            return "Invalid category";
                        }
                    }
                    return null;
                } else {
                    return "There is no board with this name";
                }
            } else
                return "You do not have the permission to do this action!";
        } else
            return "user not found";
    }

    public String assignTaskToMember(String username, String teamName, String memberUsername, String boardName, Integer taskId) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    if (board.getActive()) {
                        Task task = taskRepository.findById(taskId);
                        if (task == null)
                            return "Invalid task id!";

                        Optional<User> memberOption = team.getMembers().stream().filter(u -> u.getUsername().equals(memberUsername)).findAny();
                        if (memberOption.isPresent()) {
                            if (task.isDone())
                                return "This task has already finished";
                            User member = memberOption.get();
                            task.getUsers().add(member);
                            member.getTasks().add(task);
                        } else {
                            return "Invalid teammate";
                        }
                    }
                    return null;
                } else {
                    return "There is no board with this name";
                }
            } else
                return "You do not have the permission to do this action!";
        } else
            return "user not found";
    }

    //    public void removeActiveBoard(String activeBoard) {
//
//    }
//    public void selectBoard(String activeBoard) {
//
//    }
    public String removeBoard(String username, String teamName, String boardName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    team.getBoards().remove(board);
                    boardRepository.remove(board);
                    categoryRepository.removeByBoard(board.getId());
                    List<Integer> taskIds = taskRepository.removeByBoard(board.getId());
                    commentRepository.removeByTaskId(taskIds);
                } else {
                    return "There is no board with this name";
                }
            }
            return "You do not have the permission to do this action!";
        }
        return "user not found";
    }

    //    public boolean checkBoardExists(String boardName) {
//
//    }
    public String createBoard(String username, String teamName, String boardName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent())
                    return "There is already a board with this name";

                Board board = new Board(boardName, team);
                team.getBoards().add(board);
                boardRepository.createBoard(board);
                return null;
            }
            return "You do not have the permission to do this action!";
        }
        return "user not found";
    }

    //    public boolean rejectTeams(String teamNames) {
//
//    }
//    public boolean checkIsPending(String teamNames) {
//
//    }
//    public boolean acceptTeams(String teamNames) {
//
//    }
//    public String showPendingTeams() {
//
//    }
//    public void changeRole(String name, UserRole role) {
//
//    }
//    public void banUser(String name) {
//
//    }
//    public boolean usernameExist(String name) {
//
//    }
//    public boolean checkLoggedIn(String name) {
//
//    }
//    public boolean checkAdmin(String name) {
//
//    }
//    public void showProfile(String name) {
//
//    }
//    public void sendNotificationToAll(String notification) {
//
//    }
//    public void sendNotificationToMember(String name, String notification) {
//
//    }
//    public void sendNotificationToTeam(Team team, String notification) {
//
//    }
//    public boolean assignMember(Team team, int taskId, String userName) {
//
//    }
//    public boolean addMember(Team team, String teamMember) {
//
//    }
//    public String showMember(Team team) {
//
//    }
//    public boolean deadlineValid(LocalDateTime deadline) {
//
//    }
//    public boolean startTimeValid(LocalDateTime startTime) {
//
//    }
//    public boolean taskExists(String task) {
//
//    }
//    public Task createTask(String taskTitle, LocalDateTime startTime, LocalDateTime deadline) {
//
//    }
//    public String showAllTasks(Team activeTeam) {
//
//    }
//    public boolean nameValid(String team) {
//
//    }
//    public Team createTeam(String team){
//
//    }
//    public String showTeam(String team){
//
//    }
//    public String showTeams(TeamLeader teamLeader) {
//
//    }
//    public String showTaskById(Team team, int id) {
//
//    }
    public ShowTaskResponse showTask(String username, int taskId) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Task task = taskRepository.findById(taskId);
            if (task.getCategory() != null) {
                if (user.getTeams().contains(task.getCategory().getBoard().getTeam())) {
                    return new ShowTaskResponse(task);
                } else {
                    return new ShowTaskResponse("403");
                }
            }
            return new ShowTaskResponse("task not found");

        }
        return null;
    }


    public ShowTaskListResponse showTasks(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            List<Task> tasks = team.getBoards().stream().flatMap(b -> b.getCategories().stream())
                    .flatMap(c -> c.getTasks().stream())
                    .sorted(Comparator.comparing(Task::getDeadLine))
                    .collect(Collectors.toList());

            if (tasks.size() == 0)
                return new ShowTaskListResponse("no task yet");
            return new ShowTaskListResponse(tasks);
        } else {
            return new ShowTaskListResponse("team not found");
        }
    }


    public void sendMessage(String username, String teamName, String message) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Team team = teamRepository.findByTeamName(teamName);
            if (team != null) {
                Message newMessage = new Message(message, MessageType.TEAM, user.getId());
                team.getMessages().add(newMessage);
            }
        }
    }


    public ChatRoomResponse showChatRoom(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            List<String> messages = new ArrayList<>();
            List<String> usernames = new ArrayList<>();

            for (Message message : team.getMessages()) {
                messages.add(message.getTxt());
                usernames.add(userRepository.getById(message.getSenderId()).getUsername());
            }

            if (messages.size() == 0)
                return new ChatRoomResponse("no message yet");

            return new ChatRoomResponse(usernames, messages);
        } else {
            return new ChatRoomResponse("team not found");
        }
    }

    public RoadMapResponse showRoadMap(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            Map<String, Integer> response = team.getBoards().stream().flatMap(board -> board.getCategories().stream())
                    .flatMap(category -> category.getTasks().stream()).collect(Collectors.toMap(t -> t.getTitle(), t -> t.getId()));

            if (response.size() == 0) {
                return new RoadMapResponse(false, "no task yet");
            }
            return new RoadMapResponse(true, response);

        } else {
            return new RoadMapResponse(false, "team not found");
        }
    }


    public ScoreBoardResponse showScoreBoard(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            Map<String, Integer> usersScore = new HashMap<>();
            for (Integer userId : team.getUsersScore().keySet()) {
                String name = userRepository.getById(userId).getUsername();
                usersScore.put(name, team.getUsersScore().get(userId));
            }
            return new ScoreBoardResponse(true, usersScore);

        } else {
            return new ScoreBoardResponse(false, "team not found");
        }

    }

    public List<String> showTeamMenu(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getTeams().stream().map(Team::getName).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public String removeUserFromTask(String username, int taskId, String targetUsername) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Task task = taskRepository.findById(taskId);
            if (task.getCategory() != null) {
                if (user.getTeams().contains(task.getCategory().getBoard().getTeam()) && user.getLeader()) {
                    User targetUser = userRepository.findByUsername(targetUsername);
                    if (targetUser != null) {
                        task.getUsers().remove(targetUser);
                        targetUser.getTasks().remove(task);
                        return "User " + targetUsername + " removed successfully!";
                    }
                    return "There is not any user with this username " + targetUsername + "  in list!";
                }
                return "You Don’t Have Access To Do This Action!";
            }
            return "task not found";
        }
        return "user not found";
    }

    public String addUserToTask(String username, int taskId, String targetUsername) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Task task = taskRepository.findById(taskId);
            if (task.getCategory() != null) {
                if (user.getTeams().contains(task.getCategory().getBoard().getTeam()) && user.getLeader()) {
                    User targetUser = userRepository.findByUsername(targetUsername);
                    if (targetUser != null) {
                        if (!task.getUsers().contains(targetUser)) {
                            task.getUsers().add(targetUser);
                            targetUser.getTasks().add(task);
                        }
                        return "User " + targetUsername + " added successfully!";
                    }
                    return "There is not any user with this username " + targetUsername + "  in list!";
                }
                return "You Don’t Have Access To Do This Action!";
            }
            return "task not found";
        }
        return "user not found";
    }


    public String editTaskDeadline(String username, int taskId, String newDate) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Task task = taskRepository.findById(taskId);
            if (task.getCategory() != null) {
                if (user.getTeams().contains(task.getCategory().getBoard().getTeam()) && user.getLeader()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD|HH:mm");
                    dateFormat.setLenient(false);
                    try {
                        Date date = dateFormat.parse(newDate);
                        if (task.getCreationDate().compareTo(newDate) <= 0) {
                            task.setDeadLine(newDate);
                            return "Deadline updated successfully!";
                        }

                    } catch (Exception e) {

                    }
                    return "New deadline is invalid!";
                }
                return "You Don’t Have Access To Do This Action!";

            }
            return "task not found";
        }
        return "user not found";
    }

    public String editTaskPriority(String username, int taskId, String priority) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Task task = taskRepository.findById(taskId);
            if (task.getCategory() != null) {
                if (user.getTeams().contains(task.getCategory().getBoard().getTeam()) && user.getLeader()) {
                    task.setPriority(priority);
                    return "Priority updated successfully!";
                }
                return "You Don’t Have Access To Do This Action!";
            }
            return "task not found";
        }
        return "user not found";
    }

    public String editTaskDescription(String username, int taskId, String description) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Task task = taskRepository.findById(taskId);
            if (task.getCategory() != null) {
                if (user.getTeams().contains(task.getCategory().getBoard().getTeam()) && user.getLeader()) {
                    task.setDescription(description);
                    return "Description updated successfully!";
                }
                return "You Don’t Have Access To Do This Action!";
            }
            return "task not found";
        }
        return "user not found";
    }

    public String editTaskTitle(String username, int taskId, String title) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Task task = taskRepository.findById(taskId);
            if (task.getCategory() != null) {
                if (user.getTeams().contains(task.getCategory().getBoard().getTeam()) && user.getLeader()) {
                    task.setTitle(title);
                    return "Title updated successfully!";
                }
                return "You Don’t Have Access To Do This Action!";
            }
            return "task not found";
        }
        return "user not found";
    }

    public List<Message> showNotification(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return messageRepository.findByReceiverIdAndType(user.getId(), MessageType.TEAM_LEADER);
        }
        return null;
    }

    public List<Log> showLog(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return logRepository.getLogsByUserId(user.getId());
        }
        return null;
    }

    public ShowProfileResponse showProfile(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.profile();
        }
        return null;
    }

    public List<String> showTeam(String username, String teamName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName)).findAny().orElse(null);
            if (team != null) {
                List<String> response = new ArrayList<>();
                response.add(team.getName());
                response.add(team.getLeader().getUsername());
                team.getMembers().stream().map(User::getUsername).sorted(String::compareTo).forEach(name -> response.add(username));
                return response;
            }
        }
        return null;
    }

    public List<String> showTeams(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getRole().equals(Role.TEAM_MEMBER) || user.getRole().equals(Role.TEAM_LEADER)) {
                return user.getTeams().stream().sorted(Comparator.comparingInt(Team::getId)).map(Team::getName).collect(Collectors.toList());
            }
        }
        return null;
    }

    public ChangeUsernameResponse changeUsername(String username, String newUsername) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (newUsername.length() < 4)
                return new ChangeUsernameResponse(false, null, "Your new username must include at least 4 characters!");

            if (userRepository.findByUsername(newUsername) != null)
                return new ChangeUsernameResponse(false, null, "username already taken!");

            if (checkUserNameFormat(newUsername)) {
                if (user.getUsername().equals(newUsername))
                    return new ChangeUsernameResponse(false, null, "you already have this username!");
                user.setUsername(newUsername);
                return new ChangeUsernameResponse(true, newUsername, null);

            } else
                return new ChangeUsernameResponse(false, null, "New username contains Special Characters! Please remove them and try again");
        }
        return null;

    }


    public ChangePasswordResponse changePassword(String username , String oldPassword ,
                                                 String newPassword ) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            if(user.getPassword().equals(oldPassword)){
                if(oldPassword.equals(newPassword)){
                    return new ChangePasswordResponse(false, "Please type a New Password !");
                }
                if (checkPasswordFormat(newPassword)) {
                    user.setPassword(newPassword);
                    return new ChangePasswordResponse(true, "");  //??????????
                }
                return new ChangePasswordResponse(false, "Please Choose A strong Password (Containing at least 8 characters including 1 digit and 1 Capital Letter)");

            }else{
                return new ChangePasswordResponse(false, "wrong old password!");
            }
        }
        return null;
    }


    public String createUser(String username ,String password , String confirmPassword, String email) {
        if(checkUsernameExists(username)) {
            if(password.equals(confirmPassword)) {
                if(checkEmailExists(email)) {
                    if(checkUserNameFormat(username)) {
                        if (checkEmailFormat(email)) {
                            if (checkPasswordFormat(password)) {
                                User user = new User(username, password, email);
                                userRepository.createUser(user);
                                return "user created successfully!";
                            } else {
                                return "Please Choose A strong Password (Containing at least 8 characters including 1 digit and 1 Capital Letter)";
                            }

                        } else {
                            return "Email address is invalid!";
                        }
                    }else
                        return "Your new username must include at least 4 characters!";

                }else {
                    return "User with this email already exists!";
                }
            }else {
                return "Your passwords are not the same!";
            }
        }else{
            return "user with username " + username + " already exists!";
        }
    }


    public LoginResponse loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            if(user.getPassword().equals(password)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Log log = new Log(dateFormat.format(new Date()), user.getId());
                logRepository.createLog(log);
                return new LoginResponse(user.getId(),user.getUsername(), user.getRole(), "user logged in successfully!");
            }else {
                return new LoginResponse("Username and password didn't match!");
            }
        }else{
            return new LoginResponse("There is not any user with username: " + username +"!");
        }
    }


    public boolean checkPasswordFormat(String password) {
        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
        return passwordPattern.matcher(password).matches() && password.length() >= 8;
    }

    public boolean checkEmailFormat(String email) {
        Pattern gmailPattern = Pattern.compile("^[A-Za-z0-9+.]+@gmail.com$");
        Pattern yahooPattern = Pattern.compile("^[A-Za-z0-9+.]+@yahoo.com$");
        return yahooPattern.matcher(email).matches() || gmailPattern.matcher(email).matches();
    }

    public boolean checkUserNameFormat(String username) {
        Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9-]*$");
        return usernamePattern.matcher(username).matches();
    }

    public boolean checkUsernameExists(String username) {
        return userRepository.existUsername(username);
    }

    public boolean checkEmailExists(String email) {
        return userRepository.existEmail(email);
    }
//    public boolean checkPassword(String username ,String password) {
//
//    }
//    public boolean isTeamLeader(User user) {
//        return false ;
//    }
public String adminRejectTeams(String adminUsername, List<String> pendingTeamsName) {
    if(adminUsername.equals("admin")){
        List<Team> pendingTeams = teamRepository.findAll().stream().sorted((t1, t2) -> t2.getId().compareTo(t1.getId()))
                .filter(t -> !t.isActive())
                .collect(Collectors.toList());

        if(!pendingTeams.stream().map(Team::getName).collect(Collectors.toList()).containsAll(pendingTeamsName)){
            return "Some teams are not in pending status! Try again";
        }

        pendingTeams.stream().filter(t -> pendingTeamsName.contains(t.getName())).forEach(t -> {
            teamRepository.remove(t);
            t.getLeader().getTeams().remove(t);
            for (User member : t.getMembers()) {
                member.getTeams().remove(t);
            }
        });
        return "teams rejected successfully!";
    }
    return "You do not have access to this section";

}
    public String adminAcceptTeams(String adminUsername, List<String> pendingTeamsName) {
        if(adminUsername.equals("admin")){
            List<Team> pendingTeams = teamRepository.findAll().stream().sorted((t1, t2) -> t2.getId().compareTo(t1.getId()))
                    .filter(t -> !t.isActive())
                    .collect(Collectors.toList());

            if(!pendingTeams.stream().map(Team::getName).collect(Collectors.toList()).containsAll(pendingTeamsName)){
                return "Some teams are not in pending status! Try again";
            }

            pendingTeams.stream().filter(t -> pendingTeamsName.contains(t.getName())).forEach(t -> t.setActive(true));
            return "teams accepted successfully!";
        }
        return "You do not have access to this section";

    }
}
