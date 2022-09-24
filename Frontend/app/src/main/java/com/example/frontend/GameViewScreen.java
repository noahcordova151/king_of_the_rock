package com.example.frontend;

import static com.example.frontend.SupportingClasses.Constants.WSURL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.Entities.IInGameUser;
import com.example.frontend.Entities.IUser;
import com.example.frontend.Entities.InGameUser;
import com.example.frontend.Entities.SpawnerChoices;
import com.example.frontend.Entities.SpawnerOptions;
import com.example.frontend.Logic.GameRequestLogic;
import com.example.frontend.Network.ServerRequest;
import com.example.frontend.SupportingClasses.AppController;
import com.example.frontend.SupportingClasses.IView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Class for the logic of the screen of the main game of the user
 *
 * @author Noah Cordova
 */
public class GameViewScreen extends AppCompatActivity implements IView {

    private String TAG = GameViewScreen.class.getSimpleName();
    private JSONObject jsonGameObject;

    private WebSocketClient gameClient;
    private IView thisView = this;
    private GameRequestLogic logic;

    private IInGameUser currentInGameUser;
    private IInGameUser inGameUser2;
    private IInGameUser inGameUser3;
    private IInGameUser inGameUser4;
    private IInGameUser[] players = new IInGameUser[4];

    private TextView tvCurrentUsername, tvUsername1, tvUsername2, tvUsername3;
    private TextView tvWaterQty, tvStoneQty, tvFoodQty, tvWoodQty;
    private TextView tvCurrentUserPointsQty, tvCurrentUserTownQty, tvCurrentUserHouseQty, tvCurrentUserMineQty, tvCurrentUserLumberyardQty, tvCurrentUserGardenQty, tvCurrentUserWellQty;
    private TextView tvUser1PointsQty, tvUser1TownQty, tvUser1HouseQty, tvUser1MineQty, tvUser1LumberyardQty, tvUser1GardenQty, tvUser1WellQty;
    private TextView tvUser2PointsQty, tvUser2TownQty, tvUser2HouseQty, tvUser2MineQty, tvUser2LumberyardQty, tvUser2GardenQty, tvUser2WellQty;
    private TextView tvUser3PointsQty, tvUser3TownQty, tvUser3HouseQty, tvUser3MineQty, tvUser3LumberyardQty, tvUser3GardenQty, tvUser3WellQty;
    private TextView woodSpawner1, woodSpawner2, woodSpawner3, woodSpawner4;
    private TextView stoneSpawner1, stoneSpawner2, stoneSpawner3, stoneSpawner4;
    private TextView foodSpawner1, foodSpawner2, foodSpawner3, foodSpawner4;
    private TextView waterSpawner1, waterSpawner2, waterSpawner3, waterSpawner4;

    private TextView tvDie1, tvDie2;
    private TextView tvTimer;

    private static SpawnerOptions spawnerOptions;
    private JSONArray givenSpawners;

    private static SpawnerChoices spawnerChoices;

    private TextView[] usernameTVArray;

    private TextView[] woodSpawners = new TextView[4];
    private TextView[] foodSpawners = new TextView[4];
    private TextView[] waterSpawners = new TextView[4];
    private TextView[] stoneSpawners = new TextView[4];

    Draft[] drafts = {
            new Draft_6455()
    };

    /**
     * Converts JSONArray to an arraylist
     * @param jsonArray
     * @return
     */
    public ArrayList<Integer> jsonArrayToArrayList(JSONArray jsonArray) {
        ArrayList<Integer> returnList = new ArrayList<Integer>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                int x = (int) jsonArray.get(i);
                Log.d("arr int", Integer.toString(x));
                returnList.add(x);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return returnList;
    }

    /**
     * Set the spawners values
     * @param key
     */
    public void setSpawners(String key){
        ArrayList<Integer> currChoices;
        switch (key){
            case "wood":
                currChoices = spawnerChoices.getWoodChoices();
                for(int i = 0; i < 4; i++){
                    woodSpawners[i].setText(currChoices.get(i));
                }
            case "food":
                currChoices = spawnerChoices.getFoodChoices();
                for(int i = 0; i < 4; i++){
                    foodSpawners[i].setText(currChoices.get(i));
                }
            case "water":
                currChoices = spawnerChoices.getWaterChoices();
                for(int i = 0; i < 4; i++){
                    waterSpawners[i].setText(currChoices.get(i));
                }
            case "stone":
                currChoices = spawnerChoices.getStoneChoices();
                for(int i = 0; i < 4; i++){
                    stoneSpawners[i].setText(currChoices.get(i));
                }
        }
    }

    /**
     * Converts each arraylist of choices to a json object
     *
     * @param spawnerChoices
     * @return
     */
    public JSONObject convertChoicesToJSON(SpawnerChoices spawnerChoices) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("wood", spawnerChoices.getWoodChoices());
            jsonObject.put("food", spawnerChoices.getFoodChoices());
            jsonObject.put("water", spawnerChoices.getWaterChoices());
            jsonObject.put("stone", spawnerChoices.getStoneChoices());
        } catch (JSONException e) {
            Log.d("Can't parse choices", "Can't convert choices to json object");
        }

        return jsonObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view_screen);
        new AppController();

        Log.d("GameViewScreen", LoginScreen.getCurrentUser().toString());

        Bundle bundle = getIntent().getExtras();

        tvCurrentUsername = findViewById(R.id.activity_game_view_tv_current_username);
        tvCurrentUsername.setText(LoginScreen.getCurrentUser().getUsername());
        tvUsername1 = findViewById(R.id.activity_game_view_tv_username1);
        tvUsername1.setText("User 2");
        tvUsername2 = findViewById(R.id.activity_game_view_tv_username2);
        tvUsername1.setText("User 3");
        tvUsername3 = findViewById(R.id.activity_game_view_tv_username3);
        tvUsername1.setText("User 4");
        usernameTVArray = new TextView[3];
        usernameTVArray[0] = tvUsername1;
        usernameTVArray[1] = tvUsername2;
        usernameTVArray[2] = tvUsername3;

        tvCurrentUserPointsQty = findViewById(R.id.activity_game_view_screen_tv_current_user_icon1_quantity);
        tvCurrentUserTownQty = findViewById(R.id.activity_game_view_screen_tv_structure_town_quantity);
        tvCurrentUserHouseQty = findViewById(R.id.activity_game_view_screen_tv_structure_house_quantity);
        tvCurrentUserMineQty = findViewById(R.id.activity_game_view_screen_tv_structure_mine_quantity);
        tvCurrentUserLumberyardQty = findViewById(R.id.activity_game_view_screen_tv_structure_lumberyard_quantity);
        tvCurrentUserGardenQty = findViewById(R.id.activity_game_view_screen_tv_structure_garden_quantity);
        tvCurrentUserWellQty = findViewById(R.id.activity_game_view_screen_tv_structure_well_quantity);
        tvFoodQty = findViewById(R.id.activity_game_view_screen_tv_resource_food_quantity);
        tvStoneQty = findViewById(R.id.activity_game_view_screen_tv_resource_stone_quantity);
        tvWaterQty = findViewById(R.id.activity_game_view_screen_tv_resource_water_quantity);
        tvWoodQty = findViewById(R.id.activity_game_view_screen_tv_resource_wood_quantity);

        woodSpawner1 = findViewById(R.id.activity_game_view_screen_tv_spawner_wood_number_1);
        woodSpawner2 = findViewById(R.id.activity_game_view_screen_tv_spawner_wood_number_2);
        woodSpawner3 = findViewById(R.id.activity_game_view_screen_tv_spawner_wood_number_3);
        woodSpawner4 = findViewById(R.id.activity_game_view_screen_tv_spawner_wood_number_4);

        foodSpawner1 = findViewById(R.id.activity_game_view_screen_tv_spawner_food_number_1);
        foodSpawner2 = findViewById(R.id.activity_game_view_screen_tv_spawner_food_number_2);
        foodSpawner3 = findViewById(R.id.activity_game_view_screen_tv_spawner_food_number_3);
        foodSpawner4 = findViewById(R.id.activity_game_view_screen_tv_spawner_food_number_4);

        waterSpawner1 = findViewById(R.id.activity_game_view_screen_tv_spawner_water_number_1);
        waterSpawner2 = findViewById(R.id.activity_game_view_screen_tv_spawner_water_number_2);
        waterSpawner3 = findViewById(R.id.activity_game_view_screen_tv_spawner_water_number_3);
        waterSpawner4 = findViewById(R.id.activity_game_view_screen_tv_spawner_water_number_4);

        stoneSpawner1 = findViewById(R.id.activity_game_view_screen_tv_spawner_stone_number_1);
        stoneSpawner2 = findViewById(R.id.activity_game_view_screen_tv_spawner_stone_number_2);
        stoneSpawner3 = findViewById(R.id.activity_game_view_screen_tv_spawner_stone_number_3);
        stoneSpawner4 = findViewById(R.id.activity_game_view_screen_tv_spawner_stone_number_4);

        woodSpawners[0] = woodSpawner1;
        woodSpawners[1] = woodSpawner2;
        woodSpawners[2] = woodSpawner3;
        woodSpawners[3] = woodSpawner4;

        foodSpawners[0] = foodSpawner1;
        foodSpawners[1] = foodSpawner2;
        foodSpawners[2] = foodSpawner3;
        foodSpawners[3] = foodSpawner4;

        waterSpawners[0] = waterSpawner1;
        waterSpawners[1] = waterSpawner2;
        waterSpawners[2] = waterSpawner3;
        waterSpawners[3] = waterSpawner4;

        stoneSpawners[0] = stoneSpawner1;
        stoneSpawners[1] = stoneSpawner2;
        stoneSpawners[2] = stoneSpawner3;
        stoneSpawners[3] = stoneSpawner4;

        tvDie1 = findViewById(R.id.activity_game_view_screen_tv_die1);
        tvDie2 = findViewById(R.id.activity_game_view_screen_tv_die2);

        tvTimer = findViewById(R.id.activity_game_view_screen_tv_timer);

        currentInGameUser = new InGameUser(LoginScreen.getCurrentUser().getUsername());

        try {
            String endpoint = String.format("%s/game/%s/%s", WSURL, Lobby.getGameID(), LoginScreen.getCurrentUser().getAuthToken());
            gameClient = new WebSocketClient(new URI(endpoint), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d(GameViewScreen.class.toString(), "Connected to game websocket");
                }

                @Override
                public void onMessage(String message) {
                    Log.d("OnMessage", message);
                    try {
                        JSONObject body = new JSONObject(message);
                        Log.d(TAG, body.toString());
                        String type = body.getString("type");

                        if (type.equals("spawner-options")) {
                            // TODO start timer on frontend
                            //Switch screens and implement logic
                            JSONObject options = body.getJSONObject("options");
                            Log.d(TAG, options.toString());
                            ArrayList<Integer> woodOptions = jsonArrayToArrayList(options.getJSONArray("wood"));
                            ArrayList<Integer> stoneOptions = jsonArrayToArrayList(options.getJSONArray("stone"));
                            ArrayList<Integer> foodOptions = jsonArrayToArrayList(options.getJSONArray("food"));
                            ArrayList<Integer> waterOptions = jsonArrayToArrayList(options.getJSONArray("water"));
                            Log.d(TAG, "parsed JSONObject options for JSONArrays read into ArrayLists");
                            spawnerOptions = new SpawnerOptions(woodOptions, foodOptions, waterOptions, stoneOptions);

                            //startActivity(new Intent(getApplicationContext(), SelectSpawners.class));

                            return;
                        }
                        if (type.equals("end-selection-timer")) {
                            ServerRequest serverRequest = new ServerRequest();
                            logic = new GameRequestLogic(thisView, serverRequest, LoginScreen.getCurrentUser());
                            logic.emptySpawnerRequest(Lobby.getGameID());
                            return;
                        }
                        if (type.equals("material-update")) {
                            JSONObject materialsJson = body.getJSONObject("materials");
                            currentInGameUser.increaseResources("stone", materialsJson.getInt("stone"));
                            currentInGameUser.increaseResources("wood", materialsJson.getInt("wood"));
                            currentInGameUser.increaseResources("food", materialsJson.getInt("food"));
                            currentInGameUser.increaseResources("water", materialsJson.getInt("water"));

                            int[] resources = currentInGameUser.getResources();
                            tvStoneQty.setText("" + resources[0]);
                            tvWoodQty.setText("" + resources[1]);
                            tvFoodQty.setText("" + resources[2]);
                            tvWaterQty.setText("" + resources[3]);

                            tvDie1.setText("" + body.getJSONArray("dice").getInt(0));
                            tvDie2.setText("" + body.getJSONArray("dice").getInt(1));

                            return;
                        }
                        if (type.equals("end-selection-timer")) {
                            // TODO create json object containing user's selections
                            new AppController();
                            ServerRequest serverRequest = new ServerRequest();
                            GameRequestLogic gameWSLogic = new GameRequestLogic(thisView, serverRequest, LoginScreen.getCurrentUser());
                            JSONObject selectedSpawners = new JSONObject();
                            gameWSLogic.startSelection(selectedSpawners, Lobby.getGameID());
                            return;
                        }
                        if (type.equals("score-update")) {
                            // TODO notify all users of updated scores/structures
                            return;
                        }
                        if (type.equals("material-wants")) {
                            // TODO notify user of wanted items
                            return;
                        }
                        if (type.equals("trade-request")) {
                            // TODO notify user of trade request and allow them to accept
                            return;
                        }
                        if (type.equals("trade-accept")) {
                            // TODO give the user the trade screen
                            return;
                        }
                        if (type.equals("trade-decline")) {
                            // TODO notify user that a trade they offered has been declined
                            return;
                        }
                        if (type.equals("trade-withdraw")) {
                            // TODO notify user that a trade they were offered has been withdrawn
                            return;
                        }
                        if (type.equals("trade-update")) {
                            // TODO show the user the updated trade offer and require them to reconfirm the trade
                            return;
                        }
                        if (type.equals("trade-confirm")) {
                            // TODO notify the user the other player has confirmed the trade.
                            return;
                        }
                        if (type.equals("game-over")) {
                            // TODO endgame logic
                            return;
                        }
                    } catch (JSONException e) {
                        Log.d(GameViewScreen.class.toString(), Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {

                }

                @Override
                public void onError(Exception ex) {

                }
            };
            gameClient.connect();
        } catch (URISyntaxException e) {
            Log.d(GameViewScreen.class.toString(), "Error converting JSON string to JSON");
            e.printStackTrace();
        }

        Button tradeButton = (Button) findViewById(R.id.activity_game_view_screen_button_trade);
        tradeButton.setOnClickListener(view -> startActivity(new Intent(view.getContext(), InitialTradeScreen.class)));

        Button buildButton = (Button) findViewById(R.id.activity_game_view_screen_button_build);
        buildButton.setOnClickListener(view -> startActivity(new Intent(view.getContext(), BuildScreen.class)));

        Button chatButton = (Button) findViewById(R.id.activity_game_view_screen_button_chat);
        chatButton.setOnClickListener(view -> {
            //start chat pop-up
            //createChatBoxDialog();
        });

    }

    public void initialTextUpdate() {
        try {
            JSONArray playerArray = jsonGameObject.getJSONObject("game").getJSONArray("players");

            int userCount = 0;
            boolean pastMe = false;
            for (int i = 0; i < playerArray.length(); i++) {
                JSONObject playerObject = (JSONObject) playerArray.get(i);
                Log.d("Currentplayer", playerObject.getString("username"));
                if (LoginScreen.getCurrentUser().getUsername().equals(playerObject.getString("username")) && !pastMe) {
                    tvCurrentUsername.setText("King " + playerObject.getString("username"));
                    Log.d("My username: ", playerObject.getString("username"));
                    pastMe = true;
                } else {
                    TextView currentTV = usernameTVArray[userCount];
                    userCount++;
                    currentTV.setText("King " + playerObject.getString("username"));
                    Log.d("Other username: ", playerObject.getString("username"));
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, "Error getting player array from game object");
        }
        tvFoodQty.setText("0");
        tvWoodQty.setText("0");
        tvStoneQty.setText("0");
        tvWaterQty.setText("0");
    }

    static SpawnerOptions getSpawnerData() {
        return spawnerOptions;
    }
    @Override
    public void logText(String s) {
        Log.d(TAG, s);
    }

    @Override
    public void switchActivity() {
        givenSpawners = logic.getSpawnersFromResponse();

        ArrayList<JSONObject> spawnersList = new ArrayList<JSONObject>();
        for (int i = 0; i < givenSpawners.length(); i++) {
            try {
                JSONObject spawner = givenSpawners.getJSONObject(i);
                Log.d(TAG, "spawner: " + spawner.toString());
                spawnersList.add(spawner);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        int stoneCounter = 0;
        int woodCounter = 0;
        int foodCounter = 0;
        int waterCounter = 0;
        for (JSONObject spawner : spawnersList){
            try {
                String spawnerMaterial = spawner.getString("material");
                int spawnerNumber = spawner.getInt("spawn-number");
                if (spawnerMaterial.equals("stone")){
                    stoneSpawners[stoneCounter++].setText("" + spawnerNumber);
                }
                if (spawnerMaterial.equals("wood")){
                    woodSpawners[woodCounter++].setText("" + spawnerNumber);
                }
                if (spawnerMaterial.equals("food")){
                    foodSpawners[foodCounter++].setText("" + spawnerNumber);
                }
                if (spawnerMaterial.equals("water")){
                    waterSpawners[waterCounter++].setText("" + spawnerNumber);
                }
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void makeToast(String s) {
        Log.d(TAG, "making Toast...");
        Toast.makeText(GameViewScreen.this, s, Toast.LENGTH_LONG).show();
    }


    /*
    public void createChatBoxDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View chatPopupView = getLayoutInflater().inflate(R.layout.activity_game_chat_popup, null);

        dialogBuilder.setView(chatPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }
     */
}