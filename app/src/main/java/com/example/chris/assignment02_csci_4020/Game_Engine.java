package com.example.chris.assignment02_csci_4020;

import java.util.Vector;

public class Game_Engine {
    private Vector<Integer> playerInput;
    private Vector<Integer> gameInput;
    private int mode;

    public void addPlayerInput(int num) {
        this.playerInput.add(num);
    }
    public void addGameInput(int num){
        this.gameInput.add(num);
    }
    public void resetPlayerInput(){
        playerInput.removeAllElements();
    }

    public Game_Engine(int mode){
        this.mode = mode;
        playerInput = new Vector<Integer>();
    }
    public boolean compareInputToGame(){
        return playerInput.equals(gameInput);
    }
}
