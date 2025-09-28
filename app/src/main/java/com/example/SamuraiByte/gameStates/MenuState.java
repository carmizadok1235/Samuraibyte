package com.example.SamuraiByte.gameStates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.SamuraiByte.GameConstants;
import com.example.SamuraiByte.UI.ButtonImages;
import com.example.SamuraiByte.UI.CustomButton;
import com.example.SamuraiByte.UI.GameImages;
import com.example.SamuraiByte.interfaces.GameStateInterface;
import com.example.SamuraiByte.main.Game;
import com.example.SamuraiByte.main.MainActivity;

public class MenuState extends BaseState implements GameStateInterface {
    private Paint paint;
    private final CustomButton startButton;
    private int menuX = (MainActivity.GAME_WIDTH - GameImages.MENU_BACKGROUD.getImage().getWidth())/2;
    private int menuY = (MainActivity.GAME_HEIGHT - GameImages.MENU_BACKGROUD.getImage().getHeight())/5;
    private int buttonX = menuX + (GameImages.MENU_BACKGROUD.getImage().getWidth() - ButtonImages.START.getWidth())/2;
    private int buttonY = menuY + 100;
    public MenuState(Game game){
        super(game);

        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
        this.paint.setTextSize(60);
        this.startButton = new CustomButton(buttonX, buttonY, ButtonImages.START);
    }
    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
//        c.drawText("MENU", 1000, 300, this.paint);
        c.drawBitmap(GameConstants.background, 0, 0, null);

        c.drawBitmap(
                GameImages.MENU_BACKGROUD.getImage(),
                menuX,
                menuY,
                null
        );

        c.drawBitmap(
                this.startButton.getButtonImage(),
                this.startButton.getHitBox().left,
                this.startButton.getHitBox().top,
                null
        );
    }

    @Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (this.startButton.isInButton(new PointF(event.getX(), event.getY())))
                    this.startButton.setPushed(true);
                break;
            case MotionEvent.ACTION_UP:
                if (this.startButton.isInButton(new PointF(event.getX(), event.getY())))
                    this.game.setCurrentGameState(GameStates.PLAYING);
                this.startButton.setPushed(false);
                break;
        }
    }
}
