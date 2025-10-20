package com.example.SamuraiByte.entities;

import static com.example.SamuraiByte.GameConstants.SpriteSizes.HITBOX_SIZE;
import static com.example.SamuraiByte.GameConstants.SpriteSizes.X_DRAW_OFFSET;
import static com.example.SamuraiByte.GameConstants.SpriteSizes.Y_DRAW_OFFSET;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

public class BlackSorcerer extends Enemy{
    public BlackSorcerer(PointF pos) {
        super(pos, GameCharacters.BLACK_SORCERER);

        this.weapon = new Weapon(new PointF(0,0),0,0, Weapons.LANCE, this);
        this.health = 800;
        this.damage = 15;
        RectF lifeBarRect = new RectF(pos.x-X_DRAW_OFFSET-10, pos.y-Y_DRAW_OFFSET-15, pos.x+HITBOX_SIZE+X_DRAW_OFFSET+10, pos.y-Y_DRAW_OFFSET);
        this.lifeBarFilled = new RectF(lifeBarRect);
        this.lifeBarStroke = new RectF(lifeBarRect);
    }
}
