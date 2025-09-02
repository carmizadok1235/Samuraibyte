package com.example.learning_gamedev.main;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
//    private SurfaceHolder holder;
//    private float cameraX, cameraY;
//    private PointF lastTouchDiff;
//    private GameLoop gameLoop;
//    private TouchEvents touchEvents;
    private final Game game;
//    private boolean playerMove;
//    private MapManager mapManager;
//    private Player player;
//    private ArrayList<Gladiator> gladiators;

    public GamePanel(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
//        this.gameLoop = new GameLoop(this);
//        this.touchEvents = new TouchEvents(this);
        this.game = new Game(holder);
//        this.playerMove = false;
//        this.mapManager = new MapManager();
//        this.player = new Player();
//        this.gladiators = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            this.gladiators.add(new Gladiator(new PointF(500, 500)));
//        }
    }

//    public void render(){
//        Canvas c = holder.lockCanvas();
//        c.drawColor(Color.BLACK);
//
//        this.game.render();
//        this.mapManager.draw(c);
//        this.touchEvents.draw(c);
//        this.drawPlayer(c);
//        for (Gladiator gladiator : this.gladiators)
//            this.drawCharacter(c, gladiator);
//
//        holder.unlockCanvasAndPost(c);
//    }

//    private void drawPlayer(Canvas c){
//        c.drawBitmap(
//                this.player.getCharacter().getSprite(this.player.getAniIndex(), this.player.getFaceDir()),
//                this.player.getHitbox().left,
//                this.player.getHitbox().top,
//                null
//        );
//    }
//    private void drawCharacter(Canvas c, GameCharacter character){
//        c.drawBitmap(
//                character.getCharacter().getSprite(character.getAniIndex(),character.getFaceDir()),
//                character.getHitbox().left - this.cameraX,
//                character.getHitbox().top - this.cameraY,
//                null
//        );
//    }

//    public void update(double deltaTime){
//        this.updatePlayerMove(deltaTime);
//        this.mapManager.setCameraValues(this.cameraX, this.cameraY);
//        this.player.update(deltaTime, this.playerMove);
//        for (Gladiator gladiator : this.gladiators)
//            gladiator.update(deltaTime);
//    }

//    private void updatePlayerMove(double deltaTime) {
//        if (!this.playerMove){
//            return;
//        }
//
//        float baseSpeed = (float) (deltaTime * 300);
//        double ratio = Math.abs(this.lastTouchDiff.y / this.lastTouchDiff.x);
//
//        float angle = (float) Math.atan(ratio);
//
//        float xSpeed = (float) Math.cos(angle);
//        float ySpeed = (float) Math.sin(angle);
//
//        if (xSpeed > ySpeed){
//            if (this.lastTouchDiff.x > 0)
//                this.player.setFaceDir(GameConstants.WalkingDirection.RIGHT);
//            else
//                this.player.setFaceDir(GameConstants.WalkingDirection.LEFT);
//        }
//        else{
//            if (this.lastTouchDiff.y > 0)
//                this.player.setFaceDir(GameConstants.WalkingDirection.DOWN);
//            else
//                this.player.setFaceDir(GameConstants.WalkingDirection.UP);
//        }
//
//        if (this.lastTouchDiff.x < 0)
//            xSpeed *= -1;
//        if (this.lastTouchDiff.y < 0)
//            ySpeed *= -1;
//
//        float deltaX = xSpeed * baseSpeed;
//        float deltaY = ySpeed * baseSpeed;
//        if (this.mapManager.canMoveHereY(this.player.getHitbox().top + this.cameraY + deltaY)){
//            this.cameraY += deltaY;
//        }
//        if (this.mapManager.canMoveHereX(this.player.getHitbox().left + this.cameraX + deltaX)){
//            this.cameraX += deltaX;
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return touchEvents.onTouchEvent(event);
        return this.game.touchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.game.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
//    public void setPlayerMove(boolean playerMove, PointF lastTouchDiff){
//        this.playerMove = playerMove;
//        if (this.playerMove)
//            this.lastTouchDiff = lastTouchDiff;
//        else
//            this.player.resetAnimation();
//    }
}
