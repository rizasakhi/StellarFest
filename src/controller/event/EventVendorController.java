package controller.event;

import controller.UserController;
import driver.Connect;
import driver.Results;
import model.user.User;
import model.user.impl.VendorUser;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventVendorController {

    public static List<Long> getVendorsForEvent(long eventId) {
        List<Long> vendorIds = new ArrayList<>();

        String query = "SELECT * FROM event_vendors WHERE event_id = ?";
        try (Results results = Connect.getInstance().executeQuery(query, eventId)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                vendorIds.add(set.getLong("id"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return vendorIds;
    }

    public static List<VendorUser> getMany(List<Long> ids) {
        List<User> users = UserController.getMany(ids);

        return users.stream()
                .filter(user -> user instanceof VendorUser)
                .map(user -> (VendorUser) user)
                .collect(Collectors.toList());
    }
}
