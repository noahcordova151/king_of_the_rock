package com.example.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.Entities.IUser;

/**
 * Class for the logic of the dashboard screen of a non-admin type user
 *
 * @author Dan Rosenhamer
 */

public class UserDashboard extends AppCompatActivity {
    private String TAG = UserDashboard.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        Log.d(TAG, "currentUser: " + LoginScreen.getCurrentUser().toString());

        Button buttonToFindUser = findViewById(R.id.user_dashboard_find_user_button);
        buttonToFindUser.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SearchForUserScreen.class)));

        Button buttonToJoinGame = findViewById(R.id.user_dashboard_join_game_button);
        buttonToJoinGame.setOnClickListener(view -> startActivity(new Intent(view.getContext(), JoinGameScreen.class)));

        Button buttonToHostGame = findViewById(R.id.user_dashboard_host_game_button);
        buttonToHostGame.setOnClickListener(view -> {
            LoginScreen.getCurrentUser().setIsHost(true);
            startActivity(new Intent(view.getContext(), Lobby.class));
        });
    }
}
