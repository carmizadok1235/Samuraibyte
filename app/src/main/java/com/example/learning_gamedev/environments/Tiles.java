package com.example.learning_gamedev.environments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.learning_gamedev.GameConstants;
import com.example.learning_gamedev.main.MainActivity;
import com.example.learning_gamedev.R;
import com.example.learning_gamedev.interfaces.BitmapMethods;

public enum Tiles implements BitmapMethods {
    OUTSIDE(R.drawable.tileset_floor, 22, 26);

    private Bitmap[] sprites;
    Tiles(int resId, int widthTiles, int heightTiles) {
        options.inScaled = false;
        this.sprites = new Bitmap[widthTiles*heightTiles];
        Bitmap spriteSheet = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options);
        for (int i = 0; i < heightTiles; i++) {
            for (int j = 0; j < widthTiles; j++) {
                int index = i * widthTiles + j;
                this.sprites[index] = this.getBitmapResized(Bitmap.createBitmap(spriteSheet, GameConstants.SpriteSizes.DEFAULT_SIZE *j, GameConstants.SpriteSizes.DEFAULT_SIZE *i,GameConstants.SpriteSizes.DEFAULT_SIZE, GameConstants.SpriteSizes.DEFAULT_SIZE));
            }
        }
    }
    public Bitmap getSprite(int index){
        return this.sprites[index];
    }
}
