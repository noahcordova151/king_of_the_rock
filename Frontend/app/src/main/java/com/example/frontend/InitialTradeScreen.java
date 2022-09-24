package com.example.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.Entities.IUser;
import com.example.frontend.Logic.InitialTradeLogic;
import com.example.frontend.Network.ServerRequest;
import com.example.frontend.SupportingClasses.AppController;
import com.example.frontend.SupportingClasses.IView;

import org.json.JSONException;

public class InitialTradeScreen extends AppCompatActivity implements IView {

    private String TAG = InitialTradeScreen.class.getSimpleName();
    private InitialTradeLogic logic;
    private boolean stoneBool, woodBool, foodBool, waterBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AppController();
        setContentView(R.layout.activity_initial_trade_screen);

        Log.d(TAG, "Current user: " + LoginScreen.getCurrentUser().toString());

        ServerRequest serverRequest = new ServerRequest();
        logic = new InitialTradeLogic(this, serverRequest, LoginScreen.getCurrentUser());

        Button submitButton = (Button) findViewById(R.id.activity_initial_trade_screen_button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    logic.submitTrade(stoneBool, woodBool, foodBool, waterBool, "" + Lobby.getGameID());
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        });

        Button waterButton = (Button) findViewById(R.id.activity_initial_trade_screen_button_water);
        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterBool = true;
                makeToast("Water selected");
            }
        });

        Button foodButton = (Button) findViewById(R.id.activity_initial_trade_screen_button_food);
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodBool = true;
                makeToast("Food selected");
            }
        });

        Button stoneButton = (Button) findViewById(R.id.activity_initial_trade_screen_button_stone);
        stoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoneBool = true;
                makeToast("Stone selected");
            }
        });

        Button woodButton = (Button) findViewById(R.id.activity_initial_trade_screen_button_wood);
        woodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                woodBool = true;
                makeToast("Wood selected");
            }
        });

        Button cancelButton = (Button) findViewById(R.id.activity_initial_trade_screen_button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GameViewScreen.class));
            }
        });
    }

    @Override
    public void logText(String s) {
        Log.d(TAG, s);
    }

    @Override
    public void switchActivity() {}

    @Override
    public void makeToast(String s) {
        Log.d(TAG, "making Toast...");
        Toast.makeText(InitialTradeScreen.this, s, Toast.LENGTH_LONG).show();
    }
}
