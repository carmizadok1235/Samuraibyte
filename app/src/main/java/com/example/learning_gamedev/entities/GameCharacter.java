package com.example.learning_gamedev.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.example.learning_gamedev.GameConstants;
import static com.example.learning_gamedev.GameConstants.Animation.SHADOW_OFFSET;
import static com.example.learning_gamedev.utils.Utils.calculateAngle;
import static com.example.learning_gamedev.utils.Utils.calculateXSpeed;
import static com.example.learning_gamedev.utils.Utils.calculateYSpeed;

import com.example.learning_gamedev.UI.GameImages;

public abstract class GameCharacter extends Entity{
    protected Weapon weapon;
    protected int health;
    protected boolean attacking;
    protected int aniTick, aniIndex;
    protected int faceDir;
    protected final GameCharacters character;
    protected GameImages shadow;
    protected int base_speed;
    private Paint redPaint;
    private long lastTime = System.currentTimeMillis();
    private int animationsPerSecond = 0;

    public GameCharacter(PointF pos, GameCharacters character){
        super(new PointF(pos.x, pos.y), GameConstants.SpriteSizes.HITBOX_SIZE, GameConstants.SpriteSizes.HITBOX_SIZE);
        this.faceDir = GameConstants.WalkingDirection.DOWN;
        this.character = character;

        this.weapon = null;
        this.health = 4;
        this.attacking = false;

        this.shadow = GameImages.SHADOW;

        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(1);
    }

    protected void updateAnimation(){
        long nowTime = System.currentTimeMillis();
        if (nowTime - lastTime >= 1000){
//            System.out.println("animations per sec: " + this.animationsPerSecond);
            lastTime = nowTime;
            this.animationsPerSecond = 0;
        }
        this.aniTick++;
        this.animationsPerSecond+=1;

        if (this.aniTick >= GameConstants.Animation.SPEED){
            this.aniTick = 0;
            this.aniIndex++;

            if (this.aniIndex >= GameConstants.Animation.AMOUNT)
                this.aniIndex = 0;
        }
    }
    public PointF updateMove(double deltaTime, PointF targetPos, PointF center){
        PointF diff = new PointF(targetPos.x - center.x,targetPos.y - center.y);
//        diff.x = targetPos.x - center.x;
//        diff.y = targetPos.y - center.y;
        float angle = calculateAngle(diff);

        float xSpeed = calculateXSpeed(angle);
        float ySpeed = calculateYSpeed(angle);

        if (xSpeed > ySpeed){
            if (diff.x > 0) {
                this.setFaceDir(GameConstants.WalkingDirection.RIGHT);
            }
            else {
                this.setFaceDir(GameConstants.WalkingDirection.LEFT);
            }
        }
        else{
            if (diff.y > 0) {
                this.setFaceDir(GameConstants.WalkingDirection.DOWN);
            }
            else {
                this.setFaceDir(GameConstants.WalkingDirection.UP);
            }
        }

        if (diff.x < 0) {
            xSpeed *= -1;
        }
        if (diff.y < 0) {
            ySpeed *= -1;
        }

        float baseSpeed = (float) (deltaTime * this.base_speed);
        PointF deltaSpeed = new PointF();
        deltaSpeed.x = xSpeed * baseSpeed;
        deltaSpeed.y = ySpeed * baseSpeed;

        return deltaSpeed;
    }
    public void resetAnimation(){
        this.aniTick = 0;
        this.aniIndex = 0;
    }
    public void draw(Canvas c, float cameraX, float cameraY){
        if (!this.isActive())
            return;
        c.drawBitmap(
                this.shadow.getImage(),
                this.hitbox.left-cameraX,
                this.hitbox.bottom-cameraY-SHADOW_OFFSET,
                null
        );

        c.drawBitmap(
                this.character.getSprite(this.getAniType(),this.faceDir),
                this.hitbox.left - cameraX - GameConstants.SpriteSizes.X_DRAW_OFFSET,
                this.hitbox.top - cameraY - GameConstants.SpriteSizes.Y_DRAW_OFFSET,
                null
        );
        c.drawRect(this.hitbox.left - cameraX,
                this.hitbox.top - cameraY,
                this.hitbox.right - cameraX,
                this.hitbox.bottom - cameraY,
                this.redPaint
        );
//        c.drawRect(this.hitbox, this.redPaint);
        if (this.attacking && this.weapon != null)
            this.weapon.draw(c, cameraX, cameraY);
    }

    public int getAniIndex() {
        return this.aniIndex;
    }

    public int getFaceDir() {
        return this.faceDir;
    }

    public void setFaceDir(int faceDir){
        this.faceDir = faceDir;
    }

    public GameCharacters getCharacter() {
        return this.character;
    }

    protected int getAniType(){
        if (this.attacking)
            return 4;
        return this.aniIndex;
    }
    public Weapon getWeapon(){
        return this.weapon;
    }
    public void setAttacking(boolean attacking){
//        if (attacking){
//            this.health--;
//            System.out.println(this.health);
//        }
        this.attacking = attacking;
    }
    public boolean isAttacking(){
        return this.attacking;
    }
}
