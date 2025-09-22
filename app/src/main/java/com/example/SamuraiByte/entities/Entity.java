package com.example.SamuraiByte.entities;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.SamuraiByte.interfaces.EntityInterface;

public abstract class Entity implements EntityInterface, Comparable<Entity> {
    protected RectF hitbox;
    protected boolean active;
    public Entity(PointF pos, float width, float height){
        this.hitbox = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
        this.active = true;
    }
    public RectF getHitbox(){
        return this.hitbox;
    }
    public boolean isActive(){
        return this.active;
    }
    public void setActive(boolean active){
        this.active = active;
    }

    @Override
    public int compareTo(Entity other){
//        System.out.println("hitbox value: " + (this.hitbox.top));
//        System.out.println("other hitbox value: " + (other.hitbox.top));
        if (other instanceof Player player){
            return -(player.compareTo(this));
        }
        return Float.compare(this.hitbox.top, other.hitbox.top);
    }
}
