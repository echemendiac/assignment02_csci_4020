package com.example.chris.assignment02_csci_4020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.view.KeyEvent;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.example.chris.assignment02_csci_4020.correctSequence;


public class Original_Game extends AppCompatActivity{

    Player player;                  //This object represents the player
    Game_Engine game = new Game_Engine(1);           //This object controls the game
    TextView    playerName_tv,  //Some of the textviews for the game
                playerScore_tv,
                title_tv;

    Button      green_b,
                red_b,
                yellow_b,
                blue_b;
    Vector<Integer> buttonmap;
    final int original = 1; //mode for the game

    private SoundPool soundPool;
    private Set<Integer> soundsLoaded;

    final randomSequence sequence = new randomSequence();
    final Vector<Integer> soundIds = new Vector<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        soundsLoaded = new HashSet<Integer>();

        /*title_tv = findViewById(R.id.title_tv);
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
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        red_b = findViewById(R.id.gameb2);
        yellow_b = findViewById(R.id.gameb3);
        blue_b = findViewById(R.id.gameb4);

        //Start Button Onclick listener
        findViewById(R.id.start_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerName_tv.setText(player.getName());
                playerScore_tv.setText(player.getScore() +" pts.");

                //---- Start new Game Objects ----//
                game = new Game_Engine(original);


                buttonmap = new Vector<Integer>();
                //buttonmap.addAll(sequence.getSequence());
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

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(4);
        soundPool = spBuilder.build();

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) { // success
                    soundsLoaded.add(sampleId);
                    Log.i("SOUND", "Sound loaded " + sampleId);
                } else {
                    Log.i("SOUND", "Error cannot load sound status = " + status);
                }
            }
        });

        final int gb1Sound = soundPool.load(this, R.raw.resofactorcblip, 1);
        final int gb2Sound = soundPool.load(this, R.raw.jnvrbsse, 1);
        final int gb3Sound = soundPool.load(this, R.raw.modularsamplesdsitetrashortsfshorts, 1);
        final int gb4Sound = soundPool.load(this, R.raw.menegassbd, 1);

        soundIds.add(gb1Sound);
        soundIds.add(gb2Sound);
        soundIds.add(gb3Sound);
        soundIds.add(gb4Sound);

        findViewById(R.id.gameb1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(gb1Sound);
                if (game.isStarted()) {
                    game.addPlayerInput(1);
                    onInpuChecks();
                }
            }
        });

        findViewById(R.id.gameb2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(gb2Sound);
                if (game.isStarted()) {
                    game.addPlayerInput(2);
                    onInpuChecks();
                }
            }
        });
        findViewById(R.id.gameb3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(gb3Sound);
                if (game.isStarted()) {
                    game.addPlayerInput(3);
                    onInpuChecks();
                }
            }
        });

        findViewById(R.id.gameb4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(gb4Sound);
                if (game.isStarted()) {
                    game.addPlayerInput(4);
                    onInpuChecks();
                }
            }
        });

        findViewById(R.id.start_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startCounter();
                if(game.getPlayerISize() > 0){
                    game.reset();
                }
                sequence.clearSequence();

                game.addGameInput(sequence.getSequence());
                playSequence(game.getGameInput(), soundIds);

                System.out.println(game.getScore());

            }
        });

    }

    private void onInpuChecks(){
        if(game.getPlayerISize() == game.getGameInputSize()) {
            if (correctSequence.check(game.getGameInput(), game.getPlayerInput())) {
                game.addGameInput(sequence.getSequence());
                game.updateScore(game.getScore()+1);
                playSequence(game.getGameInput(), soundIds);
                game.clearPlayerInput();
            } else if (!correctSequence.check(game.getGameInput(), game.getPlayerInput())) {
                game.reset();
                sequence.clearSequence();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;

            soundsLoaded.clear();
        }
    }

    private void playSound(int soundId) {
        if (soundsLoaded.contains(soundId)) {
            soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    private void playSequence(Vector<Integer> gInput, Vector<Integer> sounds){
        findViewById(R.id.start_b).setEnabled(false);
        findViewById(R.id.gameb1).setEnabled(false);
        findViewById(R.id.gameb2).setEnabled(false);
        findViewById(R.id.gameb3).setEnabled(false);
        findViewById(R.id.gameb4).setEnabled(false);
        findViewById(R.id.instructions_b).setEnabled(false);

        for(int i = 0; i < gInput.size(); i++){

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(gInput.elementAt(i) == 1){
                Log.i("game", "hit green");
                playSound(sounds.elementAt(0));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(gInput.elementAt(i) == 2){
                Log.i("game", "hit red");
                playSound(sounds.elementAt(1));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(gInput.elementAt(i) == 3){
                Log.i("game", "hit yellow");
                playSound(sounds.elementAt(2));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(gInput.elementAt(i) == 4){
                Log.i("game", "hit blue");
                playSound(sounds.elementAt(3));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        findViewById(R.id.start_b).setEnabled(true);
        findViewById(R.id.gameb1).setEnabled(true);
        findViewById(R.id.gameb2).setEnabled(true);
        findViewById(R.id.gameb3).setEnabled(true);
        findViewById(R.id.gameb4).setEnabled(true);
        findViewById(R.id.instructions_b).setEnabled(true);
    }


    private int ogSimon(){
        boolean notWrong = true;
        int repeatCount = 0;
        while(notWrong){
            game.addGameInput(sequence.getSequence());
            playSequence(game.getGameInput(), soundIds);
            Log.i("ogSimon", "played sequence " + game.getGameInput().size());
            findViewById(R.id.start_b).setEnabled(true);
            findViewById(R.id.gameb1).setEnabled(true);
            findViewById(R.id.gameb2).setEnabled(true);
            findViewById(R.id.gameb3).setEnabled(true);
            findViewById(R.id.gameb4).setEnabled(true);
            findViewById(R.id.instructions_b).setEnabled(true);
            while(game.getPlayerInput().size() != game.getGameInput().size()){
                if(game.getPlayerInput().size() > 0) {
                    if (game.getPlayerInput().elementAt(game.getPlayerInput().size() - 1) != game.getGameInput().elementAt(game.getPlayerInput().size() - 1)) {
                        return game.getScore();
                    }
                }
            }
            findViewById(R.id.start_b).setEnabled(false);
            findViewById(R.id.gameb1).setEnabled(false);
            findViewById(R.id.gameb2).setEnabled(false);
            findViewById(R.id.gameb3).setEnabled(false);
            findViewById(R.id.gameb4).setEnabled(false);
            findViewById(R.id.instructions_b).setEnabled(false);
            if(!(correctSequence.check(game.getGameInput(), game.getPlayerInput()))){
                return game.getScore();
            }
            game.clearPlayerInput();
        }

        return game.getScore();
    }
}
