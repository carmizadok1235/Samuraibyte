package com.example.learning_gamedev;

public enum WalkingDirection {
    DOWN(0),
    UP(1),
    LEFT(2),
    RIGHT(3);

    private int index;
    WalkingDirection(int index) {
        this.index = index;
    }
    public int getIndex(){
        return this.index;
    }
}
