package com.example.SamuraiByte.interfaces;

import android.graphics.Canvas;

public interface EntityInterface {

    void update(double delta);
    void draw(Canvas c, float cameraX, float cameraY);
}
