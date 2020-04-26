package com.example.onlinequizapp;

public class Rang {
    private String userName;
    private long score;

    private Rang(){

    }


    public Rang(String userName, long score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {

        return userName;
    }

    public long getScore() {

        return score;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public void setScore(long score) {

        this.score = score;
    }
}
