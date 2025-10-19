package com.example.SamuraiByte.environments;

import static com.example.SamuraiByte.GameConstants.SpriteSizes.HITBOX_SIZE;
//import static com.example.SamuraiByte.GameConstants.map_background;
//import static com.example.SamuraiByte.GameConstants.map_background;
import static com.example.SamuraiByte.GameConstants.Map.BACKGROUND_MAP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.SamuraiByte.GameConstants;
import com.example.SamuraiByte.R;
import com.example.SamuraiByte.entities.Enemy;
import com.example.SamuraiByte.entities.Entity;
import com.example.SamuraiByte.entities.BlackSorcerer;
import com.example.SamuraiByte.entities.Player;
import com.example.SamuraiByte.gameStates.GameStates;
import com.example.SamuraiByte.gameStates.PlayingState;
import com.example.SamuraiByte.main.MainActivity;

import java.util.Arrays;

public class MapManager {
    private PlayingState playingState;
    private GameMap currentMap;
//    private final CollisionManager collisionManager;
    private float cameraX, cameraY;
    private Player player;
    private Entity[] drawablesArray;
    private boolean drawablesInitialized;
    private Paint redPaint;
//    private Bitmap background;

    public MapManager(PlayingState playingState, Player player){
        this.playingState = playingState;
        this.initMap();
        this.player = player;
//        this.collisionManager = new CollisionManager();
//        this.objects.add(new MapObject(new PointF(200, 300), MapObjects.HOUSE_ONE));
//        this.drawablesArray = this.currentMap.getDrawableArray();
//        this.drawablesArray[this.drawablesArray.length-1] = this.player;
        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(3);
        this.updateCollisionManager();
        this.initDrawablesArray();
//        this.setEnemiesPlayer();
//        this.background = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.map_background);
    }

//    private void setEnemiesPlayer() {
//        for (GameCharacter gameCharacter : this.currentMap.getCharactersArrayList()){
//            if (gameCharacter instanceof Enemy enemy){
//                enemy.setPlayer(this.player);
//                System.out.println("setting enemy");
//            }
//        }
//    }

    public void setCameraValues(float cameraX, float cameraY){
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }
    public int getMapWidth(){
        return this.currentMap.getMapWidth();
    }
    public int getMapHeight(){
        return this.currentMap.getMapHeight();
    }

    public boolean canMoveHereY(RectF newPos){
//        if (y >= this.getMapHeight()-GameConstants.SpriteSizes.SIZE || y <= 0)
//            return false;
//
//        return true;
        return CollisionManager.canMoveHereY(newPos);
    }
    public boolean canMoveHereX(RectF newPos){
        return CollisionManager.canMoveHereX(newPos);
    }
    public GameMap getCurrentMap(){
        return this.currentMap;
    }
    private void updateCollisionManager(){
        CollisionManager.setMapBounds(this.getMapWidth(), this.getMapHeight());
        CollisionManager.setCharacterArrayList(this.currentMap.getCharactersArrayList());
        CollisionManager.setMapObjectArrayList(this.currentMap.getMapObjectArrayList());
        CollisionManager.setPlayer(this.player);
//        CollisionManager.setPosCamera(this.cameraX, this.cameraY);
    }

    private void initMap() {
        int[][] spriteIds = GameConstants.Map.MAP_2;
        this.currentMap = new GameMap(spriteIds, Tiles.OUTSIDE, Levels.LEVEL1);
//        this.currentMap = GameMap.;

//        this.initTrees();
//        this.initStones();
//        this.objects.add(new MapObject(new PointF(1200,1200), MapObjects.HOUSE1));
//        this.objects.add(new MapObject(new PointF(3000,1200), MapObjects.HOUSE2));
//        this.objects.add(new MapObject(new PointF(1100,2300), MapObjects.LARGE_STONE));
//        this.objects.add(new MapObject(new PointF(1600,2600), MapObjects.MEDIUM_STONE1));
//        this.objects.add(new MapObject(new PointF(1400,2800), MapObjects.MEDIUM_STONE1));

    }
//    private void initTrees(){
//        this.objects.add(new MapObject(new PointF(0,0), MapObjects.TOP_LEFT_TREE_TRIANGLE));
//        this.objects.add(new MapObject(new PointF(this.getMapWidth()-MapObjects.TOP_RIGHT_TREE_TRIANGLE.getWidth(),0), MapObjects.TOP_RIGHT_TREE_TRIANGLE));
//        this.objects.add(new MapObject(new PointF(0,this.getMapHeight()-MapObjects.BOTTOM_LEFT_TREE_TRIANGLE.getHeight()), MapObjects.BOTTOM_LEFT_TREE_TRIANGLE));
//        this.objects.add(new MapObject(new PointF(this.getMapWidth()-MapObjects.BOTTOM_RIGHT_TREE_TRIANGLE.getWidth(),this.getMapHeight()-MapObjects.BOTTOM_RIGHT_TREE_TRIANGLE.getHeight()), MapObjects.BOTTOM_RIGHT_TREE_TRIANGLE));
//    }
//    private void initStones(){
//        this.objects.add(new MapObject(new PointF()))
//    }

    public void update(){
        if (this.currentMap.getLevel() == Levels.NOLEVEL)
            this.playingState.getGame().setCurrentGameState(GameStates.END);
        if (this.levelIsFinished()){
            System.out.println("level is finished");
            this.drawablesInitialized = false;
            if (this.currentMap.getLevel().getLevelNumber().ordinal()+1 < LevelNumbers.values().length)
                this.currentMap.setLevel(LevelNumbers.values()[this.currentMap.getLevel().getLevelNumber().ordinal()+1]);
        }
//        System.out.println(this.player.getHitbox());
        CollisionManager.setPosCamera(this.cameraX, this.cameraY);
        if (!this.drawablesInitialized){
            this.initDrawablesArray();
        }
//        Arrays.sort(this.drawablesArray);
        this.sortDrawablesArray();
    }
    private boolean levelIsFinished(){
        for (Enemy enemy : this.currentMap.getLevel().getEnemies()){
            if (enemy.isActive())
                return false;
        }
        return true;
    }
    private void sortDrawablesArray() {
//        System.out.println("in sort method\n");
//        System.out.println(this.drawablesArray.length);
        this.player.setLastCameraYValue(this.cameraY);
        Arrays.sort(this.drawablesArray);
    }
    private void initDrawablesArray() {
        this.drawablesArray = this.currentMap.getDrawableArray();
        this.drawablesArray[this.drawablesArray.length-1] = this.player;
        this.drawablesInitialized = true;
    }
    public void drawTiles(Canvas c){
//        c.drawBitmap(this.currentMap.getTileType().getSprite(this.currentMap.getSpriteByIndex(0,0)), -1*GameConstants.SpriteSizes.SIZE-this.cameraX,-1*GameConstants.SpriteSizes.SIZE-this.cameraY, null);
//        Bitmap background = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.map_background);
//        c.drawBitmap(map_background, -2256-this.cameraX, -2064-this.cameraY, null);
        for (int i = 0; i < BACKGROUND_MAP.length; i++){
            for (int j = 0; j < BACKGROUND_MAP[i].length; j++){
                c.drawBitmap(this.currentMap.getTileType().getSprite(BACKGROUND_MAP[i][j]), j*GameConstants.SpriteSizes.SIZE-this.cameraX-1248, i*GameConstants.SpriteSizes.SIZE-this.cameraY-580, null);
            }
        }

        for (int i = 0; i < this.currentMap.getArrayHeight(); i++) {
            for (int j = 0; j < this.currentMap.getArrayWidth(); j++) {
                c.drawBitmap(this.currentMap.getTileType().getSprite(this.currentMap.getSpriteByIndex(i, j)), j* GameConstants.SpriteSizes.SIZE-this.cameraX, i*GameConstants.SpriteSizes.SIZE-this.cameraY, null);
            }
        }
    }
//    public void drawObjects(Canvas c){
//        ArrayList<MapObject> objects = this.currentMap.getMapObjectArrayList();
//        if (objects == null)
//            return;
//
//        for (MapObject mapObject : objects){
//            mapObject.draw(c, this.cameraX, this.cameraY);
//        }
//    }
    private void drawDrawablesArray(Canvas c) {
        for (Entity entity : this.drawablesArray) {
//            System.out.println(e.getClass());
//            c.drawRect(e.getHitbox(), this.redPaint);
            if (entity instanceof Player p) {
//                System.out.println(this.drawablesArray.length);
                p.draw(c, 0, 0);

            }
            else if (entity instanceof BlackSorcerer g){
//                c.drawCircle(g.getHitbox().left+HITBOX_SIZE/2f-this.cameraX,g.getHitbox().top+HITBOX_SIZE/2f-this.cameraY,g.getSightRadius(), this.redPaint);
//                c.drawCircle(g.getHitbox().left+HITBOX_SIZE/2f-this.cameraX,g.getHitbox().top+HITBOX_SIZE/2f-this.cameraY,g.getAttackingRadius(), this.redPaint);
                g.draw(c, this.cameraX, this.cameraY);
            }
            else if (entity.isActive())
                entity.draw(c, this.cameraX, this.cameraY);
        }
    }
    public void draw(Canvas c){
        this.drawTiles(c);
        this.drawDrawablesArray(c);
//        this.drawObjects(c);
    }
    public void reset(){
        for (Levels level : Levels.values()){
            level.initEnemies();
        }
        this.initMap();
        this.initDrawablesArray();
        this.updateCollisionManager();
    }
}
