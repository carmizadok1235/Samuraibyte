package com.example.learning_gamedev.entities;

import static com.example.learning_gamedev.utils.Utils.getRandomBoolean;
import static com.example.learning_gamedev.utils.Utils.isInsideCircle;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.learning_gamedev.EnemyState;
import com.example.learning_gamedev.GameConstants;
import com.example.learning_gamedev.environments.CollisionManager;

public class Enemy extends GameCharacter{
    protected EnemyState state;
    private int attackSpeed = 5;
    private int waitForNextAttackSpeed = 20;
    private int attackTick;
    private int waitForNextAttackTick;
    protected int sightRadius;
    protected int attackingRadius;
    protected RectF playerHitbox;
    protected Player player;
    protected boolean alignedWithPlayer;
    protected boolean horizontalSide;
//    private long lastTimeCheck = System.currentTimeMillis();
//    private boolean retreat;
//    private PointF retreatPoint;
//    private boolean startedRetreatTimer;
    private long lastTime;

    public Enemy(PointF pos, GameCharacters character){
        super(pos, character);

        this.state = EnemyState.IDLE;
        this.playerHitbox = new RectF(0,0,0,0);
        this.sightRadius = 600;
        this.attackingRadius = 125;
        this.attackTick = 0;
        this.base_speed = GameConstants.Walking.BASE_ENEMY_SPEED;
        this.alignedWithPlayer = false;
        this.horizontalSide = getRandomBoolean(0.5f);
//        this.retreat = false;
//        this.startedRetreatTimer = false;
//        this.retreatPoint = new PointF();
        this.lastTime = -1;
    }

    public void setPlayerHitbox(RectF hitbox){
        playerHitbox.left = hitbox.left;
        playerHitbox.top = hitbox.top;
        playerHitbox.right = hitbox.right;
        playerHitbox.bottom = hitbox.bottom;
    }

    @Override
    public void update(double delta) {
//        PointF a = new PointF(this.hitbox.centerX(), this.hitbox.centerY());
//        PointF b = new PointF(this.playerPos.x+HITBOX_SIZE/2f, this.playerPos.y+HITBOX_SIZE/2f);
//        System.out.println("a: " + a + " b: " + b);
//        if (isInsideCircle(b, a, attackingRadius)){
//            this.state = EnemyState.ATTACKING;
//            this.attacking = true;
//        }
//        else if (isInsideCircle(b, a, this.sightRadius) && !CollisionManager.lineOfSightIntersectsWithObject(a,b)){
//            this.state = EnemyState.PURSUING;
//            this.attacking = false;
//        }
//        else{
//            this.state = EnemyState.IDLE;
//            this.resetAnimation();
//            this.attacking = false;
//        }
//        this.checkEnemyAlignedWithPlayer();
        this.updateEnemyState();
//        if (!this.alignedWithPlayer)
//            this.state = EnemyState.PURSUING;
//        else
//            this.state = EnemyState.ATTACKING;

//        if (this.state == EnemyState.PURSUING){
//            this.updateMove(delta, this.playerPos, new PointF(this.hitbox.left, this.hitbox.top));
//            this.updateAnimation();
//        }

        switch (this.state){
            case IDLE:
                break;
            case RETREATING:
                this.updateAnimation();
                this.retreatTimer();
                break;
            case PURSUING:
                this.updateAnimation();
//                PointF deltaSpeed = this.updateMove(delta, this.playerPos, new PointF(this.hitbox.left, this.hitbox.top));
//                this.hitbox.left+=deltaSpeed.x;
//                this.hitbox.right+=deltaSpeed.x;
//                this.hitbox.top+=deltaSpeed.y;
//                this.hitbox.bottom+=deltaSpeed.y;
                break;
            case ATTACKING:
                this.updateAttackDir();
                this.updateAttackingAnimation();
                break;
        }
        this.weapon.update(delta);
    }

    private void retreatTimer() {
        if (this.lastTime == -1)
            this.lastTime = System.currentTimeMillis();

        long currTime = System.currentTimeMillis();
        if (currTime - this.lastTime >= 500){
            this.state = EnemyState.IDLE;
            this.lastTime = -1;
        }
    }

    private void updateAttackDir() {
        float xDiff = this.hitbox.left - this.playerHitbox.left;
        float yDiff = this.hitbox.top - this.playerHitbox.top;

        if (Math.abs(xDiff) > Math.abs(yDiff)){
            if (xDiff > 0)
                this.faceDir = GameConstants.WalkingDirection.LEFT;
            else
                this.faceDir = GameConstants.WalkingDirection.RIGHT;
        }
        else{
            if (yDiff > 0)
                this.faceDir = GameConstants.WalkingDirection.UP;
            else
                this.faceDir = GameConstants.WalkingDirection.DOWN;
        }

    }

//    private void checkEnemyAlignedWithPlayer() {
//        this.alignedWithPlayer = this.weapon.getHitbox().intersects(
//                this.playerHitbox.left,
//                this.playerHitbox.top,
//                this.playerHitbox.right,
//                this.playerHitbox.bottom
//        );
//    }
    public PointF getRetreatPos(){
        return new PointF(2*this.hitbox.left-this.playerHitbox.left, 2*this.hitbox.top-this.playerHitbox.top);
    }

    public PointF getTargetPos(){
        PointF targetPos = new PointF(this.playerHitbox.left, this.playerHitbox.top);
        if (this.horizontalSide){
            if (this.playerHitbox.left < this.hitbox.left)
                targetPos.x = this.playerHitbox.left+125;
            else
                targetPos.x = this.playerHitbox.left-125;
        }
        else{
            if (this.playerHitbox.top < this.hitbox.top)
                targetPos.y = this.playerHitbox.top+125;
            else
                targetPos.y = this.playerHitbox.top-125;
        }
        return targetPos;
    }
    private void updateEnemyState(){
        if (this.state == EnemyState.RETREATING)
            return;
        PointF a = new PointF(this.hitbox.centerX(), this.hitbox.centerY());
        PointF b = new PointF(this.playerHitbox.centerX(), this.playerHitbox.centerY());

        if (isInsideCircle(b, a, this.attackingRadius)){
            if (!getRandomBoolean(0.002f))
                this.state = EnemyState.ATTACKING;
            else
                this.state = EnemyState.RETREATING;
//            System.out.println("hello world!");
//            this.attacking = true;
        }
        else if (isInsideCircle(b, a, this.sightRadius) && !CollisionManager.lineOfSightIntersectsWithObject(a,b)){
            if (!getRandomBoolean(0.002f))
                this.state = EnemyState.PURSUING;
            else
                this.state = EnemyState.RETREATING;
            this.attacking = false;
        }
        else{
            this.state = EnemyState.IDLE;
            this.resetAnimation();
            this.attacking = false;
        }
    }
    private void updateAttackingAnimation(){
        if (this.attacking){
            this.attackTick+=1;
            if (this.attackTick >= this.attackSpeed){
                this.attackTick = 0;
                this.attacking = false;
//                    this.getAniIndex();
            }
        }
        else{
            this.waitForNextAttackTick+=1;
            if (this.waitForNextAttackTick >= this.waitForNextAttackSpeed){
                this.waitForNextAttackTick = 0;
                this.attacking = true;
            }
        }
    }

    public boolean isAlignedWithPlayer() {
        return this.alignedWithPlayer;
    }

    public EnemyState getState(){
        return this.state;
    }
    public float getSightRadius(){
        return this.sightRadius;
    }
    public float getAttackingRadius(){
        return this.attackingRadius;
    }
}
