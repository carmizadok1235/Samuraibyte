package com.example.SamuraiByte.interfaces;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface GameStateInterface {

    void update(double delta);
    void render(Canvas c);
    void touchEvents(MotionEvent event);
}
