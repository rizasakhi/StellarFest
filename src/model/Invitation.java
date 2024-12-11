package model;

import model.join.JoinField;
import model.user.User;

import java.util.Objects;

public class Invitation {

    private final long id;
    private JoinField<Event> event;
    private JoinField<User> user;

    public Invitation(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public JoinField<Event> getEvent() {
        return event;
    }

    public void setEvent(JoinField<Event> event) {
        this.event = event;
    }

    public JoinField<User> getUser() {
        return user;
    }

    public void setUser(JoinField<User> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + id +
                ", event=" + event.getId() +
                ", user=" + user.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Invitation)) return false;
        Invitation that = (Invitation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
