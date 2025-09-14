package com.example.learning_gamedev.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.learning_gamedev.GameConstants;
import com.example.learning_gamedev.main.MainActivity;
import com.example.learning_gamedev.R;
import com.example.learning_gamedev.interfaces.BitmapMethods;

public enum GameCharacters implements BitmapMethods {
//    PLAYER(R.drawable.spritesheet),
//    GLADIATOR(R.drawable.redgladiator_spritesheet),
    BLUE_SAMURAI(R.drawable.samurai_blue_spritesheet, R.drawable.samurai_blue_attacked_spritesheet),
    BLACK_SORCERER(R.drawable.sorcerer_black_spritesheet, R.drawable.sorcerer_black_attacked_spritesheet);

//    private Bitmap spriteSheet;
    private Bitmap[][] sprites;
    private Bitmap[][] attackedSprites;
    GameCharacters(int resId, int attackedResId) {
        options.inScaled = false;
        Bitmap spriteSheet = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options);
        Bitmap attackedSpriteSheet = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), attackedResId, options);
        this.sprites = new Bitmap[7][4];
        this.attackedSprites = new Bitmap[7][4];
        for (int i = 0; i < this.sprites.length; i++) {
            for (int j = 0; j < this.sprites[i].length; j++) {
                this.sprites[i][j] = this.getBitmapResized(Bitmap.createBitmap(spriteSheet, GameConstants.SpriteSizes.DEFAULT_SIZE *j, GameConstants.SpriteSizes.DEFAULT_SIZE *i,GameConstants.SpriteSizes.DEFAULT_SIZE, GameConstants.SpriteSizes.DEFAULT_SIZE));
                this.attackedSprites[i][j] = this.getBitmapResized(Bitmap.createBitmap(attackedSpriteSheet, GameConstants.SpriteSizes.DEFAULT_SIZE *j, GameConstants.SpriteSizes.DEFAULT_SIZE *i,GameConstants.SpriteSizes.DEFAULT_SIZE, GameConstants.SpriteSizes.DEFAULT_SIZE));
            }
        }
    }

//    public Bitmap getSpriteSheet(){
//        return this.spriteSheet;
//    }

    public Bitmap getSprite(int row, int col, boolean attacked){
        return attacked ? this.attackedSprites[row][col] : this.sprites[row][col];
    }

}
