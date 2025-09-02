package com.example.learning_gamedev.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.learning_gamedev.GameConstants;
import com.example.learning_gamedev.main.MainActivity;
import com.example.learning_gamedev.R;
import com.example.learning_gamedev.interfaces.BitmapMethods;

public enum GameCharacters implements BitmapMethods {
    PLAYER(R.drawable.spritesheet),
    GLADIATOR(R.drawable.redgladiator_spritesheet);

//    private Bitmap spriteSheet;
    private Bitmap[][] sprites;
    GameCharacters(int resId) {
        options.inScaled = false;
        Bitmap spriteSheet = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options);
        this.sprites = new Bitmap[7][4];
        for (int i = 0; i < this.sprites.length; i++) {
            for (int j = 0; j < this.sprites[i].length; j++) {
                this.sprites[i][j] = this.getBitmapResized(Bitmap.createBitmap(spriteSheet, GameConstants.SpriteSizes.DEFAULT_SIZE *j, GameConstants.SpriteSizes.DEFAULT_SIZE *i,GameConstants.SpriteSizes.DEFAULT_SIZE, GameConstants.SpriteSizes.DEFAULT_SIZE));
            }
        }
    }

//    public Bitmap getSpriteSheet(){
//        return this.spriteSheet;
//    }

    public Bitmap getSprite(int row, int col){
        return this.sprites[row][col];
    }

}
