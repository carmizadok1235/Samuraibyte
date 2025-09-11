package com.example.learning_gamedev.gameStates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.learning_gamedev.EnemyState;
import com.example.learning_gamedev.UI.PlayingStateUI;
import com.example.learning_gamedev.entities.Enemy;
import com.example.learning_gamedev.entities.Weapons;
import com.example.learning_gamedev.environments.CollisionManager;
import com.example.learning_gamedev.main.Game;
import com.example.learning_gamedev.main.GamePanel;
import com.example.learning_gamedev.entities.GameCharacter;
import com.example.learning_gamedev.entities.BlackSorcerer;
import com.example.learning_gamedev.entities.Player;
import com.example.learning_gamedev.environments.MapManager;
import com.example.learning_gamedev.interfaces.GameStateInterface;

import static com.example.learning_gamedev.utils.Utils.getHitboxCopyWithAddition;

import java.util.ArrayList;

public class PlayingState extends BaseState implements GameStateInterface {
    private GamePanel gamePanel;
    private float cameraX, cameraY;
    private PointF lastTouch;
    private boolean playerMove;
    private MapManager mapManager;
//    private Entity[] drawablesArray;
//    private BuildingManager buildingManager;
    private PlayingStateUI playingStateUI;
    private Player player;
    private final ArrayList<BlackSorcerer> gladiators;
    private final Weapons axe;
    // For UI
    private Paint redPaint, yellowPaint;
    private boolean attackChecked;
//    private boolean drawablesInitialized;
    public PlayingState(Game game){
        super(game);

//        this.cameraX = 0;
//        this.cameraY = 0;
        this.player = new Player();
        this.playerMove = false;
        this.mapManager = new MapManager(this.player);
//        this.drawablesArray = this.mapManager.getCurrentMap().getDrawableArray();
//        this.buildingManager = new BuildingManager();
        this.playingStateUI = new PlayingStateUI(this);
        this.gladiators = new ArrayList<>();
        this.axe = Weapons.AXE;
//        for (int i = 0; i < 10; i++) {
//            this.addGladiator();
//        }
        this.yellowPaint = new Paint();
        this.yellowPaint.setColor(Color.YELLOW);
        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(3);
        this.attackChecked = false;
//        this.initDrawables();
//        this.buildDrawablesArray();
    }
    @Override
    public void update(double delta) {
//        if (!this.drawablesInitialized){
//            this.initDrawables();
//        }
//        System.out.println(this.player.getHitbox());
        if (this.playerMove){
            this.updatePlayerMove(delta);
            this.player.update(delta);
        }
        this.player.updateHeartLevel();
        this.mapManager.setCameraValues(this.cameraX, this.cameraY);
//        this.buildingManager.setCameraValues(this.cameraX, this.cameraY);
//        System.out.println("cameraX:" + this.cameraX + "cameraY: " + this.cameraY);

        if (this.player.isAttacking())
            if (!this.attackChecked){
                CollisionManager.checkPlayerAttack();
                this.attackChecked = true;
            }

        for (GameCharacter character : this.mapManager.getCurrentMap().getCharactersArrayList())
            if (character.isActive()){
                if (character instanceof Enemy enemy){
//                    RectF newPlayerHitbox = new RectF(
//                            this.player.getHitbox().left+this.cameraX,
//                            this.player.getHitbox().top+this.cameraY,
//                            this.player.getHitbox().right+this.cameraX,
//                            this.player.getHitbox().bottom+this.cameraY
//                    );
                    RectF newPlayerHitbox = getHitboxCopyWithAddition(this.player.getHitbox(), this.cameraX, this.cameraY);
                    enemy.setPlayerHitbox(newPlayerHitbox);
                    enemy.update(delta);
                    if (enemy.getState() == EnemyState.PURSUING || enemy.getState() == EnemyState.RETREATING)
                        this.updateEnemyMove(delta, enemy, newPlayerHitbox);
                }
            }

        this.mapManager.update();
    }
    private void updateCharacterHitbox(GameCharacter character, RectF hitboxCopyX, RectF hitboxCopyY, PointF deltaSpeed){
        if (this.mapManager.canMoveHereX(hitboxCopyX)){
            if (character instanceof Player)
                this.cameraX+=deltaSpeed.x;
            else{
                character.getHitbox().left+=deltaSpeed.x;
                character.getHitbox().right+=deltaSpeed.x;
            }
        }
        if (this.mapManager.canMoveHereY(hitboxCopyY)){
            if (character instanceof Player)
                this.cameraY+=deltaSpeed.y;
            else{
                character.getHitbox().top+=deltaSpeed.y;
                character.getHitbox().bottom+=deltaSpeed.y;
            }
        }
    }
    private void updateEnemyMove(double deltaTime, Enemy enemy, RectF newPlayerHitbox){
//        PointF newPlayerPos = new PointF(this.player.getHitbox().left+this.cameraX, this.player.getHitbox().top+this.cameraY);
//        enemy.setPlayerPos(newPlayerPos);
//        enemy.update(deltaTime);
//        PointF targetPos = new PointF(newPlayerPos.x, newPlayerPos.y);
//        if (getRandomBoolean()){
//            if (newPlayerPos.x < enemy.getHitbox().left)
//                targetPos.x = newPlayerPos.x+125;
//            else
//                targetPos.x = newPlayerPos.x-125;
//        }
//        else{
//            if (newPlayerPos.y < enemy.getHitbox().top)
//                targetPos.y = newPlayerPos.y+125;
//            else
//                targetPos.y = newPlayerPos.y-125;
//        }
        PointF targetPos = null;
        if (enemy.getState() == EnemyState.PURSUING)
            targetPos = enemy.getTargetPos();
        else
            targetPos = enemy.getRetreatPos();

        PointF deltaSpeed = enemy.updateMove(deltaTime, targetPos, new PointF(enemy.getHitbox().left, enemy.getHitbox().top));
//        RectF hitboxCopy = new RectF(
//                enemy.getHitbox().left+deltaSpeed.x,
//                enemy.getHitbox().top+deltaSpeed.y,
//                enemy.getHitbox().right+deltaSpeed.x,
//                enemy.getHitbox().bottom+deltaSpeed.y
//        );
        RectF hitboxCopyY = getHitboxCopyWithAddition(enemy.getHitbox(), 0, deltaSpeed.y);
        RectF hitboxCopyX = getHitboxCopyWithAddition(enemy.getHitbox(), deltaSpeed.x, 0);
//        if (this.mapManager.canMoveHereX(hitboxCopy)){
//            enemy.getHitbox().left+=deltaSpeed.x;
//            enemy.getHitbox().right+=deltaSpeed.x;
//        }
//        if (this.mapManager.canMoveHereY(hitboxCopy)){
//            enemy.getHitbox().top+=deltaSpeed.y;
//            enemy.getHitbox().bottom+=deltaSpeed.y;
//        }
        this.updateCharacterHitbox(enemy, hitboxCopyX, hitboxCopyY, deltaSpeed);
    }

//    private void initDrawables() {
//        this.drawablesArray = this.mapManager.getCurrentMap().getDrawableArray();
//        this.drawablesArray[this.drawablesArray.length-1] = this.player;
//        this.drawablesInitialized = true;
//    }
//
//    private void sortDrawablesArray() {
////        System.out.println("in sort method\n");
////        System.out.println(this.drawablesArray.length);
//        this.player.setLastCameraYValue(this.cameraY);
//        Arrays.sort(this.drawablesArray);
//    }

    // temporary
//    private void checkAttack(){
//        RectF weaponHitbox = new RectF();
//        weaponHitbox.left = this.player.getWeapon().getHitbox().left + this.cameraX;
//        weaponHitbox.top = this.player.getWeapon().getHitbox().top + this.cameraY;
//        weaponHitbox.right = this.player.getWeapon().getHitbox().right + this.cameraX;
//        weaponHitbox.bottom = this.player.getWeapon().getHitbox().bottom + this.cameraY;
//
//        for (GameCharacter c : this.mapManager.getCurrentMap().getCharactersArrayList())
//            if (weaponHitbox.intersects(
//                    c.getHitbox().left,
//                    c.getHitbox().top,
//                    c.getHitbox().right,
//                    c.getHitbox().bottom
//            ))
//                c.setActive(false);
//
//        this.attackChecked = true;
//    }
    // ^^^^^^^
    private void updatePlayerMove(double deltaTime) {
//        if (!this.playerMove){
//            return;
//        }

//        double ratio = Math.abs(this.lastTouchDiff.y / this.lastTouchDiff.x);
//
//        float angle = (float) Math.atan(ratio);

//        float xSpeed = (float) Math.cos(angle);
//        float ySpeed = (float) Math.sin(angle);
//        float xSpeed = calculateXSpeed(calculateAngle(this.lastTouch));
//        float ySpeed = calculateYSpeed(calculateAngle(this.lastTouch));
//
//        if (xSpeed > ySpeed){
//            if (this.lastTouch.x > 0) {
//                this.player.setFaceDir(GameConstants.WalkingDirection.RIGHT);
//            }
//            else {
//                this.player.setFaceDir(GameConstants.WalkingDirection.LEFT);
//            }
//        }
//        else{
//            if (this.lastTouch.y > 0) {
//                this.player.setFaceDir(GameConstants.WalkingDirection.DOWN);
//            }
//            else {
//                this.player.setFaceDir(GameConstants.WalkingDirection.UP);
//            }
//        }
//
//        if (this.lastTouch.x < 0) {
//            xSpeed *= -1;
//        }
//        if (this.lastTouch.y < 0) {
//            ySpeed *= -1;
//        }
//
//        float baseSpeed = (float) (deltaTime * GameConstants.Walking.BASE_SPEED);
//        float deltaX = xSpeed * baseSpeed;
//        float deltaY = ySpeed * baseSpeed;
        PointF deltaSpeed = this.player.updateMove(deltaTime, this.lastTouch, this.playingStateUI.getJoystickCenterPos());
//        RectF hitboxCopy = new RectF(
//                this.player.getHitbox().left+deltaSpeed.x+this.cameraX,
//                this.player.getHitbox().top+deltaSpeed.y+this.cameraY,
//                this.player.getHitbox().right+deltaSpeed.x+this.cameraX,
//                this.player.getHitbox().bottom+deltaSpeed.y+this.cameraY
//        );
        RectF hitboxCopyY = getHitboxCopyWithAddition(this.player.getHitbox(), 0+this.cameraX, deltaSpeed.y+this.cameraY);
        RectF hitboxCopyX = getHitboxCopyWithAddition(this.player.getHitbox(), deltaSpeed.x+this.cameraX, this.cameraY);
//        if (this.mapManager.canMoveHereY(hitboxCopy)){
//            this.cameraY += deltaSpeed.y;
//        }
//        if (this.mapManager.canMoveHereX(hitboxCopy)){
//            this.cameraX += deltaSpeed.x;
//        }
//        System.out.println(hitboxCopyY);
        this.updateCharacterHitbox(this.player, hitboxCopyX, hitboxCopyY, deltaSpeed);
    }

    @Override
    public void render(Canvas c) {
        this.mapManager.draw(c);
//        this.sortDrawablesArray();
//        this.drawDrawablesArray(c);
//        this.buildingManager.draw(c);
//        this.drawUI(c);
//        this.drawPlayer(c);
//        this.player.draw(c, 0, 0);

//        for (Gladiator gladiator : this.gladiators)
//            if (gladiator.isActive()) {
//                gladiator.draw(c, this.cameraX, this.cameraY);
//            }
        this.playingStateUI.draw(c);
//                this.drawCharacter(c, gladiator);
//        c.drawBitmap(this.axe.getWeaponImage(this.player.getFaceDir()), this.player.getWeapon().getHitbox().left, this.player.getWeapon().getHitbox().top, null);
//        c.drawRect(this.player.getWeapon().getHitbox(), redPaint);
    }

//    private void drawDrawablesArray(Canvas c) {
//        for (Entity entity : this.drawablesArray) {
////            System.out.println(e.getClass());
////            c.drawRect(e.getHitbox(), this.redPaint);
//            if (entity instanceof Player) {
////                System.out.println(this.drawablesArray.length);
//                Player p = (Player) entity;
//                p.draw(c, 0, 0);
//
//            }
//            else if (entity instanceof Gladiator g){
//                c.drawCircle(g.getHitbox().left-this.cameraX,g.getHitbox().top-this.cameraY,g.getRadius(), this.redPaint);
//                g.draw(c, this.cameraX, this.cameraY);
//            }
//            else if (entity.isActive())
//                entity.draw(c, this.cameraX, this.cameraY);
//        }
//    }
//    private void drawUI(Canvas c){
//        c.drawCircle(xCenter, yCenter, radius, redPaint);
//    }

//    private void drawPlayer(Canvas c){
//        c.drawBitmap(
//                this.player.getCharacter().getSprite(this.player.getAniIndex(), this.player.getFaceDir()),
//                this.player.getHitbox().left,
//                this.player.getHitbox().top,
//                null
//        );
//        c.drawRect(this.player.getHitbox(), this.redPaint);
//    }
//    private void drawCharacter(Canvas c, GameCharacter character){
//        c.drawBitmap(
//                character.getCharacter().getSprite(character.getAniIndex(),character.getFaceDir()),
//                character.getHitbox().left - this.cameraX - GameConstants.SpriteSizes.X_DRAW_OFFSET,
//                character.getHitbox().top - this.cameraY - GameConstants.SpriteSizes.Y_DRAW_OFFSET,
//                null
//        );
//        c.drawRect(character.getHitbox().left - this.cameraX,
//                character.getHitbox().top - this.cameraY,
//                character.getHitbox().right - this.cameraX,
//                character.getHitbox().bottom - this.cameraY,
//                redPaint
//        );
//    }
    public void setPlayerMove(boolean playerMove, PointF lastTouchDiff){
        this.playerMove = playerMove;
        if (this.playerMove)
            this.lastTouch = lastTouchDiff;
        else
            this.player.resetAnimation();
    }
    public Player getPlayer(){
        return this.player;
    }

    @Override
    public void touchEvents(MotionEvent event) {
        this.playingStateUI.touchEvents(event);
    }
//    public void addGladiator(){
//        this.gladiators.add(new BlackSorcerer(new PointF(this.player.getHitbox().left+this.cameraX, this.player.getHitbox().top+this.cameraY)));
//    }

    public void setAttacking(boolean attacking) {
        this.player.setAttacking(attacking);
        if (!attacking)
            this.attackChecked = false;
    }
}
