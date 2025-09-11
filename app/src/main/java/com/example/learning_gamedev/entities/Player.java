package com.example.learning_gamedev.entities;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.example.learning_gamedev.GameConstants;
import com.example.learning_gamedev.UI.HeartLevels;
import com.example.learning_gamedev.main.MainActivity;

public class Player extends GameCharacter{
//    private Weapon weapon;
//    private boolean attacking;
    // temporary
    private Paint redPaint;
    private float lastCameraYValue;
    private HeartLevels heartLevel;
    public Player() {
        super(new PointF(MainActivity.GAME_WIDTH/2, MainActivity.GAME_HEIGHT/2), GameCharacters.BLUE_SAMURAI);

        this.weapon = new Weapon(new PointF(0, 0), 0, 0, Weapons.LANCE, this);
        this.attacking = false;
        this.base_speed = GameConstants.Walking.BASE_PLAYER_SPEED;

        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(1);

        this.heartLevel = HeartLevels.HEART_LEVEL_FOUR;
        this.health = 4;
        this.damage = 1;
    }
    public void update(double delta){
        this.updateAnimation();
//        this.updateHeartLevel();
        this.weapon.update(delta);
    }

    public void updateHeartLevel() {
//        System.out.println(this.heartLevel);
//        System.out.println(this.health);
//        if (Math.round(this.health) == 0)
//            this.heartLevel = HeartLevels.HEART_LEVEL_ZERO;
//        else if (this.health == 1)
//            this.heartLevel = HeartLevels.HEART_LEVEL_ONE;
//        else if (this.health == 2)
//            this.heartLevel = HeartLevels.HEART_LEVEL_TWO;
//        else if (this.health == 3)
//            this.heartLevel = HeartLevels.HEART_LEVEL_THREE;
//        else if (this.health == 4)
//            this.heartLevel = HeartLevels.HEART_LEVEL_FOUR;
        switch (Math.round(this.health)){
            case 0 -> this.heartLevel = HeartLevels.HEART_LEVEL_ZERO;
            case 1 -> this.heartLevel = HeartLevels.HEART_LEVEL_ONE;
            case 2 -> this.heartLevel = HeartLevels.HEART_LEVEL_TWO;
            case 3 -> this.heartLevel = HeartLevels.HEART_LEVEL_THREE;
            case 4 -> this.heartLevel = HeartLevels.HEART_LEVEL_FOUR;
        }
    }
    public HeartLevels getHeartLevel(){
        return this.heartLevel;
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
    //    public void draw(Canvas c){
//        c.drawBitmap(this.shadow.getImage(),
//                this.hitbox.left,
//                this.hitbox.bottom - SHADOW_OFFSET,
//                null
//        );
//
//        c.drawBitmap(
//                this.character.getSprite(this.getAniType(), this.faceDir),
//                this.hitbox.left - GameConstants.SpriteSizes.X_DRAW_OFFSET,
//                this.hitbox.top - GameConstants.SpriteSizes.Y_DRAW_OFFSET,
//                null
//        );
//        c.drawRect(this.hitbox, this.redPaint);
//        if (this.attacking)
//            this.weapon.draw(c);
//    }
//    private int getAniType(){
//        if (this.attacking)
//            return 4;
//        return this.aniIndex;
//    }
//    public Weapon getWeapon(){
//        return this.weapon;
//    }
//    public void setAttacking(boolean attacking){
//        this.attacking = attacking;
//    }
//    public boolean isAttacking(){
//        return this.attacking;
//    }
}
