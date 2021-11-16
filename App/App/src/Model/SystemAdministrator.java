package Model;

public class SystemAdministrator {
    private String password ;
    private String username ;
    UserRole role = UserRole.SystemAdministrator ;

    public UserRole getRole() {
        return role;
    }

}
