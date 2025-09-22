package com.example.SamuraiByte.entities;

import static com.example.SamuraiByte.GameConstants.SpriteSizes.HITBOX_SIZE;
import static com.example.SamuraiByte.GameConstants.SpriteSizes.X_DRAW_OFFSET;
import static com.example.SamuraiByte.GameConstants.SpriteSizes.Y_DRAW_OFFSET;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

public class BlackSorcerer extends Enemy{
    private double lastTime;
//    private EnemyState state;
//    private int sightRadius;
    private Random rnd;
//    private PointF playerPos;
    public BlackSorcerer(PointF pos) {
        super(pos, GameCharacters.BLACK_SORCERER);
        this.lastTime = System.currentTimeMillis();
        this.rnd = new Random();

//        this.state = EnemyState.IDLE;
//        this.sightRadius = 600;
        this.weapon = new Weapon(new PointF(0,0),0,0, Weapons.LANCE, this);
        this.health = 800;
        this.damage = 20;
        RectF lifeBarRect = new RectF(pos.x-X_DRAW_OFFSET-10, pos.y-Y_DRAW_OFFSET-15, pos.x+HITBOX_SIZE+X_DRAW_OFFSET+10, pos.y-Y_DRAW_OFFSET);
        this.lifeBarFilled = new RectF(lifeBarRect);
        this.lifeBarStroke = new RectF(lifeBarRect);
    }

//    public void setPlayerPos(PointF pos){
//        playerPos.x = pos.x;
//        playerPos.y = pos.y;
//    }

//    public void update(double delta){
//        if (isInsideCircle(this.playerPos, new PointF(this.hitbox.left, this.hitbox.top),this.sightRadius)){
//            this.state = EnemyState.PURSUING;
//        }
//        else{
//            this.state = EnemyState.IDLE;
//        }
//
//        if (this.state == EnemyState.PURSUING){
//            this.updateMove(delta);
//            this.updateAnimation();
//        }
//        this.weapon.update(delta);
//    }
//    private void updateMove(double delta){
//        float xSpeed = calculateXSpeed(calculateAngle(this.playerPos));
//        float ySpeed = calculateYSpeed(calculateAngle(this.playerPos));
//        double currentTime = System.currentTimeMillis();
//        if (currentTime - this.lastTime >= 3000){
//            this.lastTime = currentTime;
//            this.faceDir = rnd.nextInt(4);
//        }
//
//        switch (this.faceDir){
//            case GameConstants.WalkingDirection.DOWN:
//                this.hitbox.top += delta * 300;
//                this.hitbox.bottom += delta * 300;
//                if (this.hitbox.top >= MainActivity.GAME_HEIGHT) {
//                    this.faceDir = GameConstants.WalkingDirection.UP;
//                }
//                break;
//            case GameConstants.WalkingDirection.UP:
//                this.hitbox.top -= delta * 300;
//                this.hitbox.bottom -= delta * 300;
//                if (this.hitbox.top <= 0) {
//                    this.faceDir = GameConstants.WalkingDirection.DOWN;
//                }
//                break;
//            case GameConstants.WalkingDirection.RIGHT:
//                this.hitbox.left += delta * 300;
//                this.hitbox.right += delta * 300;
//                if (this.hitbox.left >= MainActivity.GAME_WIDTH) {
//                    this.faceDir = GameConstants.WalkingDirection.LEFT;
//                }
//                break;
//            case GameConstants.WalkingDirection.LEFT:
//                this.hitbox.left -= delta * 300;
//                this.hitbox.right -= delta * 300;
//                if (this.hitbox.left <= 0) {
//                    this.faceDir = GameConstants.WalkingDirection.RIGHT;
//                }
//                break;
//        }
//    }
}
