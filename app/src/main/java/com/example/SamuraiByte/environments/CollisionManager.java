package com.example.SamuraiByte.environments;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.SamuraiByte.entities.Enemy;
import com.example.SamuraiByte.entities.GameCharacter;
import com.example.SamuraiByte.entities.MapObject;
import com.example.SamuraiByte.entities.Player;
import com.example.SamuraiByte.utils.LinearFunction;

import static com.example.SamuraiByte.GameConstants.SpriteSizes.Y_DRAW_OFFSET;
import static com.example.SamuraiByte.GameConstants.SpriteSizes.X_DRAW_OFFSET;
import static com.example.SamuraiByte.utils.Utils.getHitboxCopyWithAddition;

import java.util.ArrayList;

public class CollisionManager {
    private static int mapBoundX, mapBoundY;
    private static ArrayList<GameCharacter> characterArrayList;
    private static ArrayList<MapObject> mapObjectArrayList;
    private static Player player;
    private static float xPosCamera, yPosCamera;

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
    public static void checkPlayerAttack(){
        RectF weaponHitbox = getHitboxCopyWithAddition(player.getWeapon().getHitbox(), xPosCamera, yPosCamera);

        for (GameCharacter c : characterArrayList)
            if (weaponHitbox.intersects(
                    c.getHitbox().left,
                    c.getHitbox().top,
                    c.getHitbox().right,
                    c.getHitbox().bottom
            ))
                c.gotAttacked(player.getDamage());
    }
    public static void checkEnemyAttack(Enemy source){
        RectF playerHitbox = getHitboxCopyWithAddition(player.getHitbox(), xPosCamera, yPosCamera);
        if (source.getWeapon().getHitbox().intersects(
                playerHitbox.left,
                playerHitbox.top,
                playerHitbox.right,
                playerHitbox.bottom
        ))
            player.gotAttacked(source.getDamage());
    }
    public static void setPlayer(Player p){
        player = p;
    }
    public static void setPosCamera(float x, float y){
        xPosCamera = x;
        yPosCamera = y;
    }
}
