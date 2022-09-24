package com.example.frontend.Entities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class InGameUser implements IInGameUser {
    private String username;
    private int points;
    /**
     * structures[0] -> town
     * structures[0] -> house
     * structures[0] -> mine
     * structures[0] -> lumberyard
     * structures[0] -> garden
     * structures[0] -> well
     */
    private int[] structures = new int[6];

    /**
     * resources[0] -> stone
     * resources[1] -> wood
     * resources[2] -> food
     * resources[3] -> water
     */
    private int[] resources = new int[4];

    public InGameUser(String username){
        this.username = username;

        resources[0] = 0;
        resources[1] = 0;
        resources[2] = 0;
        resources[3] = 0;

        structures[0] = 0;
        structures[1] = 0;
        structures[2] = 0;
        structures[3] = 0;
        structures[4] = 0;
        structures[5] = 0;
    }

    @Override
    public String getUsername(){return username;}

    @Override
    public int getPoints(){return points;}

    @Override
    public void increasePoints(int val){points += val;}

    @Override
    public void decreasePoints(int val){points -= val;}

    @Override
    public int[] getResources() {return resources;}

    @Override
    public void increaseResources(String name, int val) {
        if (name.equals("stone"))
            resources[0]++;
        if (name.equals("wood"))
            resources[1]++;
        if (name.equals("food"))
            resources[2]++;
        if (name.equals("water"))
            resources[3]++;
    }

    @Override
    public void decreaseResources(String name, int val) {
        if (name.equals("stone"))
            resources[0] = Math.max(resources[0] - val, 0);
        if (name.equals("wood"))
            resources[0] = Math.max(resources[1] - val, 0);
        if (name.equals("food"))
            resources[0] = Math.max(resources[2] - val, 0);
        if (name.equals("water"))
            resources[0] = Math.max(resources[3] - val, 0);
    }

    @Override
    public int[] getStructures() {return structures;}

    @Override
    public void addStructure(String name){
        if (name.equals("town"))
            resources[0]++;
        if (name.equals("house"))
            resources[1]++;
        if (name.equals("mine"))
            resources[2]++;
        if (name.equals("lumberyard"))
            resources[3]++;
        if (name.equals("garden"))
            resources[4]++;
        if (name.equals("well"))
            resources[5]++;
    }
}
