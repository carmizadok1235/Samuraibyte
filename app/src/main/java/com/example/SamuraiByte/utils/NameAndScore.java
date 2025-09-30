package com.example.SamuraiByte.utils;

import java.util.jar.Attributes;

public class NameAndScore implements Comparable<NameAndScore> {
    private String name;
    private double score;

    public NameAndScore(String name, double score){
        this.name = name;
        this.score = score;
    }
    public String getName(){
        return this.name;
    }
    public double getScore(){
        return this.score;
    }

    @Override
    public int compareTo(NameAndScore other) {
        return Double.compare(this.score, other.score);
    }
}
