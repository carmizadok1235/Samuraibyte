package com.example.SamuraiByte.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.SamuraiByte.R;
import com.example.SamuraiByte.interfaces.BitmapMethods;
import com.example.SamuraiByte.main.MainActivity;
import com.example.SamuraiByte.GameConstants;

public enum Weapons implements BitmapMethods {
    AXE(R.drawable.axe),
    BIG_SWORD(R.drawable.big_sword),
    LANCE(R.drawable.lance);

    private Bitmap weaponImage;
    Weapons(int resId) {
        options.inScaled = false;
        this.weaponImage = this.getBitmapResized(BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options));
    }

    public Bitmap getWeaponImage(int dir){
        return switch (dir){
            case GameConstants.WalkingDirection.DOWN -> this.weaponImage;
            case GameConstants.WalkingDirection.UP -> this.rotateBitmap(this.weaponImage, 180);
            case GameConstants.WalkingDirection.LEFT -> this.rotateBitmap(this.weaponImage, 90);
            case GameConstants.WalkingDirection.RIGHT -> this.rotateBitmap(this.weaponImage, -90);
            default -> throw new IllegalStateException("Unexpected Direction");
        };
    }
    public int getWidth(){
        return this.weaponImage.getWidth();
    }
    public int getHeight(){
        return this.weaponImage.getHeight();
    }
}
