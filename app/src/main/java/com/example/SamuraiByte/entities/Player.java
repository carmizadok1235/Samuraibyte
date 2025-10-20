package com.example.SamuraiByte.entities;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.example.SamuraiByte.GameConstants;
import com.example.SamuraiByte.UI.HeartLevels;
import com.example.SamuraiByte.main.MainActivity;

public class Player extends GameCharacter{
    private Paint redPaint;
    private float lastCameraYValue;
    private HeartLevels heartLevel;
    private boolean playerMove;
    public Player() {
        super(new PointF(MainActivity.GAME_WIDTH/2, MainActivity.GAME_HEIGHT/2), GameCharacters.BLUE_SAMURAI);

        this.weapon = new Weapon(new PointF(0, 0), 0, 0, Weapons.LANCE, this);
        this.attacking = false;
        this.base_speed = GameConstants.Walking.BASE_PLAYER_SPEED;
        this.playerMove = false;

        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(1);

        this.heartLevel = HeartLevels.HEART_LEVEL_FOUR;
        this.health = 400;
        this.damage = 100;
    }
    public void update(double delta){
        if (this.attacked && this.lastTimeAttacked != -1 && System.currentTimeMillis() - this.lastTimeAttacked >= 100)
            this.attacked = false;
        if (this.playerMove)
            this.updateAnimation();
        this.updateHeartLevel();
        this.weapon.update(delta);
    }

    public void updateHeartLevel() {
//        System.out.println(this.heartLevel);
//        System.out.println(this.health);
        switch (Math.round(this.health)){
            case 0 -> this.heartLevel = HeartLevels.HEART_LEVEL_ZERO;
            case 100 -> this.heartLevel = HeartLevels.HEART_LEVEL_ONE;
            case 200 -> this.heartLevel = HeartLevels.HEART_LEVEL_TWO;
            case 300 -> this.heartLevel = HeartLevels.HEART_LEVEL_THREE;
            case 400 -> this.heartLevel = HeartLevels.HEART_LEVEL_FOUR;
        }
    }
    public HeartLevels getHeartLevel(){
        return this.heartLevel;
    }
    public void setPlayerMove(boolean move){
        this.playerMove = move;
    }

    public void setLastCameraYValue(float lastCameraYValue) {
        this.lastCameraYValue = lastCameraYValue;
    }
    @Override
    public int compareTo(Entity other){
//        System.out.println("hitbox value with camera: " + (this.hitbox.top+this.lastCameraYValue));
//        System.out.println("other hitbox value: " + (other.hitbox.top));
        return Float.compare(this.hitbox.top+this.lastCameraYValue, other.hitbox.top);
    }
}
