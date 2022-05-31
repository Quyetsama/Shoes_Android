package com.example.android_tkpm.models;

public class User {
    private String _id, fullName, email;
    private static User user = new User();

    public User() {}

    public User(String _id, String fullName, String email) {
        this._id = _id;
        this.fullName = fullName;
        this.email = email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
    }

    public static void clearUser() {
        User.user = null;
    }
}
