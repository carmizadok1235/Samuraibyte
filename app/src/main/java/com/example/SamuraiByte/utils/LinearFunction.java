package com.example.SamuraiByte.utils;

import android.graphics.PointF;

public class LinearFunction {
    float c, m;
    public LinearFunction(PointF a, PointF b){
        this.m = (a.y-b.y)/(a.x-b.x);
        this.c = a.y-(this.m*a.x);
    }
    public float getX(float y){
        return (y-this.c)/this.m;
    }
    public float getY(float x){
        return (this.m*x)+this.c;
    }
}
