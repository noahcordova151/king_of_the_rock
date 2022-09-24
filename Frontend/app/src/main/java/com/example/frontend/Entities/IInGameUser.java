package com.example.frontend.Entities;

import java.util.HashMap;

public interface IInGameUser {
    public String getUsername();

    public int getPoints();
    public void increasePoints(int val);
    public void decreasePoints(int val);

    public int[] getResources();
    public void increaseResources(String name, int val);
    public void decreaseResources(String name, int val);

    public int[] getStructures();
    public void addStructure(String name);
}
