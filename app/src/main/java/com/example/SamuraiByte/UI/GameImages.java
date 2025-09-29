package com.example.SamuraiByte.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.SamuraiByte.R;
import com.example.SamuraiByte.interfaces.BitmapMethods;
import com.example.SamuraiByte.main.MainActivity;

public enum GameImages implements BitmapMethods {
    MENU_BACKGROUD(R.drawable.mainmenu_menubackground, false, -1, false),
    LEADERBOARD_BACKGROUND(R.drawable.mainmenu_menubackground, true, 2, true),
    SHADOW(R.drawable.shadow, true, -1, false);

    private Bitmap image;

    GameImages(int resId, boolean scale, int scaleSize, boolean rotate) {
        options.inScaled = false;
        if (!scale)
            this.image = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options);
        else if (scaleSize > 0)
            this.image = getBitmapResized(BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options), scaleSize);
        else
            this.image = getBitmapResized(BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options));

        if (rotate)
            this.image = rotateBitmap(this.image, 90);
    }
    public Bitmap getImage(){
        return this.image;
    }
}
