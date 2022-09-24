package com.example.frontend.Logic;

import android.util.Log;

import com.example.frontend.Entities.IUser;
import com.example.frontend.Entities.User;
import com.example.frontend.Network.IServerRequest;
import com.example.frontend.SupportingClasses.Constants;
import com.example.frontend.SupportingClasses.IView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InitialTradeLogic implements IVolleyListener {

    IView r;
    IServerRequest serverRequest;

    private String TAG = InitialTradeLogic.class.getSimpleName();
    private IUser currUser = new User();
    private String[] materials;

    public InitialTradeLogic(IView r, IServerRequest serverRequest, IUser user) {
        this.r = r;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        this.currUser.setUser(user);
    }

    public void submitTrade(boolean stoneBool, boolean woodBool, boolean foodBool, boolean waterBool, String gameObjectString) throws JSONException {
        Log.d(TAG, "attempting to create a trade request...");
        String url =  Constants.URL + "/game/wants/" + gameObjectString + "?auth-token=" + currUser.getAuthToken();

        ArrayList<String> materialsWanted = new ArrayList<String>();
        if (stoneBool) {materialsWanted.add("stone");}
        if (woodBool) {materialsWanted.add("wood");}
        if (foodBool) {materialsWanted.add("food");}
        if (waterBool) {materialsWanted.add("water");}
        materials = new String[materialsWanted.size()];
        for(int i = 0; i < materials.length; i++){
            materials[i] = materialsWanted.get(i);
        }

        JSONObject newTradeRequestObj = new JSONObject();
        newTradeRequestObj.put("materials",materials);

        Log.d(TAG, "sending trade request...");
        serverRequest.sendToServer(url, newTradeRequestObj, "POST");
    }

    @Override
    public void onSuccess(JSONObject response) {
        r.logText(response.toString());

        String toastString = "Request for ";
        for(String mat:materials){
            toastString += mat + ", ";
        }
        toastString += "was submitted";
        r.makeToast(toastString);
    }

    @Override
    public void onError (String errorMessage) {
        r.logText(errorMessage);
    }
}
