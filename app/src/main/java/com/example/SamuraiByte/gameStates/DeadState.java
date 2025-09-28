package com.example.SamuraiByte.gameStates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.SamuraiByte.interfaces.GameStateInterface;
import com.example.SamuraiByte.main.Game;

public class DeadState extends BaseState implements GameStateInterface {
    private Paint paint;
    public DeadState(Game game) {
        super(game);
        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
        this.paint.setTextSize(50);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
        c.drawColor(Color.BLACK);
        c.drawText("you died", 200, 300, this.paint);
        c.drawText("press everywhere to continue!", 200, 400, this.paint);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN -> this.game.setCurrentGameState(GameStates.END);
        }
    }
}
