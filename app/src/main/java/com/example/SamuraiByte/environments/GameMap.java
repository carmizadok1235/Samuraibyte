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
    public void initMap() {
        this.initTrees();
        this.initInvisibleBlocks();
        this.mapObjectArrayList.add(new MapObject(new PointF(1200,900), MapObjects.HOUSE2));
        this.mapObjectArrayList.add(new MapObject(new PointF(3000,1200), MapObjects.HOUSE2));
        this.mapObjectArrayList.add(new MapObject(new PointF(1100,2300), MapObjects.LARGE_STONE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1600,2600), MapObjects.MEDIUM_STONE1));
        this.mapObjectArrayList.add(new MapObject(new PointF(1400,2800), MapObjects.MEDIUM_STONE1));
        this.initCharacters();
    }

    private void initInvisibleBlocks() {
        // Top-Left
        this.mapObjectArrayList.add(new MapObject(new PointF(1240,0), MapObjects.INVISIBLE_BLOCK1));
        this.mapObjectArrayList.add(new MapObject(new PointF(950,275), MapObjects.INVISIBLE_BLOCK2));
        this.mapObjectArrayList.add(new MapObject(new PointF(850,550), MapObjects.INVISIBLE_BLOCK3));
        this.mapObjectArrayList.add(new MapObject(new PointF(565,450), MapObjects.INVISIBLE_BLOCK2));
        this.mapObjectArrayList.add(new MapObject(new PointF(465,725), MapObjects.INVISIBLE_BLOCK3));
        this.mapObjectArrayList.add(new MapObject(new PointF(180,630), MapObjects.INVISIBLE_BLOCK2));
        this.mapObjectArrayList.add(new MapObject(new PointF(80,905), MapObjects.INVISIBLE_BLOCK3));
        this.mapObjectArrayList.add(new MapObject(new PointF(0,905), MapObjects.INVISIBLE_BLOCK3));

        // Top-Right
        this.mapObjectArrayList.add(new MapObject(new PointF(2976,0), MapObjects.INVISIBLE_BLOCK1));
        this.mapObjectArrayList.add(new MapObject(new PointF(3264,275), MapObjects.INVISIBLE_BLOCK2));
        this.mapObjectArrayList.add(new MapObject(new PointF(3372,550), MapObjects.INVISIBLE_BLOCK3));
        this.mapObjectArrayList.add(new MapObject(new PointF(3648,450), MapObjects.INVISIBLE_BLOCK2));
        this.mapObjectArrayList.add(new MapObject(new PointF(3756,725), MapObjects.INVISIBLE_BLOCK3));
        this.mapObjectArrayList.add(new MapObject(new PointF(4032,630), MapObjects.INVISIBLE_BLOCK2));
        this.mapObjectArrayList.add(new MapObject(new PointF(4140,905), MapObjects.INVISIBLE_BLOCK3));
        this.mapObjectArrayList.add(new MapObject(new PointF(4230,905), MapObjects.INVISIBLE_BLOCK3));

        // Bottom-Left
        this.mapObjectArrayList.add(new MapObject(new PointF(0, 3045), MapObjects.LEFT_EDGE_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(96, 3045), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(192, 3045), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(288, 3045), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(384, 3045), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(480, 3045), MapObjects.TOP_RIGHT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(480, 3141), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(480, 3237), MapObjects.BOTTOM_LEFT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(576, 3237), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(672, 3237), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(768, 3237), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(864, 3237), MapObjects.TOP_RIGHT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(864, 3333), MapObjects.BOTTOM_LEFT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(960, 3333), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1056, 3333), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1152, 3333), MapObjects.TOP_RIGHT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1152, 3429), MapObjects.BOTTOM_LEFT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1248, 3429), MapObjects.TOP_RIGHT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1248, 3525), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1248, 3621), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1248, 3717), MapObjects.BOTTOM_LEFT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1344, 3717), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1440, 3717), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1536, 3717), MapObjects.TOP_RIGHT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1536, 3816), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1536, 3909), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(1536, 4005), MapObjects.BOTTOM_EDGE_PIPE));

        // Bottom-Right
        this.mapObjectArrayList.add(new MapObject(new PointF(4416, 3045), MapObjects.RIGHT_EDGE_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(4320, 3045), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(4224, 3045), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(4128, 3045), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(4032, 3045), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3936, 3045), MapObjects.TOP_LEFT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3936, 3141), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3936, 3237), MapObjects.BOTTOM_RIGHT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3840, 3237), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3744, 3237), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3648, 3237), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3552, 3237), MapObjects.TOP_LEFT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3552, 3333), MapObjects.BOTTOM_RIGHT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3456, 3333), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3360, 3333), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3264, 3333), MapObjects.TOP_LEFT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3264, 3429), MapObjects.BOTTOM_RIGHT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3168, 3429), MapObjects.TOP_LEFT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3168, 3525), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3168, 3621), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3168, 3717), MapObjects.BOTTOM_RIGHT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(3072, 3717), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(2976, 3717), MapObjects.HORIZONTAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(2880, 3717), MapObjects.TOP_LEFT_CONNECTOR_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(2880, 3816), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(2880, 3909), MapObjects.DIAGONAL_PIPE));
        this.mapObjectArrayList.add(new MapObject(new PointF(2880, 4005), MapObjects.BOTTOM_EDGE_PIPE));
    }

    private void initCharacters() {
        for (int i = 0; i < this.level.getNumberOfEnemies(); i++) {
            this.charactersArrayList.add(this.level.getEnemies()[i]);
        }
    }

    private void initTrees(){
        this.mapObjectArrayList.add(new MapObject(new PointF(0,0), MapObjects.TREE_SQUARE));
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
        if (this.level != null){
            this.initCharacters();
        }
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
