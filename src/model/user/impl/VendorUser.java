package model.user.impl;

import model.user.User;
import view.SFView;

public class VendorUser extends User {
    public VendorUser(long id) {
        super(id);
    }

    public VendorUser(long id, String email, String username) {
        super(id, email, username);
    }

    @Override
    public Class<? extends SFView> getHomeView() {
        return null;
    }
}
