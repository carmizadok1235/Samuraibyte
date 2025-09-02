package com.example.learning_gamedev.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class MapObject extends Entity{
    private PointF pos;
    private MapObjects objectType;
    private Paint redPaint;

    public MapObject(PointF pos, MapObjects objectType){
        super(new PointF(pos.x, pos.y+objectType.getBaseX()), objectType.getWidth(), objectType.getBaseHeight());
        this.pos = pos;
        this.objectType = objectType;

        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(1);
    }
    public void update(double delta){

    }
    public void draw(Canvas c, float cameraX, float cameraY){
        c.drawBitmap(
                this.getImage(),
                this.hitbox.left-cameraX,
                this.hitbox.top-cameraY-this.objectType.getBaseX(),
                null
        );
        c.drawRect(this.hitbox.left-cameraX,
                this.hitbox.top-cameraY,
                this.hitbox.right-cameraX,
                this.hitbox.bottom-cameraY,
                this.redPaint
        );
    }
    public Bitmap getImage(){
        return this.objectType.getObjectImage();
    }
    public PointF getPos(){
        return this.pos;
    }

}
