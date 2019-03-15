package com.example.chris.assignment02_csci_4020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Collections;
import java.util.Vector;

public class Original_Game extends AppCompatActivity implements View.OnClickListener{

Player player;                  //This object represents the player
    Game_Engine game;           //This object controls the game
    TextView    playerName_tv,  //Some of the textviews for the game
                playerScore_tv,
                title_tv;

    Button      green_b,
                red_b,
                yellow_b,
                blue_b;
    randomSequence sequence;
    Vector<Integer> buttonmap;
    final int original = 1; //mode for the game



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        title_tv = findViewById(R.id.title_tv);
        title_tv.setText("Original Game");

        //---- Create InGame Objects ----//
        player = new Player(this);


        Log.i("o game", "am I working");

        //---- Set Up TextViews ----//
        playerName_tv = findViewById(R.id.playerName_tv);
        playerScore_tv = findViewById(R.id.playerScore_tv);

        //---- Create Java GameGrid Buttons ----//


        //---- SetUp GameGrid Button Onclick ----//
        green_b = findViewById(R.id.gameb1);
        green_b.setOnClickListener(this);

        red_b = findViewById(R.id.gameb2);
        red_b.setOnClickListener(this);

        yellow_b = findViewById(R.id.gameb3);
        yellow_b.setOnClickListener(this);

        blue_b = findViewById(R.id.gameb4);
        blue_b.setOnClickListener(this);

        //Start Button Onclick listener
        findViewById(R.id.start_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerName_tv.setText(player.getName());
                playerScore_tv.setText(player.getScore() +" pts.");

                //---- Start new Game Objects ----//
                sequence = new randomSequence();
                game = new Game_Engine(original);


                buttonmap = new Vector<Integer>();
                buttonmap.addAll(sequence.getSequence());
                Log.i("O Game", buttonmap+"");

                for(int i=0; i<buttonmap.size();i++){

                     //read in value from map Reader
                    switch(buttonmap.get(i)){
                        case 1:
                            green_b.setBackgroundColor(Color.GREEN);
                            break;
                        case 2:
                            red_b.setBackgroundColor(Color.RED);
                            break;
                        case 3:
                            yellow_b.setBackgroundColor(Color.YELLOW);
                            break;
                        case 4:
                            blue_b.setBackgroundColor(Color.BLUE);
                            break;
                        default:
                            Log.i("O Game", "Error: randomsequence off track" + buttonmap.get(i));

                    }

                }

            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.gameb1:
                game.addPlayerInput(1);
                break;
            case R.id.gameb2:
                game.addPlayerInput(2);
                break;

            case R.id.gameb3:
                game.addPlayerInput(3);
                break;
            case R.id.gameb4:
                game.addPlayerInput(4);
                break;
        }
    }
}
