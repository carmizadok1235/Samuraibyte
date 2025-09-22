package com.example.SamuraiByte.gameStates;

import com.example.SamuraiByte.main.Game;

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
