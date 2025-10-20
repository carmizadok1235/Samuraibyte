package com.example.SamuraiByte.environments;

import static com.example.SamuraiByte.GameConstants.Map.BACKGROUND_MAP;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.SamuraiByte.GameConstants;
import com.example.SamuraiByte.entities.Enemy;
import com.example.SamuraiByte.entities.Entity;
import com.example.SamuraiByte.entities.BlackSorcerer;
import com.example.SamuraiByte.entities.Player;
import com.example.SamuraiByte.gameStates.GameStates;
import com.example.SamuraiByte.gameStates.PlayingState;

import java.util.Arrays;

public class MapManager {
    private PlayingState playingState;
    private GameMap currentMap;
    private float cameraX, cameraY;
    private Player player;
    private Entity[] drawablesArray;
    private boolean drawablesInitialized;
    private Paint redPaint;

    public MapManager(PlayingState playingState, Player player){
        this.playingState = playingState;
        this.initMap();
        this.player = player;
        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(3);
        this.updateCollisionManager();
        this.initDrawablesArray();
    }

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
    }

    private void initMap() {
        int[][] spriteIds = GameConstants.Map.MAP_2;
        this.currentMap = new GameMap(spriteIds, Tiles.OUTSIDE, Levels.LEVEL1);
    }

    public void update(){
        if (this.currentMap.getLevel() == Levels.NOLEVEL)
            this.playingState.getGame().setCurrentGameState(GameStates.END);
        if (this.levelIsFinished()){
//            System.out.println("level is finished");
            this.drawablesInitialized = false;
            if (this.currentMap.getLevel().getLevelNumber().ordinal()+1 < LevelNumbers.values().length)
                this.currentMap.setLevel(LevelNumbers.values()[this.currentMap.getLevel().getLevelNumber().ordinal()+1]);
        }
        CollisionManager.setPosCamera(this.cameraX, this.cameraY);
        if (!this.drawablesInitialized){
            this.initDrawablesArray();
        }
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
        this.player.setLastCameraYValue(this.cameraY);
        Arrays.sort(this.drawablesArray);
    }
    private void initDrawablesArray() {
        this.drawablesArray = this.currentMap.getDrawableArray();
        this.drawablesArray[this.drawablesArray.length-1] = this.player;
        this.drawablesInitialized = true;
    }
    public void drawTiles(Canvas c){
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

    private void drawDrawablesArray(Canvas c) {
        for (Entity entity : this.drawablesArray) {
            if (entity instanceof Player p) {
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
