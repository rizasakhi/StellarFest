package controller.event;

import driver.Connect;
import driver.Results;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventAttendeeController {

    public static List<Long> getAttendeesForEvent(long eventId) {
        List<Long> vendorIds = new ArrayList<>();

        String query = "SELECT * FROM event_attendees WHERE event_id = ?";
        try (Results results = Connect.getInstance().executeQuery(query, eventId)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                vendorIds.add(set.getLong("user_id"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return vendorIds;
    }

}
