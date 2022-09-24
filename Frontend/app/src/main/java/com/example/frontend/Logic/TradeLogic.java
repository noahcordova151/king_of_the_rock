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

public class TradeLogic implements IVolleyListener {

    private String TAG = TradeLogic.class.getSimpleName();

    IView r;
    IServerRequest serverRequest;
    private IUser currUser = new User();

    public TradeLogic(IView r, IServerRequest serverRequest, IUser user) {
        this.r = r;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        currUser.setUser(user);
    }

    public void submitTrade(int stoneQuantity, int woodQuantity, int foodQuantity, int waterQuantity, String gameObjectString) throws JSONException {
        Log.d(TAG, "attempting to create a trade request...");
        String url =  Constants.URL + "/game/wants/" + gameObjectString + "?auth-token=" + currUser.getAuthToken();

        JSONObject newTradeRequestObj = new JSONObject();
//        newTradeRequestObj.put("stone");
//        newTradeRequestObj.put("wood");
//        newTradeRequestObj.put("food");
//        newTradeRequestObj.put("water");

        Log.d(TAG, "sending trade request...");
        serverRequest.sendToServer(url, newTradeRequestObj, "POST");
    }

    @Override
    public void onSuccess(JSONObject response) {
        r.logText(response.toString());
        //r.makeToast(structureToBuild.substring(0,1).toUpperCase() + structureToBuild.substring(1) + " was built");
    }

    @Override
    public void onError (String errorMessage) {
        r.logText(errorMessage);
    }
}
