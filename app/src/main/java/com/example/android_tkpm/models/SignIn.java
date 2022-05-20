package com.example.android_tkpm.models;

public class SignIn {
    private String email, password, tokenDevice;

    public SignIn() { }

    public SignIn(String email, String password, String tokenDevice) {
        this.email = email;
        this.password = password;
        this.tokenDevice = tokenDevice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public void setTokenDevice(String tokenDevice) {
        this.tokenDevice = tokenDevice;
    }
}
