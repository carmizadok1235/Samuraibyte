package com.example.SamuraiByte.UI;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

public class CustomButton {
    private RectF hitBox;
    private ButtonImages buttonImage;
    private boolean isPushed;
    // For multitouch
    private int pointerId;
    public CustomButton(int x, int y, ButtonImages buttonImage){
        this.hitBox = new RectF(x, y, x+buttonImage.getWidth(), y+buttonImage.getHeight());
        this.buttonImage = buttonImage;
        this.isPushed = false;
        this.pointerId = -1;
    }
    public RectF getHitBox(){ return this.hitBox; }
    public Bitmap getButtonImage(){ return this.buttonImage.getImage(this.isPushed); }
    public boolean isPushed(){ return this.isPushed; }
    public void setPushed(boolean pushed){ this.isPushed = pushed; }
    public boolean isInButton(PointF eventPos){
        return this.hitBox.contains(eventPos.x, eventPos.y);
    }
    public void setPointerId(int pointerId){
        this.pointerId = pointerId;
    }
    public int getPointerId(){
        return this.pointerId;
    }
}
