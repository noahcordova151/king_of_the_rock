package com.example.frontend;

import static com.example.frontend.SupportingClasses.Constants.URL;
import static com.example.frontend.SupportingClasses.Constants.WSURL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.frontend.Entities.IUser;
import com.example.frontend.SupportingClasses.AppController;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class Lobby extends AppCompatActivity {
    private String TAG = Lobby.class.getSimpleName();
    String lobbyCode;
    private static int gameID;

    private WebSocketClient lobbyWebSocket;

    private Intent intent;

    private long pressedTime;

    Draft[] drafts = {
            new Draft_6455()
    };

    /**
     * Holds response, instantiates websocket connection after
     */
    private void holdResponse() {
        TextView lobbyCodeText = findViewById(R.id.join_game_lobby_code_textview);
        if (lobbyCode != null) {
            lobbyCodeText.setText(lobbyCode);
        } else {
            lobbyCodeText.setText("error");
        }

        instantiateWebsocket();
        lobbyWebSocket.connect();
    }

    /**
     * Parse lobby websocket messages
     * @param jsonMessageString
     * @param lobbyCode
     * @param playerCount
     */
    private void parseWebsocketMessage(String jsonMessageString, TextView lobbyCode, TextView playerCount) {
        Log.d(Lobby.class.toString(), jsonMessageString);
        try {
            JSONObject websocketMessage = new JSONObject(jsonMessageString);
            String messageString = websocketMessage.getString("type");

            switch (messageString) {
                case "lobby":
                    String lobbyCodeString = websocketMessage.getJSONObject("lobby").getString("code");
                    lobbyCode.setText(lobbyCodeString);
                    break;

                case "player-join":
                case "player-leave":
                    int numPlayers = websocketMessage.getInt("num-players");
                    String numPlayerString = "Players: " + numPlayers + "/4";
                    playerCount.setText(numPlayerString);
                    break;

                case "start-game":
                    gameID = websocketMessage.getJSONObject("game").getInt("id");
                    startActivity(new Intent(getApplicationContext(), GameViewScreen.class));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(Lobby.class.toString(), "Error parsing websocket message");
        }

    }

    /**
     * Instantiates new lobby connection
     */
    private void instantiateWebsocket() {
        try {
            String endpoint = String.format("%s/lobby/%s/%s", WSURL, lobbyCode, LoginScreen.getCurrentUser().getAuthToken());
            Log.d(Lobby.class.toString(), String.format("Attempting WS connection to %s", endpoint));
            lobbyWebSocket = new WebSocketClient(new URI(endpoint), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("OPEN", "Opening lobby websocket, lc: " + lobbyCode);
                }

                @Override
                public void onMessage(String message) {
                    View myView = findViewById(android.R.id.content).getRootView();
                    myView.postInvalidate();
                    Log.d("Websocket Message: ", message);
                    TextView playerCount = findViewById(R.id.join_game_player_count_textview);
                    TextView lobbyCode = findViewById(R.id.join_game_lobby_code_textview);
                    parseWebsocketMessage(message, lobbyCode, playerCount);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                    Bundle bundle = intent.getExtras();
//                    if(bundle.get("CONNECT").equals("GAME")){
//                        startActivity(intent);
//                    }
                }

                @Override
                public void onError(Exception ex) {
                    Log.d("Exception:", ex.toString());
                }
            };
        } catch (URISyntaxException uriSyntaxException) {
            Log.d(Lobby.class.toString(), "Unable to establish lobby websocket connection");
            uriSyntaxException.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            lobbyWebSocket.close();
            startActivity(new Intent(getBaseContext(), JoinGameScreen.class));
            Toast.makeText(getBaseContext(), "Leaving lobby", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Bundle bundle = getIntent().getExtras();
        Log.d(TAG, "currentUser: " + LoginScreen.getCurrentUser().toString());

        if (LoginScreen.getCurrentUser().getIsHost()) {
            AppController.getInstance().addToRequestQueue(new JsonObjectRequest(
                Request.Method.POST,
                URL + "/lobby/host" + "?auth-token=" + LoginScreen.getCurrentUser().getAuthToken(),
                null,
                new Response.Listener<>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            lobbyCode = response.getString("code");
                            Log.d(Lobby.class.toString(), lobbyCode);
                            holdResponse();
                        } catch (JSONException e) {
                            Log.d(Lobby.class.toString(), "Code not found in response");
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    NetworkResponse response = error.networkResponse;
                    if ((error instanceof ServerError || error instanceof NetworkError || error instanceof TimeoutError || error instanceof AuthFailureError || error instanceof ParseError)) {
                        try {
                            String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            JSONObject obj = new JSONObject(res);

                            if (obj.has("message")) {
                                Log.d(Lobby.class.toString(), "Error on host: " + obj.getString("message"));
                            }
                        } catch (UnsupportedEncodingException | JSONException e) {
                            Log.e(Lobby.class.toString(), Log.getStackTraceString(e));
                        }
                    }
                })
            );
        } else {
            //lobbyCode = bundle.getString("lobbyCode");
            instantiateWebsocket();
            lobbyWebSocket.connect();
        }
    }

    public static int getGameID() {
        return gameID;
    }
}


