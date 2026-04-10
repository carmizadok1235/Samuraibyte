package com.example.SamuraiByte.main;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SamuraiByte.utils.LeaderboardAdapter;
import com.example.SamuraiByte.R;
import com.example.SamuraiByte.gameStates.GameStates;
import com.example.SamuraiByte.utils.NameAndScore;

public class MainActivity extends AppCompatActivity {
    private static Context gameContext;
    public static int GAME_WIDTH, GAME_HEIGHT;
    private static String username;
    private static GamePanel gamePanel;
    private static View leaderboardView;
    private static boolean leaderboardShown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameContext = this;
        leaderboardShown = false;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        GAME_WIDTH = dm.widthPixels;
        GAME_HEIGHT = dm.heightPixels;

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            this.getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//        }

        EdgeToEdge.enable(this);
        username = getIntent().getStringExtra("USERNAME");
        System.out.println(username);

        FrameLayout frameLayout = new FrameLayout(this);

        gamePanel = new GamePanel(this);
        frameLayout.addView(gamePanel);

        leaderboardView = LayoutInflater.from(this).inflate(R.layout.leaderboard_overlay, frameLayout, false);
        leaderboardView.setVisibility(View.GONE);
        frameLayout.addView(leaderboardView);

        setContentView(frameLayout);
    }
    public void showLeaderboard(NameAndScore[] scores) {
        runOnUiThread(() -> {
            RecyclerView recyclerView = leaderboardView.findViewById(R.id.leaderboard_recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new LeaderboardAdapter(scores));


            leaderboardView.setVisibility(View.VISIBLE);
            leaderboardShown = true;
        });
    }
    public void hideLeaderboard() {
        runOnUiThread(() -> {
            leaderboardView.setVisibility(View.GONE);
            gamePanel.getGame().setCurrentGameState(GameStates.MENU);
            leaderboardShown = false;
        });
    }

    public static Context getContext(){
        return gameContext;
    }
    public static String getUsername(){
        return username;
    }
    public static boolean isLeaderboardShown(){
        return leaderboardShown;
    }
}