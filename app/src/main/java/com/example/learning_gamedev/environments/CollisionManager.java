package com.example.learning_gamedev.environments;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.learning_gamedev.entities.GameCharacter;
import com.example.learning_gamedev.entities.MapObject;
import com.example.learning_gamedev.entities.Player;
import com.example.learning_gamedev.utils.LinearFunction;

import static com.example.learning_gamedev.GameConstants.SpriteSizes.Y_DRAW_OFFSET;
import static com.example.learning_gamedev.GameConstants.SpriteSizes.X_DRAW_OFFSET;

import java.util.ArrayList;

public class CollisionManager {
    private static int mapBoundX, mapBoundY;
    private static ArrayList<GameCharacter> characterArrayList;
    private static ArrayList<MapObject> mapObjectArrayList;

    public static boolean lineOfSightIntersectsWithObject(PointF a, PointF b){
        LinearFunction line = new LinearFunction(a, b);
        for (MapObject mapObject : mapObjectArrayList){
            for (float i = Math.min(a.x, b.x); i < Math.max(a.x, b.x); i+=10){
                if (mapObject.getHitbox().contains(i, line.getY(i))){
//                    System.out.println("returning true");
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean hitboxNotIntersectsWithObjects(RectF newPos){
        for (MapObject mapObject : mapObjectArrayList){
            if (mapObject.getHitbox().intersects(
                    newPos.left, newPos.top, newPos.right, newPos.bottom
            ))
                return false;
        }
        return true;
    }
    public static boolean canMoveHereX(RectF newPosX){
        return (newPosX.left + (X_DRAW_OFFSET)) >= 0
                && (newPosX.right + (X_DRAW_OFFSET)) <= mapBoundX
                && hitboxNotIntersectsWithObjects(newPosX);
    }
    public static boolean canMoveHereY(RectF newPosY){
        return (newPosY.top + (Y_DRAW_OFFSET)) >= 0
                && (newPosY.bottom + (Y_DRAW_OFFSET)) <= mapBoundY
                && hitboxNotIntersectsWithObjects(newPosY);
    }
    public static void setMapBounds(int xBound, int yBound){
        mapBoundX = xBound;
        mapBoundY = yBound;
    }
    public static void setCharacterArrayList(ArrayList<GameCharacter> newCharactersArrayList){
        characterArrayList = newCharactersArrayList;
    }
    public static void setMapObjectArrayList(ArrayList<MapObject> newMapObjectArrayList){
        mapObjectArrayList = newMapObjectArrayList;
    }
    public static void checkPlayerAttack(Player player, float cameraX, float cameraY){
        RectF weaponHitbox = new RectF();
        weaponHitbox.left = player.getWeapon().getHitbox().left + cameraX;
        weaponHitbox.top = player.getWeapon().getHitbox().top + cameraY;
        weaponHitbox.right = player.getWeapon().getHitbox().right + cameraX;
        weaponHitbox.bottom = player.getWeapon().getHitbox().bottom + cameraY;

        for (GameCharacter c : characterArrayList)
            if (weaponHitbox.intersects(
                    c.getHitbox().left,
                    c.getHitbox().top,
                    c.getHitbox().right,
                    c.getHitbox().bottom
            ))
                c.setActive(false);

//        this.attackChecked = true;
    }
}
