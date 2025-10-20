package com.example.SamuraiByte.gameStates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.SamuraiByte.entities.EnemyState;
import com.example.SamuraiByte.UI.HeartLevels;
import com.example.SamuraiByte.UI.PlayingStateUI;
import com.example.SamuraiByte.entities.Enemy;
import com.example.SamuraiByte.environments.CollisionManager;
import com.example.SamuraiByte.main.Game;
import com.example.SamuraiByte.main.GamePanel;
import com.example.SamuraiByte.entities.GameCharacter;
import com.example.SamuraiByte.entities.Player;
import com.example.SamuraiByte.environments.MapManager;
import com.example.SamuraiByte.interfaces.GameStateInterface;
import com.example.SamuraiByte.main.MainActivity;

import static com.example.SamuraiByte.utils.Utils.getHitboxCopyWithAddition;

public class PlayingState extends BaseState implements GameStateInterface {
    private GamePanel gamePanel;
    private float cameraX, cameraY;
    private PointF lastTouch;
    private boolean playerMove;
    private MapManager mapManager;
    private PlayingStateUI playingStateUI;
    private Player player;
    // For UI
    private Paint redPaint, yellowPaint;
    private boolean attackChecked;
    public PlayingState(Game game){
        super(game);

        this.initCameraValues();
        this.player = new Player();
        this.playerMove = false;
        this.mapManager = new MapManager(this, this.player);
        this.playingStateUI = new PlayingStateUI(this);
        this.yellowPaint = new Paint();
        this.yellowPaint.setColor(Color.YELLOW);
        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(3);
        this.attackChecked = false;
    }
    private void initCameraValues(){
        this.cameraX = MainActivity.GAME_WIDTH/2f;
        this.cameraY = MainActivity.GAME_HEIGHT/2f;
    }
    @Override
    public void update(double delta) {
        if (this.player.getHeartLevel() == HeartLevels.HEART_LEVEL_ZERO){
            this.game.setCurrentGameState(GameStates.DEAD);
        }
        if (this.playerMove){
            this.updatePlayerMove(delta);
        }
        this.player.update(delta);
        this.mapManager.setCameraValues(this.cameraX, this.cameraY);

        if (this.player.isAttacking())
            if (!this.attackChecked){
                CollisionManager.checkPlayerAttack();
                this.attackChecked = true;
            }

        for (GameCharacter character : this.mapManager.getCurrentMap().getCharactersArrayList())
            if (character.isActive()){
                if (character instanceof Enemy enemy){
                    RectF newPlayerHitbox = getHitboxCopyWithAddition(this.player.getHitbox(), this.cameraX, this.cameraY);
                    enemy.setPlayerHitbox(newPlayerHitbox);
                    enemy.update(delta);
                    if (enemy.getState() == EnemyState.PURSUING || enemy.getState() == EnemyState.RETREATING)
                        this.updateEnemyMove(delta, enemy);
                }
            }

        this.mapManager.update();
    }
    private void updateCharacterHitbox(GameCharacter character, RectF hitboxCopyX, RectF hitboxCopyY, PointF deltaSpeed){
        if (this.mapManager.canMoveHereX(hitboxCopyX)){
            if (character instanceof Player)
                this.cameraX+=deltaSpeed.x;
            else if (character instanceof Enemy e){
                e.updateXCharacterValues(deltaSpeed.x);
            }
        }
        if (this.mapManager.canMoveHereY(hitboxCopyY)){
            if (character instanceof Player)
                this.cameraY+=deltaSpeed.y;
            else if (character instanceof Enemy e){
                e.updateYCharacterValues(deltaSpeed.y);
            }
        }
    }
    private void updateEnemyMove(double deltaTime, Enemy enemy){
        PointF targetPos = null;
        if (enemy.getState() == EnemyState.PURSUING)
            targetPos = enemy.getTargetPos();
        else
            targetPos = enemy.getRetreatPos();

        PointF deltaSpeed = enemy.updateMove(deltaTime, targetPos, new PointF(enemy.getHitbox().left, enemy.getHitbox().top));
        RectF hitboxCopyY = getHitboxCopyWithAddition(enemy.getHitbox(), 0, deltaSpeed.y);
        RectF hitboxCopyX = getHitboxCopyWithAddition(enemy.getHitbox(), deltaSpeed.x, 0);

        this.updateCharacterHitbox(enemy, hitboxCopyX, hitboxCopyY, deltaSpeed);
    }

    private void updatePlayerMove(double deltaTime) {
        PointF deltaSpeed = this.player.updateMove(deltaTime, this.lastTouch, this.playingStateUI.getJoystickCenterPos());
        RectF hitboxCopyY = getHitboxCopyWithAddition(this.player.getHitbox(), 0+this.cameraX, deltaSpeed.y+this.cameraY);
        RectF hitboxCopyX = getHitboxCopyWithAddition(this.player.getHitbox(), deltaSpeed.x+this.cameraX, this.cameraY);

        this.updateCharacterHitbox(this.player, hitboxCopyX, hitboxCopyY, deltaSpeed);
    }

    @Override
    public void render(Canvas c) {
        this.mapManager.draw(c);
        this.playingStateUI.draw(c);
    }

    public void setPlayerMove(boolean playerMove, PointF lastTouchDiff){
        this.playerMove = playerMove;
        this.player.setPlayerMove(playerMove);
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

    public void setAttacking(boolean attacking) {
        this.player.setAttacking(attacking);
        if (!attacking)
            this.attackChecked = false;
    }
    public void resetGame(){
        this.mapManager.reset();
        this.playingStateUI.reset();
        this.player.setAttacking(false);
        this.player.setHealth(400);
        this.attackChecked = false;
        this.initCameraValues();
    }
}
