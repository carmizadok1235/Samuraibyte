package com.example.learning_gamedev.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.learning_gamedev.gameStates.GameStates;
import com.example.learning_gamedev.gameStates.MenuState;
import com.example.learning_gamedev.gameStates.PlayingState;

// the brain of the game.
public class Game {
    private SurfaceHolder holder;
    private GameLoop gameLoop;
    private MenuState menuState;
    private PlayingState playingState;
    private GameStates currentGameState;
    public Game(SurfaceHolder holder){
        this.holder = holder;
        this.gameLoop = new GameLoop(this);
        this.initGameStates();
        this.currentGameState = GameStates.MENU;
    }
    private void initGameStates(){
        this.menuState = new MenuState(this);
        this.playingState = new PlayingState(this);
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
        }

        return false;
    }

    public GameStates getCurrentGameState() {
        return this.currentGameState;
    }

    public void setCurrentGameState(GameStates currentGameState) {
        this.currentGameState = currentGameState;
    }
}
