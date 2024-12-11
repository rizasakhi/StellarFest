package model;

import model.join.JoinField;
import model.join.JoinFields;
import model.user.impl.EOUser;
import model.user.impl.GuestUser;
import model.user.impl.VendorUser;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Event {

    private final long id;
    private String name;
    private ZonedDateTime date;
    private String location;
    private String description;

    private JoinField<EOUser> organizer;
    private JoinFields<Invitation> invitations;
    private JoinFields<GuestUser> guests;
    private JoinFields<VendorUser> vendors;

    public Event(long id) {
        this.id = id;
    }

    public Event(long id, String name, ZonedDateTime date, String location, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JoinField<EOUser> getOrganizer() {
        return organizer;
    }

    public void setOrganizer(JoinField<EOUser> organizer) {
        this.organizer = organizer;
    }

    public JoinFields<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(JoinFields<Invitation> invitations) {
        this.invitations = invitations;
    }

    public JoinFields<GuestUser> getGuests() {
        return guests;
    }

    public void setGuests(JoinFields<GuestUser> guests) {
        this.guests = guests;
    }

    public JoinFields<VendorUser> getVendors() {
        return vendors;
    }

    public void setVendors(JoinFields<VendorUser> vendors) {
        this.vendors = vendors;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", organizer='" + organizer.getId() + '\'' +
                ", invitations='" + invitations.getIds() + '\'' +
                ", guests='" + guests.getIds() + '\'' +
                ", vendors='" + vendors.getIds() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
