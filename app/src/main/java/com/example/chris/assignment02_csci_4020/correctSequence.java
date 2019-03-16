package com.example.chris.assignment02_csci_4020;

import android.util.Log;

import java.util.Vector;

public class correctSequence {
    public static boolean check(Vector game, Vector player){
        for(int i = 0; i < player.size(); i++){
            if(player.elementAt(i) != game.elementAt(i)){
                Log.i("game_seq_check", "WRONG!!!!!!!!");
                return false;
            }
        }
        Log.i("game_seq_check", "CORRECT!!!!!!!!");
        return true;
    }
}
