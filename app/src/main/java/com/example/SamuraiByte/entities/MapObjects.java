package com.example.SamuraiByte.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.SamuraiByte.GameConstants;
import com.example.SamuraiByte.R;
import com.example.SamuraiByte.interfaces.BitmapMethods;
import com.example.SamuraiByte.main.MainActivity;

public enum MapObjects implements BitmapMethods {
    HOUSE1(0, 0, 64, 48, 24, 18, R.drawable.buildings_atlas),
    HOUSE2(192,0, 64, 48, 24,18,R.drawable.buildings_atlas),
    TREE_SQUARE(0,0,752,688,0,0, R.drawable.tree_triangle2),
    MEDIUM_STONE1(0,0,32,32, 20,6, R.drawable.stone_tileset),
    MEDIUM_STONE2(0,32,32,32, 0,0,R.drawable.stone_tileset),
    LARGE_STONE(32,0,64,48, 22,20,R.drawable.stone_tileset),
    LONG_STONE(80,0,32,48, 0,0,R.drawable.stone_tileset),
    INVISIBLE_BLOCK1(0,0, 50, 1, 0, 41, R.drawable.pure_png),
    INVISIBLE_BLOCK2(0,0, 50, 1, 0, 46, R.drawable.pure_png),
    INVISIBLE_BLOCK3(0,0, 50, 1, 0, 6, R.drawable.pure_png),
    LEFT_EDGE_PIPE(176, 0, 16, 16, 5, 7, R.drawable.pipes_spritesheet),
    HORIZONTAL_PIPE(192, 0, 16, 16, 5, 7, R.drawable.pipes_spritesheet),
    RIGHT_EDGE_PIPE(208, 0, 16, 16, 5, 7, R.drawable.pipes_spritesheet),
    TOP_RIGHT_CONNECTOR_PIPE(192, 16, 16, 16, 5, 11, R.drawable.pipes_spritesheet),
    TOP_LEFT_CONNECTOR_PIPE(176, 16, 16, 16, 5, 11, R.drawable.pipes_spritesheet),
    BOTTOM_LEFT_CONNECTOR_PIPE(176, 32, 16, 16, 5, 11, R.drawable.pipes_spritesheet),
    BOTTOM_RIGHT_CONNECTOR_PIPE(192, 32, 16, 16, 5, 11, R.drawable.pipes_spritesheet),
    TOP_EDGE_PIPE(160, 0, 16, 16, 5, 7, R.drawable.pipes_spritesheet),
    DIAGONAL_PIPE(160, 16, 16, 16, 5, 11, R.drawable.pipes_spritesheet),
    BOTTOM_EDGE_PIPE(160, 32, 16, 16, 5, 11, R.drawable.pipes_spritesheet);


    Bitmap objectImage;
    private float baseX;
    private float baseHeight;
    MapObjects(int x, int y, int width, int height, float baseX, float baseHeight, int resId) {
        options.inScaled = false;
        Bitmap sprite = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options);
        this.objectImage = this.getBitmapResized(Bitmap.createBitmap(sprite, x, y, width, height));

        this.baseX = baseX * GameConstants.SpriteSizes.SCALE_SIZE;
        this.baseHeight = baseHeight * GameConstants.SpriteSizes.SCALE_SIZE;
    }

    public Bitmap getObjectImage(){
        return this.objectImage;
    }
    public int getWidth(){
        return this.objectImage.getWidth();
    }
    public int getHeight(){
        return this.objectImage.getHeight();
    }
    public float getBaseX(){
        return this.baseX;
    }
    public float getBaseHeight(){
        return this.baseHeight;
    }
}
