package model.user.impl;

import model.user.User;
import view.SFView;

public class EOUser extends User {
    public EOUser(long id) {
        super(id);
    }

    public EOUser(long id, String email, String username) {
        super(id, email, username);
    }

    @Override
    public Class<? extends SFView> getHomeView() {
        return null;
    }
}
