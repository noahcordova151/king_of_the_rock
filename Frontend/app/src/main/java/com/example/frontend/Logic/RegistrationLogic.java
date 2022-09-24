package com.example.frontend.Logic;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.frontend.SupportingClasses.Constants;
import com.example.frontend.SupportingClasses.IView;
import com.example.frontend.Network.IServerRequest;

public class RegistrationLogic implements IVolleyListener {

    IView r;
    IServerRequest serverRequest;

    public RegistrationLogic(IView r, IServerRequest serverRequest) {
        this.r = r;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    public void registerUser(String email, String username, String password, boolean adminBool) throws JSONException {
        String url = Constants.URL + "/register";
        JSONObject newUserObj = new JSONObject();
        newUserObj.put("email", email);
        newUserObj.put("username", username);
        newUserObj.put("password", password);
        newUserObj.put("isAdmin", adminBool);

        serverRequest.sendToServer(url, newUserObj, "POST");
    }

    @Override
    public void onSuccess(JSONObject response) {
        Log.d("RegistrationLogic", "in onSuccess method");
        r.switchActivity();
    }

    @Override
    public void onError (String errorMessage) {
        Log.d("RegistrationLogic", "in onError method");
        r.logText(errorMessage);
        r.makeToast("Invalid email or username");
    }
}
