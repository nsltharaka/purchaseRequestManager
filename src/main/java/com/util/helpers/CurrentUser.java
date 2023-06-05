package com.util.helpers;

import com.model.dto.UserDTO;

public class CurrentUser {

    private static UserDTO currentUser;

    public static UserDTO getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserDTO user) {
        CurrentUser.currentUser = user;
    }

}
