package com.example.SamuraiByte.gameStates;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.example.SamuraiByte.GameConstants;
import com.example.SamuraiByte.R;
import com.example.SamuraiByte.UI.GameImages;
import com.example.SamuraiByte.interfaces.GameStateInterface;
import com.example.SamuraiByte.main.Game;
import com.example.SamuraiByte.main.LeaderboardHandler;
import com.example.SamuraiByte.main.MainActivity;

public class EndState extends BaseState implements GameStateInterface {
    private LeaderboardHandler leaderboardHandler;
    private GameImages leaderboardBackground;
    private float xLDB, yLDB;
    public EndState(Game game) {
        super(game);

        this.leaderboardHandler = new LeaderboardHandler(MainActivity.getContext());
        this.leaderboardBackground = GameImages.LEADERBOARD_BACKGROUND;
        this.xLDB = (MainActivity.GAME_WIDTH-this.leaderboardBackground.getImage().getWidth())/2f;
        this.yLDB = (MainActivity.GAME_HEIGHT-this.leaderboardBackground.getImage().getHeight())/2f;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
//        c.drawColor(Color.BLACK);
        c.drawBitmap(GameConstants.background, 0, 0, null);
        c.drawBitmap(this.leaderboardBackground.getImage(), this.xLDB, this.yLDB, null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN -> this.game.setCurrentGameState(GameStates.MENU);
        }
    }
    public void onNewScore(double newScore){
        String username = MainActivity.getUsername();
        if (!this.leaderboardHandler.checkInDB(username))
            this.leaderboardHandler.addNewCourse(username, newScore);
        else if (newScore < this.leaderboardHandler.readScores(username)) {
            this.leaderboardHandler.updateCourse(username, newScore);
        }
//        System.out.println(this.leaderboardHandler.readScores(username));
    }
}
