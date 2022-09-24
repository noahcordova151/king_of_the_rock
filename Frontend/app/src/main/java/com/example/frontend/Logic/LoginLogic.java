package com.example.frontend.Logic;

import android.util.Log;

import com.example.frontend.Entities.IUser;
import com.example.frontend.Entities.User;
import com.example.frontend.LoginScreen;
import com.example.frontend.Network.IServerRequest;
import com.example.frontend.SupportingClasses.Constants;
import com.example.frontend.SupportingClasses.IView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginLogic implements IVolleyListener {

    IView r;
    IServerRequest serverRequest;

    private IUser currUser;
    private String currentUsername;

    public LoginLogic(IView r, IServerRequest serverRequest) {
        this.r = r;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    public void loginUser(String username, String password) throws JSONException {
        Log.d("LoginLogic", "in loginUser, attempting login");
        String url = Constants.URL + "/login";
        JSONObject newUserObj = new JSONObject();

        currentUsername = username;

        newUserObj.put("username", username);
        newUserObj.put("password", password);

        Log.d("LoginLogic", "in loginUser, sending login request");
        serverRequest.sendToServer(url, newUserObj, "POST");
    }

    public IUser getCurrentUser() {
        return currUser;
    }

    @Override
    public void onSuccess(JSONObject response) {
        r.logText("success response: " + response.toString());
        try {
            currUser = new User(response.getString("auth-token"), currentUsername, response.getBoolean("isAdmin"));
            Log.d("LoginLogic", currUser.toString());
            LoginScreen.setCurrentUser(currUser);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        r.switchActivity();
    }

    @Override
    public void onError(String errorMessage) {
        r.logText(errorMessage);
        r.makeToast("Invalid login credentials");
    }
}
