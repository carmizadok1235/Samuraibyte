package com.example.SamuraiByte.gameStates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.SamuraiByte.GameConstants;
import com.example.SamuraiByte.UI.GameImages;
import com.example.SamuraiByte.interfaces.GameStateInterface;
import com.example.SamuraiByte.main.Game;
import com.example.SamuraiByte.main.LeaderboardHandler;
import com.example.SamuraiByte.main.MainActivity;
import com.example.SamuraiByte.utils.NameAndScore;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class EndState extends BaseState implements GameStateInterface {
    private LeaderboardHandler leaderboardHandler;
    private GameImages leaderboardBackground;
    private NameAndScore[] leaderboardValues;
    private float xLDB, yLDB;
    private Paint whitePaint;
    public EndState(Game game) {
        super(game);

        this.leaderboardHandler = new LeaderboardHandler(MainActivity.getContext());
        this.leaderboardBackground = GameImages.LEADERBOARD_BACKGROUND;
        this.xLDB = (MainActivity.GAME_WIDTH-this.leaderboardBackground.getImage().getWidth())/2f;
        this.yLDB = (MainActivity.GAME_HEIGHT-this.leaderboardBackground.getImage().getHeight())/2f;
        this.leaderboardValues = leaderboardHandler.getLeaderboardArray();
        this.whitePaint = new Paint();
        this.whitePaint.setColor(Color.WHITE);
        this.whitePaint.setTextSize(50);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
//        c.drawColor(Color.BLACK);
        c.drawBitmap(GameConstants.background, 0, 0, null);
        c.drawBitmap(this.leaderboardBackground.getImage(), this.xLDB, this.yLDB, null);

        if (this.leaderboardValues != null){
            for (int i = 1; i <= this.leaderboardValues.length; i++){
                String name = this.leaderboardValues[i-1].getName();
                double score = this.leaderboardValues[i-1].getScore();
                c.drawText(String.format("%d | %s \t\r-----------------------------------------------\t\r %.3f", i, name, score), this.xLDB+150, (this.yLDB+200)+(50*i), this.whitePaint);
            }
        }
    }

    @Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN -> this.game.setCurrentGameState(GameStates.MENU);
        }
    }
    public void onNewScore(double newScore){
        String username = MainActivity.getUsername();
        if (!this.leaderboardHandler.checkInDB(username)){
            this.leaderboardHandler.addNewCourse(username, newScore);
            this.leaderboardValues = this.leaderboardHandler.getLeaderboardArray();
        }
        else if (newScore < this.leaderboardHandler.readScore(username)) {
            this.leaderboardHandler.updateCourse(username, newScore);
            this.leaderboardValues = this.leaderboardHandler.getLeaderboardArray();
        }
//        System.out.println(this.leaderboardHandler.readScores(username));
    }
}
