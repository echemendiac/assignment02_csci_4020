package com.example.chris.assignment02_csci_4020;

import android.util.Log;

import java.util.Vector;

public class Game_Engine {
    private Vector<Integer> playerInput;
    private int playerISize = 0;
    private Vector<Integer> gameInput;
    private int gameInputSize = 0;
    private Integer score = 0;
    private int mode;


    private boolean started = false;

    public void addPlayerInput(int num) {
        this.playerInput.add(num);
        playerISize += 1;
    }
    public void clearPlayerInput(){
        playerInput.removeAllElements();
        playerISize = 0;
    }
    public Vector<Integer> getPlayerInput(){
        return playerInput;
    }
    public int getPlayerISize(){
        return playerISize;
    }
    public boolean emptyPlayerInput(){
        if(playerISize > 0){
            return true;
        }
        else {
            return false;
        }

    }

    public void addGameInput(Vector<Integer> seq){
        this.gameInput = seq;
        this.started = true;
        gameInputSize = gameInputSize + 1;
    }
    public void clearGameInput(){
        this.gameInput.removeAllElements();
        gameInputSize = 0;
    }
    public Vector<Integer> getGameInput(){
        return this.gameInput;
    }
    public int getGameInputSize(){
        return this.gameInputSize;
    }

    public void updateScore(int s){
        this.score = s;
    }
    public void clearScore(){
        this.score = 0;
    }
    public Integer getScore(){
        return this.score;
    }

    public Game_Engine(int mode){
        this.mode = mode;
        playerISize=0;
        playerInput = new Vector<Integer>();
    }
    public boolean compareInputToGame(){
        return playerInput.equals(gameInput);
    }

    public boolean isStarted() {
        return started;
    }

    public void reset(){

        this.clearPlayerInput();

        this.clearGameInput();

        Log.i("game_engine", "game over, score was " + this.getScore());

        this.clearScore();

        this.started = false;


    }
}
