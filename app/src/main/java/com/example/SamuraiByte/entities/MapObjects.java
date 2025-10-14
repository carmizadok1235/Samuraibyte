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
//    TRIPLE_TREES_1(0, 0, 64, 47, 0,0, R.drawable.triple_trees_tileset),
//    TREE_1(32, 0, 32, 32, 0,0, R.drawable.trees_tileset),
//    TOP_LEFT_TREE_TRIANGLE(0,0,256,170,0,0,R.drawable.top_left_tree_triangle),
//    TOP_RIGHT_TREE_TRIANGLE(0,0,256,170,0,0,R.drawable.top_right_tree_triangle),
//    BOTTOM_LEFT_TREE_TRIANGLE(0,0,256,175,0,0,R.drawable.bottom_left_tree_triangle),
//    BOTTOM_RIGHT_TREE_TRIANGLE(0,0,256,175,0,0,R.drawable.bottom_right_tree_triangle),
    TREE_SQUARE(0,0,752,688,0,0, R.drawable.tree_triangle2),
    MEDIUM_STONE1(0,0,32,32, 20,6, R.drawable.stone_tileset),
    MEDIUM_STONE2(0,32,32,32, 0,0,R.drawable.stone_tileset),
    LARGE_STONE(32,0,64,48, 22,20,R.drawable.stone_tileset),
    LONG_STONE(80,0,32,48, 0,0,R.drawable.stone_tileset);


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
