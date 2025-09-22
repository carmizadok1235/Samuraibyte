package com.example.SamuraiByte.environments;

import android.graphics.PointF;

import com.example.SamuraiByte.GameConstants;
import com.example.SamuraiByte.entities.Entity;
import com.example.SamuraiByte.entities.GameCharacter;
import com.example.SamuraiByte.entities.MapObject;
import com.example.SamuraiByte.entities.MapObjects;

import java.util.ArrayList;

public class GameMap {
    private int[][] spriteIndexes;
    private ArrayList<MapObject> mapObjectArrayList;
    private ArrayList<GameCharacter> charactersArrayList;
    private Tiles tileType;
    private Levels level;

    public GameMap(int[][] spriteIndexes, Tiles tileType, Levels level){
        this.spriteIndexes = spriteIndexes;
        this.tileType = tileType;
        this.level = level;
        this.mapObjectArrayList = new ArrayList<>();
        this.charactersArrayList = new ArrayList<>();
        this.initMap();
    }
    private void initMap() {
        this.initTrees();
//        this.initStones();
        this.mapObjectArrayList.add(new MapObject(new PointF(1200,900), MapObjects.HOUSE2));
        this.mapObjectArrayList.add(new MapObject(new PointF(3000,1200), MapObjects.HOUSE2));
        this.mapObjectArrayList.add(new MapObject(new PointF(1100,2300), MapObjects.LARGE_STONE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1600,2600), MapObjects.MEDIUM_STONE1));
        this.mapObjectArrayList.add(new MapObject(new PointF(1400,2800), MapObjects.MEDIUM_STONE1));
        this.initCharacters();
    }

    private void initCharacters() {
        for (int i = 0; i < this.level.getNumberOfEnemies(); i++) {
            this.charactersArrayList.add(this.level.getEnemies()[i]);
        }
    }
//    public void addGladiator(float x, float y){
//        this.charactersArrayList.add(new BlackSorcerer(new PointF(x, y)));
//    }


    private void initTrees(){
        this.mapObjectArrayList.add(new MapObject(new PointF(0,0), MapObjects.TREE_SQUARE));
//        this.mapObjectArrayList.add(new MapObject(new PointF(0,0), MapObjects.TOP_LEFT_TREE_TRIANGLE));
//        this.mapObjectArrayList.add(new MapObject(new PointF(this.getMapWidth()-MapObjects.TOP_RIGHT_TREE_TRIANGLE.getWidth(),0), MapObjects.TOP_RIGHT_TREE_TRIANGLE));
//        this.mapObjectArrayList.add(new MapObject(new PointF(0,this.getMapHeight()-MapObjects.BOTTOM_LEFT_TREE_TRIANGLE.getHeight()), MapObjects.BOTTOM_LEFT_TREE_TRIANGLE));
//        this.mapObjectArrayList.add(new MapObject(new PointF(this.getMapWidth()-MapObjects.BOTTOM_RIGHT_TREE_TRIANGLE.getWidth(),this.getMapHeight()-MapObjects.BOTTOM_RIGHT_TREE_TRIANGLE.getHeight()), MapObjects.BOTTOM_RIGHT_TREE_TRIANGLE));
    }
    public Entity[] getDrawableArray(){
        Entity[] drawables = new Entity[this.getDrawablesLength()];
        int i = 0;
        for (MapObject mapObject : this.mapObjectArrayList){
            drawables[i] = mapObject;
            i++;
        }
        for (GameCharacter character : this.charactersArrayList){
            drawables[i] = character;
            i++;
        }

        return drawables;
    }
    public void setLevel(LevelNumbers levelNumber){
        this.level = Levels.getLevelByNumber(levelNumber);
        if (this.level != null)
            this.initCharacters();
    }
    private int getDrawablesLength(){
        return this.mapObjectArrayList.size() + this.charactersArrayList.size() + 1; // "+1" for the player
    }
    public int getArrayHeight(){
        return this.spriteIndexes.length;
    }
    public int getArrayWidth(){
        return this.spriteIndexes[0].length;
    }
    public int getSpriteByIndex(int y, int x){
        return this.spriteIndexes[y][x];
    }
    public int getMapWidth(){
        return this.getArrayWidth() * GameConstants.SpriteSizes.SIZE;
    }
    public int getMapHeight(){
        return this.getArrayHeight() * GameConstants.SpriteSizes.SIZE;
    }
    public Tiles getTileType(){
        return this.tileType;
    }
    public ArrayList<MapObject> getMapObjectArrayList(){
        return this.mapObjectArrayList;
    }
    public ArrayList<GameCharacter> getCharactersArrayList(){
        return this.charactersArrayList;
    }
    public Levels getLevel(){
        return this.level;
    }
}
