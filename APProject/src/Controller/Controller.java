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

    public ShowBoardResponse showBoard(String username, String teamName, String boardName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getTeams().stream().anyMatch(t -> t.getName().equals(teamName))) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();

                    ShowBoardResponse response = new ShowBoardResponse(board.getName(), team.getLeader().getUsername());

                    List<Task> tasks = board.getCategories().stream().flatMap(c -> c.getTasks().stream()).collect(Collectors.toList());

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

    public String addTaskToBoard(String username, String teamName, String boardName, Integer taskId) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (Boolean.TRUE.equals(user.getLeader())) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    if (Boolean.TRUE.equals(board.getActive())) {
                        Task task = taskRepository.findById(taskId);
                        if (task == null)
                            return "Invalid task id!";

                        if (task.getCategory() != null && task.getCategory().getBoard().getId().equals(board.getId()))
                            return "This task has already been added to this board";

                        if (task.taskTimeFinished())
                            return "The deadline of this task has already passed";

                        if (task.getUsers().isEmpty())
                            return "Please assign this task to someone first";

                        Category firstCategory = board.getCategories().get(0);
                        task.setCategory(firstCategory);
                        firstCategory.getTasks().add(task);

                        taskRepository.update(task);
                        categoryRepository.update(firstCategory);
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

    public String doneBoard(String username, String teamName, String boardName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (Boolean.TRUE.equals(user.getLeader())) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    board.setActive(true);

                    boardRepository.update(board);
                    return null;
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
            if (Boolean.TRUE.equals(user.getLeader())) {
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

                    boardRepository.update(board);
                    return null;
                } else {
                    return "There is no board with this name";
                }
            }
            return "You do not have the permission to do this action!";
        }
        return "user not found";
    }

    public String updateCategoryColumn(String username, String teamName, String boardName, String categoryName, Integer index) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (Boolean.TRUE.equals(user.getLeader())) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    Optional<Category> categoryOptional = board.getCategories().stream().filter(c -> c.getName().equals(categoryName)).findAny();
                    if (!categoryOptional.isPresent())
                        return "There is no category with this name!";

                    Category category = categoryOptional.get();

                    if (board.getCategories().size() - 1 < index)
                        return "wrong column!";
                    board.getCategories().remove(category);
                    board.getCategories().add(index, category);

                    boardRepository.update(board);
                    return null;
                } else {
                    return "There is no board with this name";
                }
            }
            return "You do not have the permission to do this action!";
        }
        return "user not found";
    }

    public String moveTaskToNextCategory(String username, String teamName, String boardName, String taskTitle) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Team team = teamRepository.findByTeamName(teamName);
            Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
            if (teamBoard.isPresent()) {
                Board board = teamBoard.get();
                if (Boolean.TRUE.equals(board.getActive())) {
                    Task task = board.getCategories().stream().flatMap(c -> c.getTasks().stream()).filter(t -> t.getTitle().equals(taskTitle)).findAny().orElse(null);
                    if (task == null)
                        return "Invalid task!";

                    Optional<User> userOption = task.getUsers().stream().filter(u -> u.getUsername().equals(username)).findAny();
                    if (userOption.isPresent() || Boolean.TRUE.equals(user.getLeader())) {
                        Category category = task.getCategory();
                        int categoryIndex = board.getCategories().indexOf(category);
                        if (categoryIndex < board.getCategories().size() - 2) {
                            category.getTasks().remove(task);
                            Category nextCategory = board.getCategories().get(categoryIndex + 1);
                            nextCategory.getTasks().add(task);
                            task.setCategory(nextCategory);

                            taskRepository.update(task);
                            categoryRepository.update(category);
                            categoryRepository.update(nextCategory);

                            Integer userScore = team.getUsersScore().get(user.getId());
                            if (task.isDone()) {
                                userScore += 10;
                            } else if (task.isFailed()) {
                                userScore -= 5;
                            } else if (task.taskTimeFinished()) {
                                userScore -= 5;
                                Category failedCategory = board.getCategories().stream().filter(c -> c.getName().equals("failed")).findAny().orElse(null);
                                if (failedCategory != null) {
                                    Category oldCategory = task.getCategory();
                                    oldCategory.getTasks().remove(task);
                                    task.setCategory(failedCategory);

                                    taskRepository.update(task);
                                    categoryRepository.update(oldCategory);
                                    categoryRepository.update(failedCategory);

                                }
                            }
                            team.getUsersScore().put(user.getId(), userScore);

                            teamRepository.update(team);
                            return null;
                        }
                    } else {
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
            if (Boolean.TRUE.equals(user.getLeader())) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    if (Boolean.TRUE.equals(board.getActive())) {
                        Task task = board.getCategories().stream().flatMap(c -> c.getTasks().stream()).filter(t -> t.getTitle().equals(taskTitle)).findAny().orElse(null);
                        if (task == null)
                            return "There is no task with given information";

                        Optional<Category> categoryOptional = board.getCategories().stream().filter(c -> c.getName().equals(categoryName)).findAny();
                        if (categoryOptional.isPresent()) {
                            Category category = categoryOptional.get();

                            Category oldCategory = task.getCategory();
                            if (oldCategory != null) {
                                oldCategory.getTasks().remove(task);

                                categoryRepository.update(oldCategory);
                            }
                            task.setCategory(category);
                            category.getTasks().add(task);

                            categoryRepository.update(category);
                            taskRepository.update(task);
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
            if (Boolean.TRUE.equals(user.getLeader())) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    if (Boolean.TRUE.equals(board.getActive())) {
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

                            taskRepository.update(task);
                            userRepository.update(member);

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

    public String removeBoard(String username, String teamName, String boardName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (Boolean.TRUE.equals(user.getLeader())){
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent()) {
                    Board board = teamBoard.get();
                    team.getBoards().remove(board);

                    boardRepository.remove(board);
                    categoryRepository.removeByBoard(board.getId());
                    List<Integer> taskIds = taskRepository.removeByBoard(board.getId());
                    commentRepository.removeByTaskId(taskIds);
                    return null;
                } else {
                    return "There is no board with this name";
                }
            }
            return "You do not have the permission to do this action!";
        }
        return "user not found";
    }

    public String createBoard(String username, String teamName, String boardName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (Boolean.TRUE.equals(user.getLeader())) {
                Team team = teamRepository.findByTeamName(teamName);
                Optional<Board> teamBoard = team.getBoards().stream().filter(b -> b.getName().equals(boardName)).findAny();
                if (teamBoard.isPresent())
                    return "There is already a board with this name";

                Board board = new Board(boardName, team);
                team.getBoards().add(board);
                boardRepository.createBoard(board);

                teamRepository.update(team);
                return null;
            }
            return "You do not have the permission to do this action!";
        }
        return "user not found";
    }

    public ShowTaskResponse showTask(String username, int taskId) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Task task = taskRepository.findById(taskId);
            if (task != null) {
                if (user.getTeams().contains(task.getCategory().getBoard().getTeam())) {
                    return new ShowTaskResponse(task);
                } else {
                    return new ShowTaskResponse("403");
                }
            }
            return new ShowTaskResponse("task not found");

        }
        return new ShowTaskResponse("user not found!");
    }


    public static ShowTaskListResponse showTasks(String teamName) {
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
                messageRepository.createMessage(newMessage);
                team.getMessages().add(newMessage);

                teamRepository.update(team);
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
                    .flatMap(category -> category.getTasks().stream()).collect(Collectors.toMap(Task::getTitle, Task::getId));

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

                        taskRepository.update(task);
                        userRepository.update(targetUser);

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

                            taskRepository.update(task);
                            userRepository.update(targetUser);
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
                            taskRepository.update(task);
                            return "Deadline updated successfully!";
                        }

                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
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
                    taskRepository.update(task);
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
                    taskRepository.update(task);
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
                    taskRepository.update(task);
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
            return messageRepository.findByReceiverId(user.getId());
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
                userRepository.update(user);
                return new ChangeUsernameResponse(true, newUsername, null);

            } else
                return new ChangeUsernameResponse(false, null, "New username contains Special Characters! Please remove them and try again");
        }
        return null;

    }


    public ChangePasswordResponse changePassword(String username, String oldPassword,
                                                 String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(oldPassword)) {
                if (oldPassword.equals(newPassword)) {
                    return new ChangePasswordResponse(false, "Please type a New Password !");
                }
                if (checkPasswordFormat(newPassword)) {
                    user.setPassword(newPassword);
                    userRepository.update(user);
                    return new ChangePasswordResponse(true, "");
                }
                return new ChangePasswordResponse(false, "Please Choose A strong Password (Containing at least 8 characters including 1 digit and 1 Capital Letter)");

            } else {
                return new ChangePasswordResponse(false, "wrong old password!");
            }
        }
        return new ChangePasswordResponse(false, "user not found!");
    }


    public String createUser(String username, String password, String confirmPassword, String email) {
        if (!checkUsernameExists(username)) {
            if (password.equals(confirmPassword)) {
                if (!checkEmailExists(email)) {
                    if (username.length() >= 4)
                        if (checkUserNameFormat(username)) {
                            if (checkEmailFormat(email)) {
                                if (checkPasswordFormat(password)) {
                                    User user = new User(username, password, email, Role.TEAM_MEMBER, false);
                                    userRepository.createUser(user);
                                    return "user created successfully!";
                                } else {
                                    return "Please Choose A strong Password (Containing at least 8 characters including 1 digit and 1 Capital Letter)";
                                }

                            } else {
                                return "Email address is invalid!";
                            }
                        } else
                            return "New username contains Special Characters! Please remove them and try again";
                    else {
                        return "Your new username must include at least 4 characters!";
                    }
                } else {
                    return "User with this email already exists!";
                }
            } else {
                return "Your passwords are not the same!";
            }
        } else {
            return "user with username " + username + " already exists!";
        }
    }

    public static boolean checkPasswordFormat(String password) {
        Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+");
//
        return passwordPattern.matcher(password).matches() && password.length() >= 8;
    }

    public boolean checkEmailFormat(String email) {
        Pattern gmailPattern = Pattern.compile("[A-Za-z]+[0-9\\.]*@gmail.com");
        Pattern yahooPattern = Pattern.compile("[A-Za-z]+[0-9\\.]*@yahoo.com");
        return yahooPattern.matcher(email).matches() || gmailPattern.matcher(email).matches();
    }

    public boolean checkUserNameFormat(String username) {
        Pattern usernamePattern = Pattern.compile("[a-zA-Z0-9_]+");
        return usernamePattern.matcher(username).matches();
    }

    public boolean checkUsernameExists(String username) {
        return userRepository.existUsername(username);
    }

    public boolean checkEmailExists(String email) {
        return userRepository.existEmail(email);
    }


    public String adminRejectTeams(String adminUsername, List<String> pendingTeamsName) {
        if (adminUsername.equals("admin")) {
            List<Team> pendingTeams = teamRepository.findAll().stream().sorted((t1, t2) -> t2.getId().compareTo(t1.getId()))
                    .filter(t -> !t.isActive())
                    .collect(Collectors.toList());

            if (!pendingTeams.stream().map(Team::getName).collect(Collectors.toList()).containsAll(pendingTeamsName)) {
                return "Some teams are not in pending status! Try again";
            }

            pendingTeams.stream().filter(t -> pendingTeamsName.contains(t.getName())).forEach(t -> {
                teamRepository.remove(t);
                t.getLeader().getTeams().remove(t);

                userRepository.update(t.getLeader());
                for (User member : t.getMembers()) {
                    member.getTeams().remove(t);
                    userRepository.update(member);
                }
            });
            return "teams rejected successfully!";
        }
        return "You do not have access to this section";

    }

    public String adminAcceptTeams(String adminUsername, List<String> pendingTeamsName) {
        if (adminUsername.equals("admin")) {
            List<Team> pendingTeams = teamRepository.findAll().stream().sorted((t1, t2) -> t2.getId().compareTo(t1.getId()))
                    .filter(t -> !t.isActive())
                    .collect(Collectors.toList());

            if (!pendingTeams.stream().map(Team::getName).collect(Collectors.toList()).containsAll(pendingTeamsName)) {
                return "Some teams are not in pending status! Try again";
            }

            pendingTeams.stream().filter(t -> pendingTeamsName.contains(t.getName())).forEach(t -> {
                t.setActive(true);
                teamRepository.update(t);
            });
            return "teams accepted successfully!";
        }
        return "You do not have access to this section";

    }

    public List<String> adminShowPendingTeams(String adminUsername) {
        if (adminUsername.equals("admin")) {
            return teamRepository.findAll().stream().sorted((t1, t2) -> t2.getId().compareTo(t1.getId()))
                    .filter(t -> !t.isActive())
                    .map(Team::getName)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public String adminSendMessageToTeam(String adminUsername, String teamName, String messageTxt) {
        if (adminUsername.equals("admin")) {
            Team team = teamRepository.findByTeamName(teamName);
            if (team == null)
                return "No team exists with this name!";

            Message message = new Message(messageTxt, MessageType.ADMIN, 0);
            messageRepository.createMessage(message);
            team.getMessages().add(message);

            teamRepository.update(team);
            return "message sent successfully!";

        }
        return "You do not have access to this section";
    }

    public String adminSendMessageToUser(String adminUsername, String memberUsername, String messageTxt) {
        if (adminUsername.equals("admin")) {
            User user = userRepository.findByUsername(memberUsername);
            if (user == null)
                return "There is no user with this username";

            Message message = new Message(messageTxt, MessageType.ADMIN, 0, user.getId());
            messageRepository.createMessage(message);
            user.getMessages().add(message);

            userRepository.update(user);
            return "message sent successfully!";
        }

        return "You do not have access to this section";
    }

    public String adminSendMessageToAllUsers(String adminUsername, String messageTxt) {
        if (adminUsername.equals("admin")) {
            Message message = new Message(messageTxt, MessageType.ADMIN, 0);
            messageRepository.createMessage(message);

            userRepository.findAll().forEach(u -> {
                u.getMessages().add(message);
                userRepository.update(u);
            });
            return "message sent successfully!";
        }
        return "You do not have access to this section";
    }

    public String adminChangeUserRole(String adminUsername, String memberUsername, String role) {
        if (adminUsername.equals("admin")) {
            User user = userRepository.findByUsername(memberUsername);

            if (user == null)
                return "There is no user with this username";

            user.setRole(role);

            if (role.equals(Role.TEAM_LEADER)) {
                user.setLeader(true);
                for (Team teamMemberTeam : user.getTeams()) {
                    teamMemberTeam.getMembers().remove(user);
                    teamRepository.update(teamMemberTeam);
                }

                user.setTeams(new ArrayList<>());
            } else
                user.setLeader(false);

            userRepository.update(user);

            return "user role change successfully!";
        }
        return "You do not have access to this section";
    }

    public String adminBanUser(String adminUsername, String memberUsername) {
        if (adminUsername.equals("admin")) {
            User user = userRepository.findByUsername(memberUsername);

            if (user == null)
                return "There is no user with this username";

            userRepository.deleteUser(user);
            for (Team team : user.getTeams()) {
                team.getMembers().remove(user);
                teamRepository.update(team);
            }

            for (Task task : user.getTasks()) {
                task.getUsers().remove(user);
                taskRepository.update(task);
            }

            return "user baned successfully!";
        }
        return "You do not have access to this section";
    }

    public AdminShowProfileResponse adminShowProfile(String adminUsername, String memberUsername) {
        if (adminUsername.equals("admin")) {
            User user = userRepository.findByUsername(memberUsername);

            if (user == null)
                return new AdminShowProfileResponse("There is no user with this username");

            return new AdminShowProfileResponse(user);

        }
        return new AdminShowProfileResponse("You do not have access to this section");
    }

    public String sendMessageToTeam(String username, String teamName, String messageTxt) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getLeader()) {
            Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName)).findAny().orElse(null);
            if (team != null) {

                Message message = new Message(messageTxt, MessageType.TEAM_LEADER, user.getId());
                messageRepository.createMessage(message);
                team.getMessages().add(message);

                teamRepository.update(team);
                messageRepository.update(message);
                return "message sent successfully!";
            }

            return "No team exists with this name!";
        }

        return null;
    }

    public String sendMessageToMember(String username, String teamName, String memberName, String messageTxt) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getLeader()) {
            Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName)).findAny().orElse(null);
            if (team != null) {
                User member = team.findByUsername(memberName);
                if (member == null)
                    return "No user exists with this username!";

                Message message = new Message(messageTxt, MessageType.TEAM_LEADER, user.getId(), member.getId());
                messageRepository.createMessage(message);
                member.getMessages().add(message);

                userRepository.update(member);
                messageRepository.update(message);
                return "message sent successfully!";
            }

            return "No team exists with this name!";
        }

        return null;
    }

    public String assignTaskToMember(String username, String teamName, String memberName, int taskId) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getLeader()) {
            Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName)).findAny().orElse(null);
            if (team != null) {
                User member = team.findByUsername(memberName);
                if (member == null)
                    return "No user exists with this username!";

                Task task = team.getTasks().stream().filter(t -> t.getId() == taskId).findAny().orElse(null);
                if (task == null)
                    return "No Task exists with this id!";

                task.getUsers().remove(member);
                task.getUsers().add(member);
                member.getTasks().remove(task);
                member.getTasks().add(task);

                userRepository.update(member);
                taskRepository.update(task);
                return "Member Assigned Successfully!";
            }
            return "team not found!";
        }
        return "You do not have the permission to do this action!";
    }


    public String promoteTeamMember(String username, String teamName, String memberName) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getLeader()) {
            Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName))
                    .findAny().orElse(null);
            if (team != null) {
                User teamMember = team.findByUsername(memberName);
                if (teamMember == null)
                    return "No user exists with this username!";

                teamMember.setLeader(true);
                teamMember.setRole(Role.TEAM_LEADER);
                for (Team teamMemberTeam : teamMember.getTeams()) {
                    teamMemberTeam.getMembers().remove(teamMember);
                }

                team.setLeader(teamMember);
                teamMember.setTeams(new ArrayList<>());
                teamMember.getTeams().add(team);
                user.getTeams().remove(team);

                teamRepository.update(team);
                userRepository.update(user);
                userRepository.update(teamMember);
                return "user: " + memberName + " 's role change to leader";
            }
            return "team not found!";
        }
        return "You do not have the permission to do this action!";
    }

    public String suspendTeamMember(String username, String teamName, String memberName) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getLeader()) {
            Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName))
                    .findAny().orElse(null);
            if (team != null) {
                User teamMember = team.findByUsername(memberName);
                if (teamMember == null)
                    return "No user exists with this username!";

                team.getSuspendMembers().add(teamMember);
                teamRepository.update(team);
                return "user: " + memberName + " suspended";
            }
            return "team not found!";
        }
        return "You do not have the permission to do this action!";
    }

    public String deleteTeamMember(String username, String teamName, String memberName) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getLeader()) {
            Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName)).findAny().orElse(null);
            if (team != null) {
                User teamMember = team.findByUsername(memberName);
                if (teamMember == null)
                    return "No user exists with this username!";

                team.getMembers().remove(teamMember);
                team.getSuspendMembers().remove(teamMember);
                teamMember.getTeams().remove(team);

                userRepository.update(teamMember);
                teamRepository.update(team);
                return "user removed successfully";
            }
            return "team not found";
        }
        return "You do not have the permission to do this action!";
    }

    public String addMemberToTeam(String username, String teamName, String newMemberName) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getLeader()) {
            Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName))
                    .findAny().orElse(null);
            if (team != null) {
                User newMember = userRepository.findByUsername(newMemberName);
                if (newMember == null)
                    return "No user exists with this username!";
                newMember.getTeams().remove(team);
                newMember.getTeams().add(team);

                team.getMembers().remove(newMember);
                team.getMembers().add(newMember);
                userRepository.update(newMember);
                teamRepository.update(team);
                return "new member add successfully!";
            }
            return "team not found";
        }
        return "You do not have the permission to do this action!";
    }

    public List<String> showMembersName(String username, String teamName) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getLeader()) {
            return user.getTeams().stream().filter(t -> t.getName().equals(teamName))
                    .flatMap(t -> t.getMembers().stream())
                    .map(User::getUsername)
                    .sorted(String::compareTo)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public String createTask(String username, String teamName, String taskTitle, String startTime, String deadline) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                Team team = teamRepository.findByTeamName(teamName);
                if (team.getTasks().stream().anyMatch(t -> t.getTitle().equals(taskTitle)))
                    return "There is another task with this title!";

                if (Task.isValidDate(startTime)) {
                    if (Task.isValidDate(deadline)) {
                        Task task = new Task(taskTitle);
                        task.setDeadLine(deadline);
                        task.setCreationDate(startTime);
                        team.getTasks().add(task);
                        taskRepository.createTask(task);
                        teamRepository.update(team);

                        return "Task created successfully!";
                    }
                    return "Invalid deadline!";
                }
                return "Invalid start date!";
            }
            return "You do not have the permission to do this action!";
        }
        return "user not found";
    }

    public String createNewTeam(String username, String teamName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getLeader()) {
                if (isValidTeamName(teamName)) {
                    Team existedTeam = teamRepository.findByTeamName(teamName);
                    if (existedTeam != null)
                        return "There is another team with this name!";

                    Team team = new Team(teamName);
                    team.setLeader(user);
                    teamRepository.createTeam(team);
                    user.getTeams().add(team);
                    userRepository.update(user);

                    return "Team created successfully! Waiting For Admin’s confirmation…";
                } else {
                    return "Team name is invalid!";
                }
            } else {
                return "You do not have the permission to do this action!";
            }
        } else {
            return "user not found";
        }
    }

    public ShowCalenderResponse showCalender(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            List<String> sortedDeadlines = user.getTasks().stream()
                    .filter(t -> !(t.isFailed() || t.isDone() || t.taskTimeFinished()))
                    .map(Task::getDeadLine)
                    .sorted(String::compareTo)
                    .collect(Collectors.toList());

            if (sortedDeadlines.isEmpty())
                return new ShowCalenderResponse("no deadlines");

            List<String> responseDeadLines = new ArrayList<>();
            for (String deadline : sortedDeadlines) {
                int days = Task.getDeadlineDays(deadline);
                StringBuilder stringBuilder = new StringBuilder();
                if (days < 4)
                    stringBuilder.append("***");
                else if (days <= 10)
                    stringBuilder.append("**");
                else
                    stringBuilder.append("*");

                int dateIndex = deadline.indexOf("|");
                if (dateIndex < 0)
                    dateIndex = 10;

                stringBuilder.append(deadline.substring(0, dateIndex));
                stringBuilder.append("__remaining days:");
                stringBuilder.append(days);
                responseDeadLines.add(stringBuilder.toString());
            }
            return new ShowCalenderResponse(responseDeadLines);
        }
        return new ShowCalenderResponse("user not found!");
    }

    public List<Task> showAllTasksOfTeam(String username, String teamName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (Boolean.TRUE.equals(user.getLeader())) {
                Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName)).findAny().orElse(null);
                if (team == null)
                    return new ArrayList<>();

                return team.getTasks();

            }
        }

        return null;
    }

    private boolean isValidTeamName(String teamName) {
        Pattern pattern = Pattern.compile("(^[a-zA-Z])([a-zA-Z0-9])*");

        return teamName != null && teamName.length() >= 5 && teamName.length() <= 12 && pattern.matcher(teamName).matches();
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

                        if (!task.isFailed())
                            return "This task is not in failed section";

                        Category category = null;
                        if (categoryName != null) {

                            Optional<Category> categoryOptional = board.getCategories().stream().filter(c -> c.getName().equals(categoryName)).findAny();
                            if (categoryOptional.isPresent()) {
                                category = categoryOptional.get();
                            } else {
                                return "Invalid category";
                            }
                        } else {
                            category = board.getCategories().get(0);
                        }

                        Category oldCategory = task.getCategory();
                        if (oldCategory != null) {
                            oldCategory.getTasks().remove(task);
                            categoryRepository.update(oldCategory);
                        }
                        task.setCategory(category);
                        category.getTasks().add(task);

                        if (assignUsername != null) {
                            User assignUser = team.findByUsername(assignUsername);
                            if (assignUser == null)
                                return "Invalid teammember";
                            task.getUsers().add(assignUser);
                            assignUser.getTasks().add(task);
                            userRepository.update(user);
                        }

                        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD|HH:mm");
                        dateFormat.setLenient(false);
                        try {
                            Date date = dateFormat.parse(deadLine);
                            if (task.getCreationDate().compareTo(deadLine) <= 0 && date.compareTo(new Date()) >= 0) {
                                task.setDeadLine(deadLine);
                            } else {
                                return "invalid deadline";
                            }

                        } catch (Exception e) {
                            return "invalid deadline";
                        }

                        taskRepository.update(task);
                        categoryRepository.update(category);

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
                    if (category != null) {
                        return category.getTasks().stream().map(Task::getTitle).collect(Collectors.toList());
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    private boolean isValidUsername(String username) {
        Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9-]*$");
        return usernamePattern.matcher(username).matches();
    }

    private boolean isValidPassword(String password) {
        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
        return passwordPattern.matcher(password).matches() && password.length() >= 8;
    }

    public LoginResponse loginUser(String username, String password) {
        if (username.equals("admin") && password.equals("admin"))
            return new LoginResponse(0, username, Role.SYSTEM_ADMINISTRATOR, "user logged in successfully!");

        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Log log = new Log(dateFormat.format(new Date()), user.getId());
                user.getLogs().add(log);
                logRepository.createLog(log);
                userRepository.update(user);
                return new LoginResponse(user.getId(), user.getUsername(), user.getRole(), "user logged in successfully!");
            } else {
                return new LoginResponse("Username and password didn’t match!");
            }
        } else {
            return new LoginResponse("There is not any user with username: " + username + "!");
        }
    }

    public boolean checkUEmailExists(String email) {
        return userRepository.existEmail(email);
    }

    public boolean userHasThisTeam(String username, String teamName) {
        User user = userRepository.findByUsername(username);
        return user.getTeams().stream().anyMatch(t -> t.getName().equals(teamName));
    }

    public List<Task> showAllTasks(String username, String teamName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Team team = user.getTeams().stream().filter(t -> t.getName().equals(teamName)).findAny().orElse(null);
            if (team == null)
                return new ArrayList<>();

            return team.getTasks();
        }

        return new ArrayList<>();
    }
}