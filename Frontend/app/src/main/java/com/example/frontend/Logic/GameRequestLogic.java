package com.example.frontend.Logic;

import android.content.Context;
import android.util.Log;

import com.example.frontend.Entities.IUser;
import com.example.frontend.Entities.User;
import com.example.frontend.Network.IServerRequest;
import com.example.frontend.SupportingClasses.Constants;
import com.example.frontend.SupportingClasses.IView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameRequestLogic implements IVolleyListener {

    IView r;
    private static IServerRequest serverRequest;
    private static final IUser currUser = new User();
    private JSONArray spawners;

    public GameRequestLogic(IView r, IServerRequest serverRequest, IUser user) {
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        currUser.setUser(user);
    }

    public void startSelection(JSONObject selectionObject, int gameID) {
        Log.d(GameRequestLogic.class.toString(), "Sending start selection");
        String url = Constants.URL + "/game/spawners/" + gameID + "?auth-token=" + currUser.getAuthToken();

        selectionObject = new JSONObject();
        try {
            selectionObject.put("", new JSONArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        serverRequest.sendToServer(url, null, "POST");
    }

    public void emptySpawnerRequest(int gameID){
        String url = Constants.URL + "/game/spawners/" + gameID + "?auth-token=" + currUser.getAuthToken();
        JSONObject emptySpawnerObject = new JSONObject();

        serverRequest.sendToServer(url, emptySpawnerObject, "POST");
    }

    public JSONArray getSpawnersFromResponse(){
        return spawners;
    }

    @Override
    public void onSuccess(JSONObject response) {
        r.logText(response.toString());
        try {
            spawners = response.getJSONArray("spawners");
            r.switchActivity();
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onError(String message) {
    }
}
