package com.example.SamuraiByte.gameStates;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.SamuraiByte.interfaces.GameStateInterface;
import com.example.SamuraiByte.main.Game;
import com.example.SamuraiByte.main.MainActivity;
import com.example.SamuraiByte.R;

public class DeadState extends BaseState implements GameStateInterface {
    private Paint paint;
    private Bitmap youDiedText;
    private float xText, yText;
    public DeadState(Game game) {
        super(game);
        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
        this.paint.setTextSize(50);
        this.youDiedText = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.you_died_text);
        this.xText = (MainActivity.GAME_WIDTH-this.youDiedText.getWidth())/2f;
        this.yText = (MainActivity.GAME_HEIGHT-this.youDiedText.getHeight())/2f;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
        c.drawColor(Color.BLACK);
//        c.drawText("you died", 200, 300, this.paint);
//        c.drawText("press everywhere to continue!", 200, 400, this.paint);
        c.drawBitmap(this.youDiedText, this.xText, this.yText, null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN -> this.game.setCurrentGameState(GameStates.END);
        }
    }
}
