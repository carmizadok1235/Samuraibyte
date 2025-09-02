package com.example.learning_gamedev.gameStates;

import com.example.learning_gamedev.main.Game;

public abstract class BaseState {
    protected Game game;
    public BaseState(Game game){
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    public void setGameState(GameStates gameState){
        this.game.setCurrentGameState(gameState);
    }
}
