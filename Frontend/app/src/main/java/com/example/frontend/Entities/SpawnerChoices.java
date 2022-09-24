package com.example.frontend.Entities;

import java.util.ArrayList;

public class SpawnerChoices {

    private ArrayList<Integer> woodChoices;
    private ArrayList<Integer> foodChoices;
    private ArrayList<Integer> waterChoices;
    private ArrayList<Integer> stoneChoices;

    public SpawnerChoices(ArrayList<Integer> woodChoices, ArrayList<Integer> foodChoices, ArrayList<Integer> waterChoices, ArrayList<Integer> stoneChoices) {
        this.woodChoices = woodChoices;
        this.foodChoices = foodChoices;
        this.waterChoices = waterChoices;
        this.stoneChoices = stoneChoices;
    }

    /**
     * Sets user's spawner selection for wood
     * @param woodChoices
     */
    private void setWoodChoices(ArrayList<Integer> woodChoices){
        this.woodChoices = woodChoices;
    }

    /**
     * Gets user's spawner selection for wood
     * @return
     */
    public ArrayList<Integer> getWoodChoices() {
        return woodChoices;
    }

    /**
     * Sets user's spawner selection for food
     * @param foodChoices
     */
    private void setFoodChoices(ArrayList<Integer> foodChoices){
        this.foodChoices = foodChoices;
    }

    /**
     * Gets user's spawner selection for food
     * @return
     */
    public ArrayList<Integer> getFoodChoices() {
        return foodChoices;
    }

    /**
     * Sets user's spawner selection for water
     * @param waterChoices
     */
    private void setWaterChoices(ArrayList<Integer> waterChoices){
        this.waterChoices = waterChoices;
    }

    /**
     * Gets user's spawner selection for water
     * @return
     */
    public ArrayList<Integer> getWaterChoices() {
        return waterChoices;
    }

    /**
     * Sets user's spawner selection for stone
     * @param stoneChoices
     */
    private void setStoneChoices(ArrayList<Integer> stoneChoices){
        this.stoneChoices = stoneChoices;
    }

    /**
     * Gets user's spawn selection for stone
     * @return
     */
    public ArrayList<Integer> getStoneChoices() {
        return stoneChoices;
    }

}
