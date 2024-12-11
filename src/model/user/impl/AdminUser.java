package model.user.impl;

import model.user.User;
import view.SFView;
import view.admin.AdminHomeView;

public class AdminUser extends User {
    public AdminUser(long id) {
        super(id);
    }

    public AdminUser(long id, String email, String username) {
        super(id, email, username);
    }

    @Override
    public Class<? extends SFView> getHomeView() {
        return AdminHomeView.class;
    }
}
