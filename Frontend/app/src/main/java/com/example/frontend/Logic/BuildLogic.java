package com.example.frontend.Logic;

import android.util.Log;
import android.widget.Toast;

import com.example.frontend.Entities.IUser;
import com.example.frontend.Entities.User;
import com.example.frontend.Network.IServerRequest;
import com.example.frontend.SupportingClasses.Constants;
import com.example.frontend.SupportingClasses.IView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class BuildLogic implements IVolleyListener {

    IView r;
    IServerRequest serverRequest;

    private String TAG = BuildLogic.class.getSimpleName();
    private IUser currUser = new User();
    private String structureToBuild;

    public BuildLogic(IView r, IServerRequest serverRequest, IUser user) {
        this.r = r;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        this.currUser.setUser(user);
    }

    public void buildStructure(String structureName, String gameID) throws JSONException {
        Log.d(TAG, "attempting to build a structure...");
        String url =  Constants.URL + "/game/build/" + gameID + "/" + structureName + "?auth-token=" + currUser.getAuthToken();

        structureToBuild = structureName;

        Log.d(TAG, "sending build request...");
        serverRequest.sendToServer(url, null, "POST");
    }

    @Override
    public void onSuccess(JSONObject response) {
        //TODO: something with response for gameview screen?
        r.logText(response.toString());
        r.makeToast(structureToBuild.substring(0, 1).toUpperCase() + structureToBuild.substring(1) + " was built");
    }

    @Override
    public void onError(String errorMessage) {
        r.logText(errorMessage);
        r.makeToast("Unable to build " + structureToBuild.substring(0, 1).toUpperCase() + structureToBuild.substring(1));
    }
}
