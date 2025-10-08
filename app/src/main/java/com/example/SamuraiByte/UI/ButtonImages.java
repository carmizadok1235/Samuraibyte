package com.example.SamuraiByte.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.SamuraiByte.R;
import com.example.SamuraiByte.interfaces.BitmapMethods;
import com.example.SamuraiByte.main.MainActivity;

public enum ButtonImages implements BitmapMethods {
    START(R.drawable.button_start, 600, 140),
    SCOREBOARD(R.drawable.scoreboard_button, 600, 140),
    MENU(R.drawable.playing_button_menu, 280, 140),
    ATTACK_BUTTON(R.drawable.attack_button_spritesheet, 300, 150);

    private int width, height;
    private Bitmap pushed, unPushed;
    ButtonImages(int resId, int width, int height){
        options.inScaled = false;
        this.width = width;
        this.height = height;

        Bitmap spritesheet = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options);
        this.pushed = Bitmap.createBitmap(spritesheet, width/2, 0, width/2, height);
        this.unPushed = Bitmap.createBitmap(spritesheet, 0, 0, width/2, height);
    }
    public int getWidth(){ return this.width/2; };
    public int getHeight(){ return this.height; }
    public Bitmap getImage(boolean isPushed){
        return isPushed ? this.pushed : this.unPushed;
    }
}
