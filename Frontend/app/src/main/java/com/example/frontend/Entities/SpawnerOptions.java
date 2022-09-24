package com.example.frontend.Entities;

import java.util.ArrayList;

public class SpawnerOptions {

    private ArrayList<Integer> wood;
    private ArrayList<Integer> food;
    private ArrayList<Integer> water;
    private ArrayList<Integer> stone;

    public SpawnerOptions(ArrayList<Integer> wood, ArrayList<Integer> food, ArrayList<Integer> water, ArrayList<Integer> stone){
        this.wood = wood;
        this.food = food;
        this.water = water;
        this.stone = stone;
    }

    /**
     * Gets spawner options for wood
     * @return
     */
    public ArrayList<Integer>  getWood(){
        return wood;
    }

    /**
     * Gets spawner options for food
     * @return
     */
    public ArrayList<Integer>  getFood(){
        return food;
    }

    /**
     * Gets spawner options for water
     * @return
     */
    public ArrayList<Integer>  getWater(){
        return water;
    }

    /**
     * Gets spawner options for stone
     * @return
     */
    public ArrayList<Integer>  getStone(){
        return stone;
    }

}
