package enums;

public enum Role {

    ADMIN("Admin"),
    EVENT_ORGANIZER("Event Organizer"),
    VENDOR("Vendor"),
    GUEST("Guest");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public static Role getRole(String role) {
        for (Role r : Role.values()) {
            if (r.getRole().equals(role)) {
                return r;
            }
        }
        return null;
    }

    public String getRole() {
        return this.role;
    }
}
