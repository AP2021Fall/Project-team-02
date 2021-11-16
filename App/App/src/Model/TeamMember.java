package Model;


public class TeamMember extends User {
    protected UserRole role = UserRole.TEAMMEMBER  ;

    public TeamMember(int password, String username, String email) {
        super(password, username, email);
    }

    public UserRole getRole() {
        return role;
    }
}
