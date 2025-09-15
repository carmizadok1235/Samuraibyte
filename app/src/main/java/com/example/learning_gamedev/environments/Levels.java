package com.example.learning_gamedev.environments;

import android.graphics.PointF;

import com.example.learning_gamedev.entities.BlackSorcerer;
import com.example.learning_gamedev.entities.Enemy;
import com.example.learning_gamedev.entities.EnemyNames;

import java.util.ArrayList;
import java.util.logging.Level;

public enum Levels {
    LEVEL1(EnemyNames.BLACK_SORCERER, 3, LevelNumbers.LEVEL1),
    LEVEL2(EnemyNames.BLACK_SORCERER, 5, LevelNumbers.LEVEL2),
    LEVEL3(EnemyNames.BLACK_SORCERER, 7, LevelNumbers.LEVEL3),
    LEVEL4(EnemyNames.BLACK_SORCERER, 9, LevelNumbers.LEVEL4),
    NOLEVEL(EnemyNames.NONE, 0, LevelNumbers.NONELEVEL);

    private Enemy[] enemies;
    private EnemyNames enemyName;
    private int numberOfEnemies;
    private LevelNumbers levelNumber;
    Levels(EnemyNames enemyName, int numberOfEnemies, LevelNumbers levelNumber){
        this.enemies = new Enemy[numberOfEnemies];
        this.enemyName = enemyName;
        this.numberOfEnemies = numberOfEnemies;
        this.levelNumber = levelNumber;
        this.initEnemies();
    }

    private void initEnemies() {
        for (int i = 0; i < this.numberOfEnemies; i++){
//            System.out.println("adding enemies");
            switch (this.enemyName){
                case BLACK_SORCERER -> this.enemies[i] = new BlackSorcerer(new PointF(i*500, 2000));
            }
        }
    }
    public int getNumberOfEnemies(){
        return this.numberOfEnemies;
    }
    public Enemy[] getEnemies(){
        return this.enemies;
    }
    public LevelNumbers getLevelNumber(){
        return this.levelNumber;
    }
    public static Levels getLevelByNumber(LevelNumbers levelNumber){
        return switch (levelNumber){
            case LEVEL1 -> Levels.LEVEL1;
            case LEVEL2 -> Levels.LEVEL2;
            case LEVEL3 -> Levels.LEVEL3;
            case LEVEL4 -> Levels.LEVEL4;
            case NONELEVEL -> Levels.NOLEVEL;
        };
    }
}
