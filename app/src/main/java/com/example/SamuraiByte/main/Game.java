package com.example.SamuraiByte.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.SamuraiByte.gameStates.DeadState;
import com.example.SamuraiByte.gameStates.EndState;
import com.example.SamuraiByte.gameStates.GameStates;
import com.example.SamuraiByte.gameStates.MenuState;
import com.example.SamuraiByte.gameStates.PlayingState;

// the brain of the game.
public class Game {
    private SurfaceHolder holder;
    private GameLoop gameLoop;
    private StopWatch stopWatch;
    private double lastElapsedTime;
    private MenuState menuState;
    private PlayingState playingState;
    private EndState endState;
    private DeadState deadState;
    private GameStates currentGameState;
    public Game(SurfaceHolder holder){
        this.holder = holder;
        this.gameLoop = new GameLoop(this);
        this.stopWatch = new StopWatch();
        this.initGameStates();
        this.currentGameState = GameStates.MENU;
    }
    private void initGameStates(){
        this.menuState = new MenuState(this);
        this.playingState = new PlayingState(this);
        this.endState = new EndState(this);
        this.deadState = new DeadState(this);
    }
    public void startGameLoop(){
        this.gameLoop.startLoop();
    }
    public void render(){
        Canvas c = this.holder.lockCanvas();
        c.drawColor(Color.BLACK);

//        System.out.println(this.currentGameState);
        switch (this.currentGameState){
            case MENU:
                this.menuState.render(c);
                break;
            case PLAYING:
                this.playingState.render(c);
                break;
            case END:
                this.endState.render(c);
                break;
            case DEAD:
                this.deadState.render(c);
                break;
        }

        this.holder.unlockCanvasAndPost(c);
    }
    public void update(double delta){
        switch (this.currentGameState){
            case MENU:
                this.menuState.update(delta);
                break;
            case PLAYING:
                this.playingState.update(delta);
                break;
            case END:
                this.endState.update(delta);
                break;
            case DEAD:
                this.deadState.update(delta);
                break;
        }
    }

    public boolean touchEvent(MotionEvent event) {
        switch (this.currentGameState){
            case MENU:
                this.menuState.touchEvents(event);
                return true;
            case PLAYING:
                this.playingState.touchEvents(event);
                return true;
            case END:
                this.endState.touchEvents(event);
                break;
            case DEAD:
                this.deadState.touchEvents(event);
                break;
        }

        return false;
    }

    public GameStates getCurrentGameState() {
        return this.currentGameState;
    }

    public void setCurrentGameState(GameStates currentGameState) {
        if (this.currentGameState == GameStates.PLAYING && currentGameState == GameStates.END){
            this.endGame();
            System.out.println("time: " + this.lastElapsedTime);
            this.endState.onNewScore(this.lastElapsedTime);
        }
        else if (currentGameState == GameStates.PLAYING)
            this.stopWatch.start();
        else
            this.endGame();
        this.currentGameState = currentGameState;
//        this.currentGameState = currentGameState;
    }
    public void endGame(){
        this.lastElapsedTime = this.stopWatch.stop();
        this.playingState.resetGame();
        System.out.println("time: " + this.lastElapsedTime);
//        this.setCurrentGameState(GameStates.END);
//        this.currentGameState = GameStates.END;
//        this.playingState.resetGame();
    }
}
