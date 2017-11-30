package com.horacio.Response;

/**
 * Created by Horac on 2017/5/20.
 */
public class GetUserResp {
    private int id;
    private String username;
    private double bestScore;
    private int lastCreate;
    private String position;

    public GetUserResp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBestScore() {
        return bestScore;
    }

    public void setBestScore(double bestScore) {
        this.bestScore = bestScore;
    }

    public int getLastCreate() {
        return lastCreate;
    }

    public void setLastCreate(int lastCreate) {
        this.lastCreate = lastCreate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
