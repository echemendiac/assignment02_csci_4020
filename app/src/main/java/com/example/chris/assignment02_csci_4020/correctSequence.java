package com.example.chris.assignment02_csci_4020;

import java.util.Vector;

public class correctSequence {
    public static boolean check(Vector game, Vector player){
        if(game.size() != player.size()){
            return false;
        }
        else if(!game.equals(player)){
            return false;
        }
        else
            return true;
    }
}
