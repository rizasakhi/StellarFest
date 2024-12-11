package controller.event;

import controller.UserController;
import driver.Connect;
import driver.Results;
import model.user.User;
import model.user.impl.GuestUser;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventGuestController {

    public static List<Long> getGuestsForEvent(long eventId) {
        List<Long> guestIds = new ArrayList<>();

        String query = "SELECT * FROM event_guests WHERE event_id = ?";
        try (Results results = Connect.getInstance().executeQuery(query, eventId)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                guestIds.add(set.getLong("id"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return guestIds;
    }

    public static List<GuestUser> getMany(List<Long> ids) {
        List<User> users = UserController.getMany(ids);

        return users.stream()
                .filter(user -> user instanceof GuestUser)
                .map(user -> (GuestUser) user)
                .collect(Collectors.toList());
    }
}
