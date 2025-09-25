package com.example.SamuraiByte.gameStates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.example.SamuraiByte.interfaces.GameStateInterface;
import com.example.SamuraiByte.main.Game;
import com.example.SamuraiByte.main.LeaderboardHandler;
import com.example.SamuraiByte.main.MainActivity;

public class EndState extends BaseState implements GameStateInterface {
    private LeaderboardHandler leaderboardHandler;
    public EndState(Game game) {
        super(game);

        this.leaderboardHandler = new LeaderboardHandler(MainActivity.getContext());

    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
        c.drawColor(Color.BLACK);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        this.game.setCurrentGameState(GameStates.MENU);
    }
}
