package com.example.volleyrequests1;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.example.volleyrequests1.app.AppController;
import com.example.volleyrequests1.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonRequestActivity extends Activity implements OnClickListener {
    private String TAG = JsonRequestActivity.class.getSimpleName();
    private Button btnJsonObj, btnJsonArray;
    private TextView msgResponse;
    private ProgressDialog pDialog;
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_request);

        btnJsonObj = (Button) findViewById(R.id.activity_json_request_object_request_button);
        btnJsonArray = (Button) findViewById(R.id.activity_json_request_array_request_button);
        msgResponse = (TextView) findViewById(R.id.activity_json_request_textview_response);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnJsonObj.setOnClickListener(this);
        btnJsonArray.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_json_request_object_request_button:
                makeJsonObjReq();
                break;
            case R.id.activity_json_request_array_request_button:
                makeJsonArryReq();
                break;
        }
    }

    //JSON object request
    private void makeJsonObjReq() {
        //showProgressDialog();
        if (!pDialog.isShowing())
            pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                Const.URL_JSON_OBJECT, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        //hideProgressDialog();
                        if (pDialog.isShowing())
                            pDialog.hide();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        //hideProgressDialog();
                        if (pDialog.isShowing())
                            pDialog.hide();
                    }
        })
        {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "John Wick");
                //params.put("email", "abc@androidhive.info");
                params.put("password", "password");
                return params;
            }
        };

        //add request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    //JSON array request
    private void makeJsonArryReq() {
        if (!pDialog.isShowing())
            pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        //hideProgressDialog();
                        if (pDialog.isShowing())
                            pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hideProgressDialog();
                if (pDialog.isShowing())
                    pDialog.hide();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

}