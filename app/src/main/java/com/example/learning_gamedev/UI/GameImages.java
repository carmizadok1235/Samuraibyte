package com.example.learning_gamedev.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.learning_gamedev.R;
import com.example.learning_gamedev.interfaces.BitmapMethods;
import com.example.learning_gamedev.main.MainActivity;

public enum GameImages implements BitmapMethods {
    MENU_BACKGROUD(R.drawable.mainmenu_menubackground, false),
    SHADOW(R.drawable.shadow, true);

    private final Bitmap image;

    GameImages(int resId, boolean scale) {
        options.inScaled = false;
        if (!scale)
            this.image = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options);
        else
            this.image = getBitmapResized(BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options));
    }
    public Bitmap getImage(){
        return this.image;
    }
}
