package com.example.SamuraiByte.utils;

import android.graphics.PointF;
import android.graphics.RectF;

public final class Utils {
    public static float calculatePythagorean(float a, float b){
        return (float)Math.sqrt(Math.pow((double)a, 2) + Math.pow((double)b, 2));
    }
    public static boolean isInsideCircle(PointF pos, PointF circleCenterPos, float radius){
        float a = Math.abs(pos.x - circleCenterPos.x);
        float b = Math.abs(pos.y - circleCenterPos.y);

//        float c = (float)Math.sqrt(Math.pow((double)a, 2) + Math.pow((double)b, 2));
        float c = calculatePythagorean(a, b);
        return c <= radius;
    }
    public static float calculateAngle(PointF lastTouchDiff){
        double ratio = Math.abs(lastTouchDiff.y / lastTouchDiff.x);

        return (float) Math.atan(ratio);
    }
    public static float calculateXSpeed(float angle){
        return (float) Math.cos(angle);
    }
    public static float calculateYSpeed(float angle){
        return (float) Math.sin(angle);
    }
    public static RectF getHitboxCopyWithAddition(RectF hitbox, float xAddition, float yAddition){
        return new RectF(
                hitbox.left+xAddition,
                hitbox.top+yAddition,
                hitbox.right+xAddition,
                hitbox.bottom+yAddition
        );
    }
    public static boolean getRandomBoolean(float chance){
        if (chance < 0 || chance > 1)
            throw new IllegalArgumentException("0 < chance < 1");
        return Math.random() < chance;
    }
}
