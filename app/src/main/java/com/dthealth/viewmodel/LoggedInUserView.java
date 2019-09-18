package com.dthealth.viewmodel;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView {
    private String displayName;
    private String userId;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName,String userId) {
        this.displayName = displayName;
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserId() {
        return userId;
    }
}
