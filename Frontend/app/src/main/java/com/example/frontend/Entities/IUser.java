package com.example.frontend.Entities;

public interface IUser {
    public void setUser(IUser user);

    public String getUsername();
    public void setUsername(String username);

    public String getAuthToken();
    public void setAuthToken(String authToken);

    public boolean getIsAdmin();
    public void setIsAdmin(boolean isAdmin);

    public boolean getIsHost();
    public void setIsHost(boolean isHost);

    public String toString();
}
