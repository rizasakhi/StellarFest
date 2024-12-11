package model.user;

import view.SFView;

import java.util.Objects;

public abstract class User {

    private final long id;
    private String email;
    private String username;

    public User(long id) {
        this.id = id;
    }

    public User(long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public abstract Class<? extends SFView> getHomeView();

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
