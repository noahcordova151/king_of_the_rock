package com.example.frontend.Logic;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IVolleyListener {
    public void onError(String message);
    public void onSuccess(JSONObject response);
}
