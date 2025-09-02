package com.example.learning_gamedev.entities;

import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.ArrayList;

public class BuildingManager {
    private ArrayList<MapObject> buildingsArrayList;
    float cameraX, cameraY;
    public BuildingManager(){
        this.buildingsArrayList = new ArrayList<>();
        this.buildingsArrayList.add(new MapObject(new PointF(200,300), MapObjects.HOUSE1));
    }
    public void setCameraValues(float cameraX, float cameraY){
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }
    public void addBuilding(MapObject building){
        this.buildingsArrayList.add(building);
    }
    public void draw(Canvas c){
        for (MapObject b : this.buildingsArrayList){
            b.draw(c, this.cameraX, this.cameraY);
        }
    }
}
