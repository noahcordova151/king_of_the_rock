package com.example.frontend;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.Entities.SpawnerChoices;
import com.example.frontend.Entities.SpawnerOptions;

import java.util.ArrayList;

public class SelectSpawners extends AppCompatActivity {

    private static SpawnerChoices spawnerChoices;
    private SpawnerOptions spawnerOptions = GameViewScreen.getSpawnerData();

    private ArrayList<Integer> woodOptions = spawnerOptions.getWood();
    private ArrayList<Integer> foodOptions = spawnerOptions.getFood();
    private ArrayList<Integer> waterOptions = spawnerOptions.getWater();
    private ArrayList<Integer> stoneOptions = spawnerOptions.getStone();

    private static ArrayList<Integer> woodChoices = new ArrayList<>();
    private static ArrayList<Integer> foodChoices = new ArrayList<>();
    private static ArrayList<Integer> waterChoices = new ArrayList<>();
    private static ArrayList<Integer> stoneChoices = new ArrayList<>();

    private boolean[] woodStates = new boolean[4];
    private boolean[] foodStates = new boolean[4];
    private boolean[] waterStates = new boolean[4];
    private boolean[] stoneStates = new boolean[4];

    private ImageButton btn_wood_1 = findViewById(R.id.activity_spawner_screen_spawner_wood_1);
    private ImageButton btn_wood_2 = findViewById(R.id.activity_spawner_screen_spawner_wood_2);
    private ImageButton btn_wood_3 = findViewById(R.id.activity_spawner_screen_spawner_wood_3);
    private ImageButton btn_wood_4 = findViewById(R.id.activity_spawner_screen_spawner_wood_4);

    private ImageButton btn_food_1 = findViewById(R.id.activity_spawner_screen_spawner_food_1);
    private ImageButton btn_food_2 = findViewById(R.id.activity_spawner_screen_spawner_food_2);
    private ImageButton btn_food_3 = findViewById(R.id.activity_spawner_screen_spawner_food_3);
    private ImageButton btn_food_4 = findViewById(R.id.activity_spawner_screen_spawner_food_4);

    private ImageButton btn_water_1 = findViewById(R.id.activity_spawner_screen_spawner_water_1);
    private ImageButton btn_water_2 = findViewById(R.id.activity_spawner_screen_spawner_water_2);
    private ImageButton btn_water_3 = findViewById(R.id.activity_spawner_screen_spawner_water_3);
    private ImageButton btn_water_4 = findViewById(R.id.activity_spawner_screen_spawner_water_4);

    private ImageButton btn_stone_1 = findViewById(R.id.activity_spawner_screen_spawner_stone_1);
    private ImageButton btn_stone_2 = findViewById(R.id.activity_spawner_screen_spawner_stone_2);
    private ImageButton btn_stone_3 = findViewById(R.id.activity_spawner_screen_spawner_stone_3);
    private ImageButton btn_stone_4 = findViewById(R.id.activity_spawner_screen_spawner_stone_4);

    /**
     * Updates the users current spawner selections
     *
     * @param type
     */
    private void updateChoices(String type) {
        switch (type) {
            case "wood":
                for (int i = 0; i < 4; i++) {
                    if (woodStates[i]) {
                        woodChoices.add(woodOptions.get(i));
                    } else {
                        woodChoices.remove(woodOptions.get(i));
                    }
                }
            case "food":
                for (int i = 0; i < 4; i++) {
                    if (foodStates[i]) {
                        foodChoices.add(foodOptions.get(i));
                    } else {
                        foodChoices.remove(foodOptions.get(i));
                    }
                }
            case "water":
                for (int i = 0; i < 4; i++) {
                    if (waterStates[i]) {
                        waterChoices.add(waterOptions.get(i));
                    } else {
                        waterChoices.remove(waterOptions.get(i));
                    }
                }
            case "stone":
                for (int i = 0; i < 4; i++) {
                    if (stoneStates[i]) {
                        stoneChoices.add(stoneOptions.get(i));
                    } else {
                        stoneChoices.remove(stoneOptions.get(i));
                    }
                }
        }
    }

    static SpawnerChoices getSpawnerChoices(){
        spawnerChoices = new SpawnerChoices(woodChoices, foodChoices, waterChoices, stoneChoices);
        return spawnerChoices;
    }

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_spawner_select_screen);

        for (int i = 0; i < 4; i++) {
            woodStates[i] = false;
            foodStates[i] = false;
            waterStates[i] = false;
            stoneStates[i] = false;
        }

        TextView[] woodSpawners = {
                findViewById(R.id.activity_spawner_screen_wood_1_roll_value),
                findViewById(R.id.activity_spawner_screen_wood_2_roll_value),
                findViewById(R.id.activity_spawner_screen_wood_3_roll_value),
                findViewById(R.id.activity_spawner_screen_wood_4_roll_value),
        };
        TextView[] foodSpawners = {
                findViewById(R.id.activity_spawner_screen_food_1_roll_value),
                findViewById(R.id.activity_spawner_screen_food_2_roll_value),
                findViewById(R.id.activity_spawner_screen_food_3_roll_value),
                findViewById(R.id.activity_spawner_screen_food_4_roll_value),
        };
        TextView[] waterSpawners = {
                findViewById(R.id.activity_spawner_screen_water_1_roll_value),
                findViewById(R.id.activity_spawner_screen_water_2_roll_value),
                findViewById(R.id.activity_spawner_screen_water_3_roll_value),
                findViewById(R.id.activity_spawner_screen_water_4_roll_value),
        };
        TextView[] stoneSpawners = {
                findViewById(R.id.activity_spawner_screen_stone_1_roll_value),
                findViewById(R.id.activity_spawner_screen_stone_2_roll_value),
                findViewById(R.id.activity_spawner_screen_stone_3_roll_value),
                findViewById(R.id.activity_spawner_screen_stone_4_roll_value),
        };
        // Set text for all the views
        for (int i = 0; i < 4; i++) {
            woodSpawners[i].setText(woodOptions.get(i));
            foodSpawners[i].setText(foodOptions.get(i));
            stoneSpawners[i].setText(stoneOptions.get(i));
            waterSpawners[i].setText(waterOptions.get(i));
        }

        btn_wood_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                woodStates[0] = !woodStates[0];
                view.setSelected(woodStates[0]);
                updateChoices("wood");
            }
        });

        btn_wood_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                woodStates[1] = !woodStates[1];
                view.setSelected(woodStates[1]);
                updateChoices("wood");
            }
        });

        btn_wood_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                woodStates[2] = !woodStates[2];
                view.setSelected(woodStates[2]);
                updateChoices("wood");
            }
        });

        btn_wood_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                woodStates[3] = !woodStates[3];
                view.setSelected(woodStates[3]);
                updateChoices("wood");
            }
        });

        btn_food_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodStates[0] = !foodStates[0];
                view.setSelected(foodStates[0]);
                updateChoices("food");
            }
        });

        btn_food_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodStates[1] = !foodStates[1];
                view.setSelected(foodStates[1]);
                updateChoices("food");
            }
        });

        btn_food_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodStates[2] = !foodStates[2];
                view.setSelected(foodStates[2]);
                updateChoices("food");
            }
        });

        btn_food_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodStates[3] = !foodStates[3];
                view.setSelected(foodStates[3]);
                updateChoices("food");
            }
        });

        btn_water_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterStates[0] = !waterStates[0];
                view.setSelected(waterStates[0]);
                updateChoices("water");
            }
        });

        btn_water_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterStates[1] = !waterStates[1];
                view.setSelected(waterStates[1]);
                updateChoices("water");
            }
        });

        btn_water_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterStates[2] = !waterStates[2];
                view.setSelected(waterStates[2]);
                updateChoices("water");
            }
        });

        btn_water_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterStates[3] = !waterStates[3];
                view.setSelected(waterStates[3]);
                updateChoices("water");
            }
        });

        btn_stone_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoneStates[0] = !stoneStates[0];
                view.setSelected(stoneStates[0]);
                updateChoices("stone");
            }
        });

        btn_stone_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoneStates[1] = !stoneStates[1];
                view.setSelected(stoneStates[1]);
                updateChoices("stone");
            }
        });

        btn_stone_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoneStates[2] = !stoneStates[2];
                view.setSelected(stoneStates[2]);
                updateChoices("stone");
            }
        });

        btn_stone_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoneStates[3] = !stoneStates[3];
                view.setSelected(stoneStates[3]);
                updateChoices("stone");
            }
        });

    }

}
