package com.example.SamuraiByte.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.SamuraiByte.GameConstants;
import static com.example.SamuraiByte.GameConstants.SpriteSizes.SCALE_SIZE;
import static com.example.SamuraiByte.GameConstants.SpriteSizes.X_DRAW_OFFSET;

public class Weapon extends Entity{
    private Weapons weaponType;
    private GameCharacter weaponHolder;
    // temporary
    private Paint redPaint;

    public Weapon(PointF pos, int width, int height, Weapons weapon, GameCharacter weaponHolder){
        super(new PointF(0,0), 0, 0);

        this.weaponType = weapon;
        this.weaponHolder = weaponHolder;

        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(1);
    }
    public void update(double delta){
        PointF pos = this.getWeaponPos();
        float width = this.getWeaponWidth();
        float height = this.getWeaponHeight();

        this.hitbox = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
    }
    public void draw(Canvas c, float cameraX, float cameraY){
        c.drawBitmap(
                this.weaponType.getWeaponImage(this.weaponHolder.getFaceDir()),
                this.hitbox.left - cameraX,
                this.hitbox.top - cameraY,
                null
        );
        c.drawRect(
                this.hitbox.left - cameraX,
                this.hitbox.top - cameraY,
                this.hitbox.right - cameraX,
                this.hitbox.bottom -cameraY,
                redPaint
        );
    }

    private PointF getWeaponPos(){
        return switch (this.weaponHolder.getFaceDir()) {
            case GameConstants.WalkingDirection.DOWN -> new PointF(
                    this.weaponHolder.getHitbox().left + 0.5f * SCALE_SIZE,
                    this.weaponHolder.getHitbox().bottom + 0f * SCALE_SIZE
            );
            case GameConstants.WalkingDirection.UP -> new PointF(
                    this.weaponHolder.getHitbox().left - 1.5f*SCALE_SIZE,
                    this.weaponHolder.getHitbox().top - this.weaponType.getHeight() - 3f*SCALE_SIZE
            );
            case GameConstants.WalkingDirection.RIGHT -> new PointF(
                    this.weaponHolder.getHitbox().right + 0.5f*X_DRAW_OFFSET,
                    this.weaponHolder.getHitbox().bottom - this.weaponType.getWidth() - 1.5f * SCALE_SIZE
            );
            case GameConstants.WalkingDirection.LEFT -> new PointF(
                    this.weaponHolder.getHitbox().left - this.weaponType.getHeight() - 0.5f*X_DRAW_OFFSET,
                    this.weaponHolder.getHitbox().bottom - this.weaponType.getWidth() - 0.5f * SCALE_SIZE
            );
            default -> throw new IllegalStateException("Unexpected Direction");
        };
    }
    private float getWeaponWidth(){
        return switch (this.weaponHolder.getFaceDir()) {
            case GameConstants.WalkingDirection.DOWN, GameConstants.WalkingDirection.UP ->
                this.weaponType.getWidth();
            case GameConstants.WalkingDirection.RIGHT, GameConstants.WalkingDirection.LEFT ->
                this.weaponType.getHeight();
            default -> throw new IllegalStateException("Unexpected Direction");
        };
    }
    private float getWeaponHeight(){
        return switch (this.weaponHolder.getFaceDir()) {
            case GameConstants.WalkingDirection.DOWN, GameConstants.WalkingDirection.UP ->
                    this.weaponType.getHeight();
            case GameConstants.WalkingDirection.RIGHT, GameConstants.WalkingDirection.LEFT ->
                    this.weaponType.getWidth();
            default -> throw new IllegalStateException("Unexpected Direction");
        };
    }
}
