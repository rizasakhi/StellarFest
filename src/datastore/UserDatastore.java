package datastore;

import model.user.User;

public class UserDatastore {

    private static volatile UserDatastore instance = null;
    private User currentUser;

    private UserDatastore() {
        this.currentUser = null;
    }

    public static UserDatastore getInstance() {
        if (instance == null) {
            synchronized (UserDatastore.class) {
                if (instance == null) {
                    instance = new UserDatastore();
                }
            }
        }
        return instance;

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
