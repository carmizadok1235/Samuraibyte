package com.example.learning_gamedev;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class RndSquare {
    private PointF pos;
    private Paint paint;
    private int size;
    private int xDir = 1, yDir = 1;

    public RndSquare(PointF pos, int color, int size){
        this.pos = pos;
        this.paint = new Paint();
        this.paint.setColor(color);
        this.size = size;
    }

    public void draw(Canvas c){
        c.drawRect(pos.x, pos.y, pos.x+size, pos.y+size, paint);
    }
    public void move(double deltaTime){
        this.pos.x += xDir * deltaTime * 300;
        if (this.pos.x+size >= 1080 || this.pos.x <= 0)
            this.xDir *= -1;

        this.pos.y += yDir * deltaTime * 300;
        if (this.pos.y+size >= 2400 || this.pos.y <= 0)
            this.yDir *= -1;
    }
}
