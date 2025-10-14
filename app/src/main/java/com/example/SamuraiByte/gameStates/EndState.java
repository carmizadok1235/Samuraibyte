package com.example.SamuraiByte.gameStates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import com.example.SamuraiByte.GameConstants;
import com.example.SamuraiByte.UI.GameImages;
import com.example.SamuraiByte.interfaces.GameStateInterface;
import com.example.SamuraiByte.main.DatabaseHandler;
import com.example.SamuraiByte.main.Game;
import com.example.SamuraiByte.main.MainActivity;
import com.example.SamuraiByte.utils.NameAndScore;

public class EndState extends BaseState implements GameStateInterface {
    private DatabaseHandler leaderboardHandler;
    private GameImages leaderboardBackground;
    private NameAndScore[] leaderboardValues;
    private float xLDB, yLDB;
    private Paint whitePaint;
    public EndState(Game game) {
        super(game);

        this.leaderboardHandler = new DatabaseHandler(MainActivity.getContext());
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
            float startY = this.yLDB+200;
            float lineHeight = 60;

            float nameColumnX = this.xLDB + 125;
            float lineColumnX = this.xLDB + 150 + 300;
            float scoreColumnX = this.xLDB + 150 + 900;

            for (int i = 0; i < this.leaderboardValues.length; i++){
                NameAndScore nameAndScore = this.leaderboardValues[i];
                String scoreFormat = "%." + this.getAfterDotLength(nameAndScore.getScore()) + "f";
                String nameText = String.format("%d | %s", i+1, nameAndScore.getName());
                String scoreText = String.format(scoreFormat, nameAndScore.getScore());

                float y = startY +(i*lineHeight);

                c.drawText(nameText, nameColumnX, y, this.whitePaint);
//                drawDashedLine(c, lineColumnX, y - 20, scoreColumnX - 80, y-20, this.whitePaint);
                c.drawText("-----------------------------------", lineColumnX, y, this.whitePaint);
                c.drawText(scoreText, scoreColumnX, y, this.whitePaint);
                /*String name = this.leaderboardValues[i-1].getName();
                double score = this.leaderboardValues[i-1].getScore();
                c.drawText(String.format("%d | %s \t\r-----------------------------------------------\t\r %.3f", i, name, score), this.xLDB+150, (this.yLDB+200)+(50*i), this.whitePaint);
                 */
            }
        }
    }
    private int getAfterDotLength(double score){
        if (score > 10000)
            return 0;
        if (score > 1000)
            return 1;
        if (score > 100)
            return 2;
        if (score > 10)
            return 3;
        return 4;
    }
    private void drawDashedLine(Canvas c, float startX, float startY, float endX, float endY, Paint paint){
        Paint dashPaint = new Paint(paint);
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setPathEffect(new DashPathEffect(new float[]{15, 15}, 0));

        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);
        c.drawPath(path, dashPaint);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN -> this.game.setCurrentGameState(GameStates.MENU);
        }
    }
    public void onNewScore(double newScore){
        String username = MainActivity.getUsername();
        if (!this.leaderboardHandler.checkInLeaderboardDB(username)){
            this.leaderboardHandler.addNewLeaderboardCourse(username, newScore);
            this.leaderboardValues = this.leaderboardHandler.getLeaderboardArray();
        }
        else if (newScore < this.leaderboardHandler.readLeaderboardScore(username)) {
            this.leaderboardHandler.updateLeaderboardCourse(username, newScore);
            this.leaderboardValues = this.leaderboardHandler.getLeaderboardArray();
        }
//        System.out.println(this.leaderboardHandler.readScores(username));
    }
}
