package com.example.frontend.Entities;

public class User implements IUser {

    private String authToken;
    private String username;
    private boolean isAdmin;
    private boolean isHost;

    public User(){}

    public User(String authToken, String username, boolean isAdmin) {
        this.authToken = authToken;
        this.username = username;
        this.isAdmin = isAdmin;
        isHost = false;
    }

    public void setUser(IUser user){
        this.authToken = user.getAuthToken();
        this.username = user.getUsername();
        this.isAdmin = user.getIsAdmin();
        this.isHost = user.getIsHost();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getAuthToken() {
        return authToken;
    }

    @Override
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public boolean getIsAdmin() {
        return isAdmin;
    }

    @Override
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean getIsHost() {
        return isHost;
    }

    @Override
    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }

    @Override
    public String toString() {
        return "Current user: {authToken,\"" + authToken + "\"; username,\"" + username + "\"; isAdmin," + isAdmin + "; isHost," + isHost + "}";
    }
}
